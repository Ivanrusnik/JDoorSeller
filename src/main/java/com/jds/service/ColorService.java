package com.jds.service;

import com.jds.dao.repository.ColorRepository;
import com.jds.dao.entity.ImageEntity;
import com.jds.model.image.ColorPicture;
import com.jds.model.image.TypeOfDoorColor;
import com.jds.model.image.TypeOfImage;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.jds.model.image.TypeOfImage.DOOR_COLOR;

@Service
public class ColorService {

    @Autowired
    private ColorRepository dAO;

    @Autowired
    private DeleteCheckService deleteCheckService;

    public List<ImageEntity> getColors() {
        return dAO.getImages();
    }

    public List<ImageEntity> getColorsType(Enum type) {
        List<ImageEntity> list = dAO.getImages();
        List<ImageEntity> listReturn = new ArrayList<ImageEntity>();
                for(int i = 0; i < list.size(); i++){
                    if (list.get(i).getTypeOfImage() == type){
                        listReturn.add(list.get(i));
                    }
                }
        return listReturn;
    }

    public List<ImageEntity> getColorsTypeIfContainsGlassTrue(Enum type) {
        List<ImageEntity> list = dAO.getImagesContainsGlass();
        List<ImageEntity> listReturn = new ArrayList<ImageEntity>();
        for(int i = 0; i < list.size(); i++){
            if (list.get(i).getTypeOfImage() == type){
                listReturn.add(list.get(i));
            }
        }
        return listReturn;
    }

    public ImageEntity getColor(@NonNull String id) {

        if ("0".equals(id)) {
            return new ImageEntity();
        }

        return dAO.getColorById(Integer.parseInt(id)).clearNonSerializingFields();
    }

    public String saveColor(@NonNull ImageEntity colors) {
        String colorPath = colors.getPicturePath();
        String colorDirectory = getPathDirectoryByImageType(colors.getTypeOfImage());
        if (colorPath != null && !colorPath.contains(colorDirectory)) {
            colors.setPicturePath(colorDirectory + addFileExtension(colorPath, FileExtensionByImageType(colors.getTypeOfImage())));
        }
        dAO.saveColors(colors);
        return "ok";
    }

    public String deleteColor(@NonNull String id) {

        ImageEntity color = getColor(id);

        if (deleteCheckService.checkColor(color)){
            return null;
        } else {
            return dAO.deleteColor(color);
        }
    }

    public EnumSet<TypeOfImage> getImageTypeList() {
        return EnumSet.allOf(TypeOfImage.class);
    }

    public EnumSet<TypeOfDoorColor> getImageTypeDoorColor() {
        return EnumSet.allOf(TypeOfDoorColor.class);
    }

    public String addFileExtension(@NonNull String colorPath, @NonNull String extension) {

        if (colorPath.contains(extension)) {
            return colorPath;
        }

        return colorPath + extension;
    }

    private String FileExtensionByImageType(@NonNull TypeOfImage type) {
        if (DOOR_COLOR == type || TypeOfImage.SHIELD_COLOR == type)
            return ".jpg";
        else if (TypeOfImage.SHIELD_DESIGN == type)
            return ".png";
        return "";
    }

    private String getPathDirectoryByImageType(@NonNull TypeOfImage type) {
        if (DOOR_COLOR == type)
            return "images/Door/AColor1/";
        else if (TypeOfImage.SHIELD_COLOR == type)
            return "images/shield sketch/";
        else if (TypeOfImage.SHIELD_DESIGN == type)
            return "images/shield sketch/design/";
        else if (TypeOfImage.DOOR_DESIGN == type)
            return "images/Door/design/";
        else if (TypeOfImage.SHIELD_GLASS == type)
            return "images/Door/shieldGlass/";
        return "";
    }

    public List<ColorPicture> getImageFileList(String type) {

        String pathImageDir = getPathDirectoryByImageType(TypeOfImage.valueOf(type));
        Path rootLocation = Paths.get("");
        File dir = new File(rootLocation + "src/main/resources/static/" + pathImageDir);
        if (!dir.exists()) {
            System.out.println("!EROR: file is not defaund" + dir.getAbsolutePath());
        }

        List<ColorPicture> list = new ArrayList<>();
        int i = 0;
        for (File elem : dir.listFiles()) {
            i++;
            if (elem.isDirectory()) {
                File dirParent = new File(rootLocation + "src/main/resources/static/" + pathImageDir + elem.getName());
                for (File elem2 : dirParent.listFiles()) {
                    i++;
                    if (elem2.isFile()) {
                        list.add(colorAdPicture(i, elem2.getName(), pathImageDir + elem.getName() + "/" + elem2.getName()));
                    }
                }
            } else if (elem.isFile()) {
                list.add(colorAdPicture(i, elem.getName(), pathImageDir + elem.getName()));
            }
        }

        list = list.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());

        return list;
    }

    public ColorPicture colorAdPicture(int id, String name, String path) {
        return new ColorPicture(id, name, path);
    }
}
