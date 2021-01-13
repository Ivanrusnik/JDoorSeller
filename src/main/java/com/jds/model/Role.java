package com.jds.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,ADMIN,ONE_C;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
