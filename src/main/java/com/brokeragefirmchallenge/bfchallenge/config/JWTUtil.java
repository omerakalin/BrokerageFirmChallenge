package com.brokeragefirmchallenge.bfchallenge.config;

import com.brokeragefirmchallenge.bfchallenge.persistence.entity.Token;
import com.brokeragefirmchallenge.bfchallenge.model.TokenStatus;
import com.brokeragefirmchallenge.bfchallenge.persistence.repository.TokenRepository;
import com.brokeragefirmchallenge.bfchallenge.persistence.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.brokeragefirmchallenge.bfchallenge.service.TokenService;

import java.time.LocalDateTime;
import java.util.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String jwtsecret;

    @Autowired
    private UserRepository userRepository;
    private final TokenRepository tokenRepository;
    @Autowired
    @Lazy
    private TokenService tokenService;
    public JWTUtil(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    //UUID-Based,OAuth2,Base64-Encoded,Key-Pair Signing types can be used
    public String generateOAuth2Token(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS256, jwtsecret)
                .compact();

        deactivateOldToken(userId);
        saveTokenToDb(token, userId);
        tokenService.updateTokenTable(userId);
        return token;
    }

    public boolean isTokenValid(String token) {
        Token dbToken = tokenRepository.findByToken(token);
        return dbToken != null && dbToken.getStatus() == TokenStatus.ACTIVE && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        Map<String, Object> claims = decodeJWT(token);
        Date expirationDate = new Date(((Number) claims.get("exp")).longValue() * 1000);
        return expirationDate.before(new Date());
    }

    public Long extractUserId(String token) {
        try {
            // Use parser() method instead of parserBuilder()
            JwtParser parser = Jwts.parser()
                    .setSigningKey(jwtsecret)
                    .build();

            // Parse the claims from the token
            Claims claims = parser.parseClaimsJws(token).getBody();

            // Extract the username from claims
            String username = claims.getSubject();

            // Use the findUserIdFromUsername method to find the userId
            return findUserIdFromUsername(username);

        } catch (Exception e) {
            return null;
        }
    }

    private void saveTokenToDb(String token, Long userId) {
        Token newToken = new Token(userId, token, TokenStatus.ACTIVE);
        tokenRepository.save(newToken);
    }

    private void deactivateOldToken(Long userId) {
        Pageable pageable = PageRequest.of(0, 1); // LIMIT 1 (latest token)
        List<Object[]> result = tokenRepository.findLatestTokenStatusAndUpdatedAtByUserId(userId, pageable);

        if (!result.isEmpty()) {
            Object[] latestToken = result.get(0);
            Long tokenId = (Long) latestToken[0];  // token id is the first element
            LocalDateTime updatedAt = (LocalDateTime) latestToken[2];  // updatedAt is the third element

            // Update the status and updatedAt for the latest token
            tokenRepository.updateTokenStatusAndUpdatedAtById(tokenId, TokenStatus.PASSIVE, updatedAt);
        }
    }

    private Map<String, Object> decodeJWT(String jwt) {
        try {
            String[] parts = jwt.split("\\.");
            String body = new String(Base64.getDecoder().decode(parts[1]));
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(body, Map.class);  // Parse JWT body into Map
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode JWT", e);
        }
    }

    public Long findUserIdFromUsername(String username) {
        // Fetch userId from Customer table first
        Long userId = userRepository.findUserIdByCustomerUsername(username);
        if (userId == null) {
            // If not found, check the CompanyUsers table
            userId = userRepository.findUserIdByCompanyUserUsername(username);
        }

        if (userId == null) {
            throw new RuntimeException("User not found for username: " + username);
        }

        return userId;
    }
}
