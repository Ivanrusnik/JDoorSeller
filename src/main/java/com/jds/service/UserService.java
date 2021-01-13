package com.jds.service;


import com.jds.dao.repository.OrderDAO;
import com.jds.dao.repository.UserDAO;
import com.jds.dao.entity.DoorsОrder;
import com.jds.dao.entity.UserEntity;
import com.jds.dao.entity.UserSetting;
import com.jds.model.Role;

import com.jds.model.modelEnum.PriceGroups;
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
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService, UserServ {

    @Autowired
    private UserDAO dAO;
    @Autowired
    private OrderDAO OrderdAO;
    @Autowired
    private MaineService maineService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userHoldder(username);
    }

    public UserEntity userHoldder(String username) {


        if (username.equals("admin")) {

            List<Role> roleList = new ArrayList<>();
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
                    .priceGroup(PriceGroups.RETAIL_PRICE)
                    .build();
        }

        UserEntity user = dAO.getUserByName(username);
        if (user != null) {
            List<Role> roleList = new ArrayList<>();
            roleList.add(user.getRole());

            user.setAuthorities(roleList);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);
        }
        return user;
    }

    public List<UserEntity> getUsers() {

        return setUpUsersForView(dAO.getUsers());

    }

    private List<UserEntity> setUpUsersForView(List<UserEntity> list) {

        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.USER);

        list.stream()
                .peek((user) -> setEnabledAndRole(user, roleList))
                .peek((user) -> setOrderСounters(user))
                .collect(Collectors.toList());

        list.add(userHoldder("admin"));

        return list;
    }

    private void setOrderСounters(UserEntity user) {
        List<DoorsОrder> orders = OrderdAO.getOrdersByUser(user);


        user.setOrderCounter((int) orders.stream()
                .count()
        );

        user.setWorkOrderCounter((int) orders.stream()
                .filter(order -> order.isWorking())
                .count()
        );
    }

    public void saveUser(@NonNull String userId,
                         @NonNull String username,
                         @NonNull String password,
                         int discount,
                         boolean enabledСheckbox,
                         PriceGroups priceGroups,
                         Role role) {

        if (userId == "0" && (username == "" || password == "")) {
            throw new IllegalArgumentException("username or password in saveUser can not be empty!");
        } else if (userId != "0" && password == ""){
            password = dAO.getUser(Integer.parseInt(userId)).getPassword();
        }

        List<Role> roleList = new ArrayList<>();
        roleList.add(role);

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
                .priceGroup(priceGroups)
                .role(role)
                .build());
    }

    public String insertPassword(String pass) {
        if (pass == "") {
            return getCurrentUser().getPassword();
        } else {
            return new BCryptPasswordEncoder().encode(pass);
        }
    }

    public UserEntity getUser(@NonNull String userId) {

        int idInt = Integer.parseInt(userId);


        if (idInt == 9300) {
            return userHoldder("admin");
        } else if (idInt == 0) {
            return UserEntity.builder()
                    .username("newUser")
                    .build();
        }

        UserEntity user = dAO.getUser(idInt);
        setOrderСounters(user);

        return user;

    }

    public void setEnabledAndRole(UserEntity user, List<Role> roleList) {
        user.setAuthorities(roleList);
        user.setEnabled(true);
    }

    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }

    public UserSetting getUserSetting() {
        return dAO.getUserSetting(getCurrentUser().getId());
    }

    public void saveUserSetting(int retailMargin, int salesTax, boolean includesTax) {
        dAO.saveUserSetting(
                UserSetting.builder()
                        .id(getCurrentUser().getId())
                        .retailMargin(retailMargin)
                        .salesTax(salesTax)
                        .includesTax(maineService.booleanToInt(includesTax))
                        .build()
        );
    }

    public Set<Role> getRoles() {
        return EnumSet.allOf(Role.class);
    }
}
