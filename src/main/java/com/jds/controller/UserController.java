package com.jds.controller;

import com.jds.dao.entity.UserEntity;

import com.jds.model.Role;
import com.jds.model.modelEnum.PriceGroups;
import com.jds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService service;


    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/users")
    public String getUsers(Model model) throws Exception {
        List<UserEntity> list = service.getUsers();
        model.addAttribute("users", list);
        return "userList";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/user")
    public String getUser(Model model,@RequestParam(defaultValue = "0") String userId) throws Exception {

        UserEntity user = service.getUser(userId);
        model.addAttribute("userId", userId);

        if (user!=null){
            model.addAttribute("user",user);
        }

        return "user";

    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/users")
    public String saveUser(Model model,@RequestParam(required = false) String username,
                           @RequestParam(required = false) String userId,
                           @RequestParam(required = false) String password,
                           @RequestParam(required = false) int discount,
                           @RequestParam(required = false) boolean enabledСheckbox,
                           @RequestParam(required = false) PriceGroups priceGroups,
                           @RequestParam(required = false) Role role) throws Exception {

        service.saveUser(
                userId,
                username,
                password,
                discount,
                enabledСheckbox,
                priceGroups,
                role);

        List<UserEntity> list = service.getUsers();
        model.addAttribute("users", list);
        return "userList";

    }

    @GetMapping(value = "usersetting")
    public String getUsersSettingPage(Model model) throws Exception {

        model.addAttribute("setting", service.getUserSetting());
        model.addAttribute("isAdnin", service.getCurrentUser().isAdmin());
        return "userSetting";
    }

    @PostMapping(value = "usersetting")
    public String saveUsersSetting(@RequestParam(required = false) int retailMargin,
                                   @RequestParam(required = false) int salesTax,
                                   @RequestParam(required = false) boolean includesTax) throws Exception {

        service.saveUserSetting(retailMargin,salesTax,includesTax);
        return "redirect:usersetting";
    }

    @GetMapping(value = "users/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<Role> getRoles() throws Exception {
        return service.getRoles();
    }

}
