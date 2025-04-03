package com.kubgtu.car_school.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class SecurityContextService {

    public UUID getCurrentUserUuid(){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userIdString = jwt.getClaim("sub");
        return UUID.fromString(userIdString);
    }
}
