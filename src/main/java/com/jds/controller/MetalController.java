package com.jds.controller;

import com.jds.dao.entity.Metal;
import com.jds.model.ResponseAction;
import com.jds.service.MetalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MetalController {
    @Autowired
    private MetalService service;

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/metal")
    public String getMetalListPage(Model model) throws Exception {

        List<Metal> list = service.getMetals();
        model.addAttribute("metalList", list);
        return "metalList";
    }

    @GetMapping(value = "/metal/{id}")
    public String getMetalPage(@PathVariable String id) throws Exception {

        return "metal";
    }

    @GetMapping(value = "/metal/item/{id}")
    @ResponseBody
    public Metal getFurniture(@PathVariable String id) {

        return service.getMetal(id);

    }

    @PutMapping(value = "/metal/item")
    @ResponseBody
    public ResponseAction saveMetal(@RequestBody Metal metal) {

        return new ResponseAction(service.saveMetal(metal));

    }

    @DeleteMapping(value = "/metal/{id}")
    @ResponseBody
    public ResponseAction deleteFurniture(@PathVariable String id) {

        return new ResponseAction(service.deleteMetal(id));


    }
}
