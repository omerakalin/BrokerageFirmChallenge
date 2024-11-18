package com.brokeragefirmchallenge.bfchallenge.config;

import com.brokeragefirmchallenge.bfchallenge.model.TokenStatus;
import com.brokeragefirmchallenge.bfchallenge.persistence.entity.Token;
import com.brokeragefirmchallenge.bfchallenge.persistence.repository.TokenRepository;
import com.brokeragefirmchallenge.bfchallenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserService userService;  // Service to fetch roles

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);

            Token dbToken = tokenRepository.findByToken(jwtToken);
            if (dbToken == null || dbToken.getStatus() != TokenStatus.ACTIVE) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized Token Provided");
                return;
            }

            Long userId = jwtUtil.extractUserId(jwtToken);
            if (userId != null) {
                String role = userService.getUserRoleByUserId(userId);
                if ("ADMIN".equals(role) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(userId, null, List.of())
                    );
                }
            }
        }

        chain.doFilter(request, response);
    }
}
