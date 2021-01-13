package com.jds.controller;

import com.jds.dao.entity.DoorClass;
import com.jds.service.MaineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DoorClassController {

    @Autowired
    private MaineService service;

    @GetMapping(value = "/doorclass")
    public String getDoorClass(@RequestParam(required = false) String classId, Model model) throws Exception {

        DoorClass doorClass = service.getDoorClass(classId);
        model.addAttribute("classId", classId);
        model.addAttribute("name", doorClass.getName());
        model.addAttribute("description", doorClass.getDescription());
        model.addAttribute("fireproof", doorClass.getFireproof());
        model.addAttribute("hot", doorClass.getHot());
        model.addAttribute("namePicture", doorClass.getNamePicture());

        return "doorClass";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/doorclass")
    public String saveDoorClass(@RequestParam(required = false) String classId,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String description,
                                @RequestParam(required = false) boolean fireproofcheckbox,
                                @RequestParam(required = false) boolean hotcheckbox,
                                @RequestParam(required = false) String namePicture,
                                Model model) throws Exception {

        service.saveOrUpdateDoorClass(classId, name, description,
                fireproofcheckbox, hotcheckbox, namePicture);

        List<DoorClass> list = service.getDoorClass();
        model.addAttribute("accountInfos", list);
        return "doorClassList";
    }

    @DeleteMapping(value = "/doorclass", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public int deleteDoorclass(Model model, @RequestParam(required = false) String classId) throws Exception {

        service.deleteDoorClass(classId);
        return 1;
    }
}
