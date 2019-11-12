package com.jds.service;

import com.jds.model.auth.UserAuthentication;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class TokenAuthService {
    private static final String AUTH_HEADER_NAME = "X-Auth-Token";

    @Autowired
    private TokenHendler tokenHendler;
    @Autowired
    private UserService userService;

    public Optional<Authentication> getAuthentication(@NonNull HttpServletRequest request) {

        return  Optional
                .ofNullable(request.getHeader(AUTH_HEADER_NAME))
                        .flatMap(tokenHendler::extractUserId)
                        .flatMap(userService::fineById)
                        .map(UserAuthentication::new);
    }
}
