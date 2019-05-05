package com.alan.developer.demoreactivew.web;

import com.alan.developer.demoreactivew.model.TokenJwt;
import com.alan.developer.demoreactivew.model.UserCredentials;
import com.alan.developer.demoreactivew.service.JwtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/security/v1")
public class SecurityController {

    private JwtService jwtService;

    public SecurityController(JwtService js) {
        this.jwtService = js;
    }

    @PostMapping(value = "token")
    public TokenJwt login(final UserCredentials userCredentials) {
        return TokenJwt.builder().token(jwtService.getJwt(userCredentials)).build();
    }
}
