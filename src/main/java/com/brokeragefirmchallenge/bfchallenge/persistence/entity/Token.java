package com.brokeragefirmchallenge.bfchallenge.persistence.entity;

import com.brokeragefirmchallenge.bfchallenge.model.TokenStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "token"})})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Unique identifier for each token
    private Long id;

    private Long userId; // User identifier
    private String token;
    private String username;

    @Enumerated(EnumType.STRING)
    private TokenStatus status; // "ACTIVE" or "PASSIVE"

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private static final Logger logger = Logger.getLogger(Token.class.getName());

    public Token(Long userId, String token, TokenStatus status) {
        this.userId = userId;
        this.token = token;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PostPersist
    public void logTokenDetails() {
        String logMessage = String.format(
                "TokenInfo: id: %s, userId: %s, username: %s, status: %s, token: %s, createdAt: %s",
                this.id,
                this.userId,
                this.username,
                this.status,
                this.token,
                this.createdAt
        );
        logger.info(logMessage);
    }
}
