package com.brokeragefirmchallenge.bfchallenge.api.controller;

import com.brokeragefirmchallenge.bfchallenge.service.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/api/token")
    public String getToken(@RequestParam String username) {
        return tokenService.generateNewToken(username);
    }
}
