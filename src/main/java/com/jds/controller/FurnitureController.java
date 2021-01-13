package com.jds.controller;

import com.jds.dao.entity.DoorFurniture;
import com.jds.model.AvailableFieldsForSelection;
import com.jds.model.ResponseAction;
import com.jds.model.modelEnum.TypeOfFurniture;
import com.jds.service.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FurnitureController {

    @Autowired
    private FurnitureService service;

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/furniture")
    public String getFurnitureListPage(Model model) {

        model.addAttribute("furnitureList", service.getFurnitureList());
        return "furnitureList";
    }

    @GetMapping(value = "/furniture/{id}")
    public String getFurniturePage(@PathVariable String id) {

        return "furniture";
    }

    @GetMapping(value = "/furniture/item/{id}")
    @ResponseBody
    public DoorFurniture getFurniture(@PathVariable String id) {


        return service.getDoorFurniture(id);

    }

    @PutMapping(value = "/furniture/item")
    @ResponseBody
    public ResponseAction saveFurniture(@RequestBody DoorFurniture furniture) {

        return new ResponseAction(service.saveFurniture(furniture));

    }


    @GetMapping(value = "/furniture/types")
    @ResponseBody
    public List<TypeOfFurniture> getTypes() {

        return service.getTypesFurniture();

    }

    @GetMapping(value = "/furniture/available-fields/{door_type_id}")
    @ResponseBody
    public AvailableFieldsForSelection getAvailableFields(@PathVariable String door_type_id) {

        return service.getAvailableFields(door_type_id);

    }

    @DeleteMapping(value = "/furniture/{id}")
    @ResponseBody
    public ResponseAction deleteFurniture(@PathVariable String id) {

        return new ResponseAction(service.deleteFurniture(id));

    }

}
