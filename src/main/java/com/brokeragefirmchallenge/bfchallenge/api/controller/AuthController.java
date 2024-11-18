package com.brokeragefirmchallenge.bfchallenge.api.controller;

import com.brokeragefirmchallenge.bfchallenge.config.CredentialsConfig;
import com.brokeragefirmchallenge.bfchallenge.config.JWTUtil;
import com.brokeragefirmchallenge.bfchallenge.persistence.entity.CompanyUsers;
import com.brokeragefirmchallenge.bfchallenge.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final JWTUtil jwtUtil;
    private final CredentialsConfig credentialsConfig;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(JWTUtil jwtUtil, CredentialsConfig credentialsConfig,
                          AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.credentialsConfig = credentialsConfig;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // Check in CompanyUsers table
            CompanyUsers user = userRepository.findByUsername(authRequest.getUsername());
            if (user != null && user.getPassword().equals(authRequest.getPassword())) {
                String token = jwtUtil.generateOAuth2Token(user.getId());
                return ResponseEntity.ok(new AuthResponse(token));
            }

            // Check in Customers table
            Long customerId = userRepository.findUserIdByCustomerUsername(authRequest.getUsername());
            if (customerId != null) {
                String token = jwtUtil.generateOAuth2Token(customerId);
                return ResponseEntity.ok(new AuthResponse(token));
            }

            // Check userId in CompanyUsers table
            Long companyUserId = userRepository.findUserIdByCompanyUserUsername(authRequest.getUsername());
            if (companyUserId != null) {
                String token = jwtUtil.generateOAuth2Token(companyUserId);
                return ResponseEntity.ok(new AuthResponse(token));
            }

            // No user found
            logger.warn("Invalid credentials attempt for username: {}", authRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");

        } catch (NullPointerException e) {
            logger.warn("Null value encountered: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            logger.error("Authentication failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication Failed: " + e.getMessage());
        }
    }



    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthRequest {
        private String username;
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthResponse {
        private String token;
    }
}
