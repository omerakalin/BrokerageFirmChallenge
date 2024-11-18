package com.brokeragefirmchallenge.bfchallenge.service;

import com.brokeragefirmchallenge.bfchallenge.config.JWTUtil;
import com.brokeragefirmchallenge.bfchallenge.persistence.entity.Token;
import com.brokeragefirmchallenge.bfchallenge.model.TokenStatus;
import com.brokeragefirmchallenge.bfchallenge.persistence.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final JWTUtil jwtUtil;
    private final TokenRepository tokenRepository;


    @Autowired
    public TokenService(JWTUtil jwtUtil, TokenRepository tokenRepository) {
        this.jwtUtil = jwtUtil;
        this.tokenRepository = tokenRepository;

    }

    public String generateNewToken(String username) {
        return jwtUtil.generateOAuth2Token(jwtUtil.findUserIdFromUsername(username));
    }

    public void deactivatePreviousTokens(String username) {
        // Get the userId from the username using the findUserIdFromUsername method
        Long userId = jwtUtil.findUserIdFromUsername(username);

        // Deactivate previous tokens by userId
        tokenRepository.updateTokenStatusByUserId(userId, TokenStatus.PASSIVE);
    }


    public void updateTokenTable(Long userId) {
        tokenRepository.updateTokenStatusByUserId(userId, TokenStatus.PASSIVE);
        tokenRepository.keepOnlyLastTwoTokens(userId);
    }

    public Token createToken(String username) {
        deactivatePreviousTokens(username);
        String newToken = generateNewToken(username);
        Token token = new Token(jwtUtil.extractUserId(username), newToken, TokenStatus.ACTIVE);
        return tokenRepository.save(token);
    }

}
