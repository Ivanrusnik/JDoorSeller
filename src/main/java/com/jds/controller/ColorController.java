package com.jds.controller;

import com.jds.dao.entity.ImageEntity;
import com.jds.model.image.ColorPicture;
import com.jds.model.ResponseAction;
import com.jds.model.image.TypeOfDoorColor;
import com.jds.model.image.TypeOfImage;
import com.jds.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.jds.model.image.TypeOfImage.*;

@Controller
public class ColorController {
    @Autowired
    private ColorService service;

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/color")
    public String getMetalListPage(Model model) throws Exception {
        List<ImageEntity> list = service.getColors();

        list = list.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());

        model.addAttribute("List", list);
        return "colorList";
    }

    @GetMapping(value = "color/doorColors",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ImageEntity> getDoorColorsList() {
        List<ImageEntity> list = service.getColorsType(DOOR_COLOR);
        for (int i =0; i < list.size(); i++){
            list.get(i).clearNonSerializingFields();
        }
        return list;
    }

    @GetMapping(value = "/color/{id}")
    public String getMetalPage(@PathVariable String id) throws Exception {

        return "color";
    }

    @GetMapping(value = "/color/item/{id}")
    @ResponseBody
    public ImageEntity getColor(@PathVariable String id) {

        return service.getColor(id);

    }

    @PutMapping(value = "/color/item")
    @ResponseBody
    public ResponseAction saveColor(@RequestBody ImageEntity color) {

        return new ResponseAction(service.saveColor(color));

    }

    @GetMapping(value = "/color/image-file-list/{imageType}")
    @ResponseBody
    public List<ColorPicture> getColorList(@PathVariable String imageType) {

        return service.getImageFileList(imageType);

    }

    @DeleteMapping(value = "/color/{id}")
    @ResponseBody
    public ResponseAction deleteColor(@PathVariable String id) {

        return new ResponseAction(service.deleteColor(id));

    }

    @GetMapping(value = "/image/types")
    @ResponseBody
    public EnumSet<TypeOfImage> getImageTypeList() {

        return service.getImageTypeList();

    }

    @GetMapping(value = "/color/door-color/types")
    @ResponseBody
    public EnumSet<TypeOfDoorColor> getImageTypeDoorColor() {

        return service.getImageTypeDoorColor();

    }

    @GetMapping(value = "/color/shieldDesign")
    @ResponseBody
    public List<ImageEntity> getColorShieldDesign() {

        List<ImageEntity> list = service.getColorsTypeIfContainsGlassTrue(SHIELD_DESIGN);
        for (int i =0; i < list.size(); i++){
            list.get(i).clearNonSerializingFields();
        }
        return list;
    }
}
