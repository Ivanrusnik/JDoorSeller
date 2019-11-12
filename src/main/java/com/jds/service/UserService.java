package com.jds.service;


import com.jds.dao.UserDAO;
import com.jds.entity.UserEntity;
import com.jds.entity.UserSetting;
import com.jds.model.auth.Role;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDAO dAO;
    @Autowired
    private MaineService maineService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userHoldder(username);
    }

    public UserEntity userHoldder(String username) {


        if (username.equals("admin")) {

            List<Role> roleList = new ArrayList<>();
            roleList.add(Role.USER);
            roleList.add(Role.ADMIN);

            return UserEntity.builder()
                    .id(9300)
                    .username(username)
                    .password("$2a$10$DMD6ILHkU12.yg7Xup91XO/ByVVV5Y3G1c/lct8nZNAnxUS9gJ9Ei")
                    .authorities(roleList)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .enabled(true)
                    .build();
        }

        UserEntity user = dAO.getUserByName(username);
        if (user != null){
            List<Role> roleList = new ArrayList<>();
            roleList.add(Role.USER);

            user.setAuthorities(roleList);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);
        }
        return user;
    }

    public List<UserEntity> getUsers() {

        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.USER);

        List<UserEntity> list = dAO.getUsers();
        list.stream().peek((user)->setEnabledAndRole(user,roleList))
                .collect(Collectors.toList());

        list.add(userHoldder("admin"));

        return list;
    }

    public void saveUser(@NonNull String userId, @NonNull String username, @NonNull String password,int discount, boolean enabledСheckbox) {

        if (username == "" || password == "") {
            throw new IllegalArgumentException("username or password in saveUser can not be empty!");
        }

        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.USER);

        int idInt = Integer.parseInt(userId);

        dAO.saveOrUpdateUser(UserEntity.builder()
                .id(idInt)
                .username(username)
                .password(insertPassword(password))
                .authorities(roleList)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .discount(discount)
                .enabled(enabledСheckbox)
                .build());
    }

    public String insertPassword(String pass){
        if (pass =="") {
           return getCurrentUser().getPassword();
        }
        else {
            return new BCryptPasswordEncoder().encode(pass);
        }
    }

    public UserEntity getUser(String userId){

        int idInt = Integer.parseInt(userId);


        if (idInt==9300){
            return userHoldder("admin");
        }

        else if (idInt==0){
            return UserEntity.builder()
                    .username("newUser")
                    .build();
        }

        return dAO.getUser(idInt);

    }

    public void setEnabledAndRole(UserEntity user,List<Role> roleList){
        user.setAuthorities(roleList);
        user.setEnabled(true);
    }

    public UserEntity getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  (UserEntity) authentication.getPrincipal();
    }

    public UserSetting getUserSetting(){
        return  dAO.getUserSetting(getCurrentUser().getId());
    }

    public void saveUserSetting(int retailMargin,int salesTax,boolean includesTax){
        dAO.saveUserSetting(
                UserSetting.builder()
                .id(getCurrentUser().getId())
                .retailMargin(retailMargin)
                .salesTax(salesTax)
                .includesTax(maineService.booleanToInt(includesTax))
                .build()
        );
    }

    public Optional<UserEntity> fineById(@NonNull int id) {
        return Optional.ofNullable(dAO.getUserById(id));
    }
}
