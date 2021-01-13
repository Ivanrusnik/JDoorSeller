package com.jds.controller;

import com.jds.dao.entity.*;
import com.jds.model.ResponseAction;
import com.jds.model.Specification;
import com.jds.service.MaterialsService;
import com.jds.service.UserServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MaterialsController {

    @Autowired
    private MaterialsService service;
    @Autowired
    private UserServ userService;


    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/materials")
    public String getMaterialPage() {
        return "materialList";
    }

    @GetMapping(value = "/materialsList")
    @ResponseBody
    public List<RawMaterials> getAllMaterials() {
        return service.getAllMaterials();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/specification")
    public String getSpecificationPage(Model model) throws Exception {
        model.addAttribute("isAdnin", userService.getCurrentUser().isAdmin());
        return "specification";
    }

    @GetMapping(value = "/specificationbyid", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Specification getSpecification(@RequestParam(required = false) String typeId,
                                          Model model) throws Exception {

        return service.getSpecification(typeId);
    }

    @PostMapping(value = "/specification", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<LineSpecification> saveSpecification(@RequestBody Specification templateJSON) throws Exception {

        return service.saveSpecification(templateJSON);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/pages/specifications")
    public String getSpecificationListPage(Model model) throws Exception {

        List<SpecificationEntity> list = service.getLineSpecification();
        model.addAttribute("List", list);
        return "specificationList";
    }

    @GetMapping(value = "/pages/specifications/{id}")
    public String getSpecificationPage(@PathVariable String id) throws Exception {

        return "specificationEntity";
    }

    @PutMapping(value = "/specifications", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SpecificationEntity saveSpecificationEntity(@RequestBody SpecificationEntity specificationEntity) {

        service.saveSpecificationEntity(specificationEntity);

        return specificationEntity;
    }

    @GetMapping(value = "/specifications/{id}")
    @ResponseBody
    public SpecificationEntity getSpecificationEntity (@PathVariable String id) {

        return service.getSpecificationEntity(id);
    }

    @DeleteMapping(value = "/specifications/{id}")
    @ResponseBody
    public ResponseAction deleteSpecification(@PathVariable String id) {

        return new ResponseAction(service.deleteSpecificationEntity(id));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/pages/specification/{id}/line/{line_id}")
    public String getSpecificationLinePage(Model model, @PathVariable String id, @PathVariable String line_id) {

        model.addAttribute("id", id);
        model.addAttribute("line_id", line_id);
        return "specificationLine";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/specification/{id}/line/{line_id}")
    @ResponseBody
    public LineSpecification getSpecificationLine(@PathVariable String id, @PathVariable String line_id) {

        return service.getLineSpecification(line_id);
    }

    @PostMapping(value = "/specification/line/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LineSpecification saveSpecificationLine(@RequestBody LineSpecification lineSpecification, @PathVariable String id) throws Exception {

        return service.saveSpecificationLine(Integer.parseInt(id), lineSpecification);
    }

    @DeleteMapping(value = "/specification/line/{id}")
    @ResponseBody
    public String deleteLineSpecification(@PathVariable String id) {
        return service.deleteLineSpecification(id);
    }

}
