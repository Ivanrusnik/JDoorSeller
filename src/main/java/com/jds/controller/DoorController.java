package com.jds.controller;

import com.jds.entity.DoorEntity;
import com.jds.entity.DoorsОrder;
import com.jds.model.RestrictionOfSelectionFields;
import com.jds.service.DoorService;
import com.jds.service.MaineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;

@Controller
public class DoorController {

    @Autowired
    private DoorService service;
    @Autowired
    private MaineService maineService;

    @GetMapping(value = "/calculation")
    public String calculation(Model model,
                              @RequestParam(required = false) String orderId,
                              @RequestParam(required = false) String id,
                              @RequestParam(required = false) String typid) throws Exception {
        model.addAttribute("orderId", orderId);
        model.addAttribute("id", id);
        model.addAttribute("typid", typid);
        return "calculation";
    }

    @GetMapping(value = "/door", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorEntity data(@RequestParam(required = false, defaultValue = "0") String id,
                           @RequestParam(required = false, defaultValue = "0") String orderId,
                           @RequestParam(required = false, defaultValue = "0") String typid) throws Exception {

        return service.getDoor(Integer.parseInt(id),
                Integer.parseInt(orderId),
                Integer.parseInt(typid));
    }


    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorEntity calculateTheDoor(@RequestBody DoorEntity door) throws Exception {

        return service.calculateTheDoor(door);

    }

    @PostMapping(value = "/saveDoor", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorEntity saveDoor(Model model, @RequestBody DoorEntity door) throws Exception {

        return service.saveDoor(door);

    }

    @DeleteMapping(value = "/door", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorsОrder deleteOrder(Model model,
                                  @RequestParam(required = false) String orderId,
                                  @RequestParam(required = false) String id) throws Exception {

        return service.deleteDoorFromOrder(id, orderId);
    }

}
