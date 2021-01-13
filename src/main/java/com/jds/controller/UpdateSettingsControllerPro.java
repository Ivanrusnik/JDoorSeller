package com.jds.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jds.dao.repository.MainDAO;
import com.jds.dao.repository.MaterialsRepository;
import com.jds.dao.repository.MetalRepository;
import com.jds.dao.entity.*;
import com.jds.service.ColorService;
import com.jds.service.MaineService;
import com.jds.service.MaterialsService;
import com.jds.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.StringReader;


@Controller
public class UpdateSettingsControllerPro {

    @Autowired
    private MainDAO mainDAO;

    @Autowired
    private MaterialsRepository materialsDAO;

    @Autowired
    private MaineService service;

    @Autowired
    private MaterialsService materialsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MetalRepository metalDAO;

    @Autowired
    private ColorService colorService;

    @PostMapping(value = "/update/doorclass", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateDoorClass(@RequestParam(required = false) String dataJson) throws Exception {


        StringReader reader = new StringReader(dataJson);
        ObjectMapper mapper = new ObjectMapper();

        DoorClass doorClass = mapper.readValue(reader, DoorClass.class);

        mainDAO.saveOrUpdateDoorClass(doorClass);
        return "jr";
    }

    @PostMapping(value = "/update/limitationdoor", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateLimitationDoor(@RequestParam("request") String data, @RequestBody LimitationDoor dataJson) {

        mainDAO.saveOrUpdateLimitationDoor(dataJson);

        return "jr";
    }

    @PostMapping(value = "/update/doortype", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateDoorType(@RequestParam(required = false) String dataJson) throws Exception {

        StringReader reader = new StringReader(dataJson);
        ObjectMapper mapper = new ObjectMapper();

        DoorType doorType = mapper.readValue(reader, DoorType.class);
        int id = service.saveOrUpdateDoorType(doorType);

        return String.valueOf(id);
    }

    @PostMapping(value = "/update/sizeofdoorparts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateSizeOfDoorParts(@RequestParam(required = false) String dataJson) throws Exception {

        //!!! make sure there are no lines with the same name
        StringReader reader = new StringReader(dataJson);
        ObjectMapper mapper = new ObjectMapper();

        SizeOfDoorParts sizeOfDoorParts = mapper.readValue(reader, SizeOfDoorParts.class);
        mainDAO.saveOrUpdateSizeOfDoorParts(sizeOfDoorParts);

        return "jr";
    }

    @PostMapping(value = "/update/metal", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateMetal(@RequestParam(required = false) String dataJson) throws Exception {


        StringReader reader = new StringReader(dataJson);
        ObjectMapper mapper = new ObjectMapper();

        Metal metal = mapper.readValue(reader, Metal.class);
        metalDAO.saveMetal(metal);

        return "jr";
    }

    @PostMapping(value = "/update/Furniture", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateFurniture(@RequestParam(required = false) String dataJson) throws Exception {


        StringReader reader = new StringReader(dataJson);
        ObjectMapper mapper = new ObjectMapper();

        DoorFurniture doorFurniture = mapper.readValue(reader, DoorFurniture.class);
        mainDAO.saveDoorFurniture(doorFurniture);

        return "jr";
    }

    @PostMapping(value = "/update/doorcolors", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateDoorColors(@RequestParam(required = false) String dataJson) throws Exception {


        StringReader reader = new StringReader(dataJson);
        ObjectMapper mapper = new ObjectMapper();

        ImageEntity doorColors = mapper.readValue(reader, ImageEntity.class);
        colorService.saveColor(doorColors);

        return "jr";
    }

    @PostMapping(value = "/update/bendsetting", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateBendSetting(@RequestParam(required = false) String dataJson) throws Exception {


        StringReader reader = new StringReader(dataJson);
        ObjectMapper mapper = new ObjectMapper();

        BendSetting bendSetting = mapper.readValue(reader, BendSetting.class);
        service.saveBendSetting(bendSetting);


        return "jr";
    }

    @PostMapping(value = "/update/salaryconstants", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateSalaryConstants(@RequestParam(required = false) String dataJson) throws Exception {


        StringReader reader = new StringReader(dataJson);
        ObjectMapper mapper = new ObjectMapper();

        SalaryConstants salaryConstants = mapper.readValue(reader, SalaryConstants.class);
        service.saveSalaryConstants(salaryConstants);

        return "jr";
    }

    @PostMapping(value = "/update/salarysetting", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateSalarySetting(@RequestParam(required = false) String dataJson) throws Exception {


        StringReader reader = new StringReader(dataJson);
        ObjectMapper mapper = new ObjectMapper();

        SalarySetting salarySetting = mapper.readValue(reader, SalarySetting.class);
        service.saveSalarySetting(salarySetting);

        return "jr";
    }

    @PostMapping(value = "/update/specificationsetting", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateSpecificationSetting(@RequestParam(required = false) String dataJson) throws Exception {


        StringReader reader = new StringReader(dataJson);
        ObjectMapper mapper = new ObjectMapper();

        SpecificationSetting setting = mapper.readValue(reader, SpecificationSetting.class);
        materialsService.saveSpecificationSetting(setting);

        return "jr";
    }

    @PostMapping(value = "/update/specification", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String setSpecification(@RequestParam(required = false) String dataJson) throws Exception {

        StringReader reader = new StringReader(dataJson);
        ObjectMapper mapper = new ObjectMapper();

        LineSpecification spec = mapper.readValue(reader, LineSpecification.class);


        LineSpecification lineSpecification = materialsDAO.saveLineSpecification(spec);

        return "jr";
    }

}
