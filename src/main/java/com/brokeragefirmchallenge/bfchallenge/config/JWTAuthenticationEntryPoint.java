package com.brokeragefirmchallenge.bfchallenge.config;

import com.brokeragefirmchallenge.bfchallenge.model.TokenStatus;
import com.brokeragefirmchallenge.bfchallenge.persistence.entity.Token;
import com.brokeragefirmchallenge.bfchallenge.persistence.repository.TokenRepository;
import com.brokeragefirmchallenge.bfchallenge.persistence.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;

import java.io.IOException;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private TokenRepository tokenRepository;  // Inject TokenRepository to interact with the database

    @Autowired
    private UserRepository userRepository;  // Inject UserRepository to fetch the userId

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String message = "Unauthorized";
        String unauthorizedToken = request.getHeader("Authorization");

        if (unauthorizedToken == null) {
            message = "No Token Provided";
            unauthorizedToken = "null";  // Token is missing
        } else {
            String tokenValue = unauthorizedToken.startsWith("Bearer ") ? unauthorizedToken.substring(7) : null;

            if (tokenValue != null) {
                // Check if the token is valid and active in the database
                Token validToken = tokenRepository.findByToken(tokenValue);
                if (validToken != null && validToken.getStatus() == TokenStatus.ACTIVE) {
                    message = "Authorized Token Provided";
                } else {
                    message = "Unauthorized Token Provided";
                }
            }
        }

        // Send both unauthorized and the validated token in the response
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 status code
        response.getWriter().write(String.format(
                "{ \"message\": \"%s\", \"Unauthorized Token\": \"%s\" }",
                message, unauthorizedToken
        ));

        // Log the tokens for debugging purposes
        System.out.println("Sent Token: " + unauthorizedToken);
    }
}
