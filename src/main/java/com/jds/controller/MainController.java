package com.jds.controller;

import com.jds.dao.MainDAO;
import com.jds.entity.DoorClass;
import com.jds.entity.DoorType;
import com.jds.model.Door;
import com.jds.model.FireproofDoor;
import com.jds.service.MaineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private MaineService Service;

    @GetMapping(value = "/")
    public String updateDoorClass(@RequestParam(required = false) String kay,
                                  @RequestParam(required = false) String dataJson) throws Exception {

        return "MinePage";
    }

    @GetMapping(value = "/setting")
    public String setting(@RequestParam(required = false) String kay,
                                  @RequestParam(required = false) String dataJson, Model model) throws Exception {

        List<DoorClass> list = Service.getDoorClass();
        model.addAttribute("accountInfos", list);
        return "settingPage_doorclass";
    }
    @GetMapping(value = "/doorclass")
    public String getDoorClass(@RequestParam(required = false) String kay,
                               @RequestParam(required = false) String dataJson, Model model) throws Exception {

        List<DoorClass> list = Service.getDoorClass();
        model.addAttribute("accountInfos", list);
        return "settingPage_doorclass";
    }
    @GetMapping(value = "/doortype")
    public String getDoorType(@RequestParam(required = false) String kay,
                               @RequestParam(required = false) String dataJson, Model model) throws Exception {

        List<DoorType> list = Service.getDoorType();
        model.addAttribute("accountInfos", list);
        return "settingPage_doortype";
    }


    @GetMapping(value = "/calculation")
    public String calculation(@RequestParam(required = false) String kay,
                              @RequestParam(required = false) String dataJson, Model model) throws Exception {


        List<FireproofDoor> list = Service.getlistDoor();
        model.addAttribute("doors", list);

        return "calculation";
    }

    @GetMapping(value = "/data",produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Door data(@RequestParam(required = false) String kay,
                     @RequestParam(required = false) String dataJson, Model model) throws Exception {

        FireproofDoor door = new FireproofDoor(100199);

        return door;
    }
}
