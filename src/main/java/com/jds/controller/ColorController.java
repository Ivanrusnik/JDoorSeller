package com.jds.controller;

import com.jds.dao.entity.ImageEntity;
import com.jds.model.backResponse.ResponseMassage;
import com.jds.model.image.*;
import com.jds.model.backResponse.ResponseModel;
import com.jds.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.jds.model.image.TypeOfImage.*;

@Controller
public class ColorController {
    @Autowired
    private ColorService service;


    @GetMapping(value = "/pages/colors")
    @Secured("ROLE_ADMIN")
    public String getMetalListPage(Model model) throws Exception {
        List<ImageEntity> list = service.getColors();

        list = list.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());

        model.addAttribute("List", list);
        return "colorList";
    }

    @GetMapping(value = "/colors", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ImageEntity> getDoorColors(
            @RequestParam(required = false, defaultValue = "DOOR_COLOR") TypeOfImage type) {
        return service.getImagesByType(type);
    }

    @GetMapping(value = "/pages/colors/{id}")
    public String getColorPage(@PathVariable String id) throws Exception {

        return "color";
    }

    @GetMapping(value = "/colors/{id}")
    @ResponseBody
    public ImageEntity getColor(@PathVariable String id) {

        return service.getColor(id);

    }

    @PutMapping(value = "/colors")
    @ResponseBody
    public ResponseModel saveColor(@RequestBody ImageEntity color) {

        return new ResponseModel(service.saveColor(color));

    }

    @GetMapping(value = "/color/image-file-list/{imageType}")
    @ResponseBody
    public List<ColorPicture> getColorList(@PathVariable TypeOfImage imageType) {

        return service.getImageFileList(imageType);
    }

    @GetMapping(value = "/color/image-file-list/{imageType}/masks")
    @ResponseBody
    public List<ColorPicture> getMasksList(@PathVariable TypeOfImage imageType) {

        return service.getMaskFileList(imageType);
    }

    @DeleteMapping(value = "/colors/{id}")
    @ResponseBody
    public ResponseModel deleteColor(@PathVariable String id) {

        return new ResponseModel(service.deleteColor(id));
    }

    @GetMapping(value = "/image/types")
    @ResponseBody
    public EnumSet<TypeOfImage> getImageTypeList() {

        return service.getImageTypeList();
    }

    @GetMapping(value = "/doorType/{doorTypeId}/colors/types/buttons")
    @ResponseBody
    public List<TypeView> getImageTypeDoorColor(@PathVariable int doorTypeId) {

        return service.getImageTypeDoorColor();

    }

    @GetMapping(value = "/colors/shield-designs/types/buttons")
    @ResponseBody
    public List<TypeView> getAllTypeShieldDesign() {

        return service.getImageTypeShieldDesign();

    }

    @GetMapping(value = "/color/shieldDesign")
    @ResponseBody
    public List<ImageEntity> getColorShieldDesign() {

        List<ImageEntity> list = service.getColorsTypeIfContainsGlassTrue(SHIELD_DESIGN);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).clearNonSerializingFields();
        }
        return list;
    }

    @GetMapping(value = "/doorType/{doorTypeId}/{type}/colors")
    @ResponseBody
    public List<ImageEntity> getColorByType(@PathVariable int doorTypeId, @PathVariable TypeOfDoorColor type) {

        return service.getColorsByType(doorTypeId, type);
    }

    @GetMapping(value = "/doorType/{doorTypeId}/{type}/shield-design")
    @ResponseBody
    public List<ImageEntity> getShieldDesignByType(@PathVariable int doorTypeId, @PathVariable TypeOfShieldDesign type) {

        return service.getShieldDesignByType(doorTypeId, type);
    }

    @GetMapping(value = "/color/types/color")
    @ResponseBody
    public List<TypeView> getAllTypeDoorColor() {

        return service.getImageTypeDoorColor();

    }

    @GetMapping(value = "/colors/{id}/limitations")
    @ResponseBody
    public List<ImageEntity> getLimitation(@PathVariable int id) {

        return service.fineLimitationByMasterId(id);
    }

    @PutMapping(value = "/colors/{id}/limitations")
    @ResponseBody
    public ResponseMassage putLimitation(@PathVariable int id, @RequestBody List<ImageEntity> limits) {

        service.putLimitationByMasterId(id, limits);

        return new ResponseMassage(true, "ок");
    }

    @DeleteMapping(value = "/colors/{id}/limitations")
    @ResponseBody
    public ResponseMassage deleteLimitation(@PathVariable int id) {

        service.deleteLimitationByMasterId(id);

        return new ResponseMassage(true, "ок");
    }
}
