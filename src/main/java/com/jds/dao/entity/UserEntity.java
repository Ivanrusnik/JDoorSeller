package com.jds.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jds.model.Role;
import com.jds.model.modelEnum.PriceGroups;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @JsonIgnore
    @Column(name = "password")
    String password;

    @Column(name = "login")
    private String username;

    @Column(name = "discount")
    private int discount;

    @Column(name = "price_group")
    @Enumerated(EnumType.STRING)
    private PriceGroups priceGroup;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @Transient
    private List<Role> authorities;

    @JsonIgnore
    @Transient
    private boolean accountNonExpired;

    @JsonIgnore
    @Transient
    private boolean accountNonLocked;

    @JsonIgnore
    @Transient
    private boolean credentialsNonExpired;

    @JsonIgnore
    @Transient
    private boolean enabled;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seller")
    private List<DoorsОrder> orders;

    @Transient
    int orderCounter;

    @Transient
    int workOrderCounter;

    public UserEntity() {
        authorities = new ArrayList<>();
        authorities.add(Role.USER);
    }

    public boolean isAdmin(){

        boolean isAdmin = false;
        for (Role role:authorities){
            if (role == Role.ADMIN){
                isAdmin = true;
            }
        }
        return isAdmin;
    }

}
