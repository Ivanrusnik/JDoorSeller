package com.jds.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jds.model.auth.Role;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
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
    List<DoorsОrder> orders;

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
