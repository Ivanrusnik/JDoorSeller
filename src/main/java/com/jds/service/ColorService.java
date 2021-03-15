package com.jds.service;

import com.jds.controller.OrderController;
import com.jds.dao.entity.LimitationDoor;
import com.jds.dao.repository.ColorRepository;
import com.jds.dao.entity.ImageEntity;
import com.jds.dao.repository.TemplateRepository;
import com.jds.model.enumClasses.TypeOfLimitionDoor;
import com.jds.model.image.*;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.jds.model.image.TypeOfImage.DOOR_COLOR;

@Service
public class ColorService {

    @Autowired
    private ColorRepository dAO;
    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private DeleteCheckService deleteCheckService;
    private Logger logger = LoggerFactory.getLogger(ColorService.class);

    public List<ImageEntity> getColors() {
        return dAO.getImages();
    }

    public List<ImageEntity> getColorsType(Enum type) {
        List<ImageEntity> list = dAO.getImages();
        List<ImageEntity> listReturn = new ArrayList<ImageEntity>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTypeOfImage() == type) {
                listReturn.add(list.get(i));
            }
        }
        return listReturn;
    }

    public List<ImageEntity> getColorsTypeIfContainsGlassTrue(Enum type) {
        List<ImageEntity> list = dAO.getImagesContainsGlass();
        List<ImageEntity> listReturn = new ArrayList<ImageEntity>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTypeOfImage() == type) {
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
        String colorDirectory = colors.getTypeOfImage().getPath();
        if (colorPath != null && !colorPath.contains(colorDirectory)) {
            colors.setPicturePath(colorDirectory + addFileExtension(colorPath, FileExtensionByImageType(colors.getTypeOfImage())));
        }
        dAO.saveColors(colors);
        return "ok";
    }

    public String deleteColor(@NonNull String id) {

        ImageEntity color = getColor(id);

        if (deleteCheckService.checkColor(color)) {
            return null;
        } else {
            return dAO.deleteColor(color);
        }
    }

    public EnumSet<TypeOfImage> getImageTypeList() {
        return EnumSet.allOf(TypeOfImage.class);
    }

    public List<TypeView> getImageTypeDoorColor() {
        return EnumSet.allOf(TypeOfDoorColor.class).stream()
                .map(TypeView::new)
                .collect(Collectors.toList());
    }

    public List<TypeView> getImageTypeDoorColors(int doorTypeId, TypeOfLimitionDoor typeSettings) {

        return EnumSet.allOf(TypeOfDoorColor.class).stream()
                .map(TypeView::new)
                .collect(Collectors.toList());
    }

    public List<TypeView> getImageTypeShieldDesign() {
        return EnumSet.allOf(TypeOfShieldDesign.class).stream()
                .map(TypeView::new)
                .collect(Collectors.toList());
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

    public List<ColorPicture> getImageFileList(TypeOfImage type) {
        return getImageFileList(type.getPath());
    }

    public List<ColorPicture> getMaskFileList(TypeOfImage type) {
        return getImageFileList(type.getMaskPath());
    }

    public List<ColorPicture> getImageFileList(String picDirName) {

        File PicDirFile = new File(ColorPicture.pathToFolderPictures(picDirName));
        if (!PicDirFile.exists()) {
            logger.error(String.format("!ERROR: directory is not found  %s", PicDirFile.getAbsolutePath()));
        }

        List<ColorPicture> list = new ArrayList<>();
        int i = 0;
        for (File elem : PicDirFile.listFiles()) {
            i++;
            if (elem.isDirectory()) {
                File dirParent = new File(ColorPicture.pathToFolderPictures(picDirName + elem.getName()));
                for (File elem2 : dirParent.listFiles()) {
                    i++;
                    if (elem2.isFile()) {
                        list.add(new ColorPicture(
                                i,
                                elem2.getName(),
                                picDirName + elem.getName() + "/" + elem2.getName()));
                    }
                }
            } else if (elem.isFile()) {
                list.add(new ColorPicture(
                        i, elem.getName(),
                        picDirName + elem.getName()));
            }
        }

        list = list.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());

        return list;
    }

    public List<ImageEntity> getColorsByType(int doorTypeId, TypeOfDoorColor type) {

        List<LimitationDoor> listLim = templateRepository.getLimitationDoors(doorTypeId, TypeOfLimitionDoor.COLOR_DOOR);

        return listLim.stream()
                .map(lim -> dAO.getColorById(lim.getItemId()))
                .filter(elem -> elem.getTypeOfDoorColor() == type)
                .collect(Collectors.toList());
    }

    public List<ImageEntity> getShieldDesignByType(int doorTypeId, TypeOfShieldDesign type) {
        List<LimitationDoor> listLim = templateRepository.getLimitationDoors(doorTypeId, TypeOfLimitionDoor.SHIELD_DESIGN);

        return listLim.stream()
                .map(lim -> dAO.getColorById(lim.getItemId()))
                .filter(elem -> elem.getTypeOfShieldDesign() == type)
                .collect(Collectors.toList());
    }
}
