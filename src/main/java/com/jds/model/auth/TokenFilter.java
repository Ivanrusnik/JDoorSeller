package com.jds.model.auth;

import com.jds.entity.UserEntity;
import com.jds.model.auth.UserAuthentication;
import com.jds.service.TokenAuthService;
import com.jds.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class TokenFilter extends GenericFilterBean {

    private final TokenAuthService tokenAuthService;

    public TokenFilter(@NonNull TokenAuthService tokenAuthService){
        this.tokenAuthService = tokenAuthService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        SecurityContextHolder.getContext().setAuthentication(
                tokenAuthService.getAuthentication((HttpServletRequest)servletRequest).orElse(null));

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
