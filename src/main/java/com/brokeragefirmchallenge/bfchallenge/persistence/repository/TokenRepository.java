package com.brokeragefirmchallenge.bfchallenge.persistence.repository;

import com.brokeragefirmchallenge.bfchallenge.persistence.entity.Token;
import com.brokeragefirmchallenge.bfchallenge.model.TokenStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Logger logger = Logger.getLogger(TokenRepository.class.getName());

    @Query("SELECT t FROM Token t WHERE t.token = :token AND t.status = 'ACTIVE'")
    Token findByToken(@Param("token") String token);


    @Transactional
    @Modifying
    @Query("UPDATE Token t SET t.status = :status WHERE t.userId = :userId")
    void updateTokenStatusByUserId(@Param("userId") Long userId, @Param("status") TokenStatus status);

    @Query("SELECT t FROM Token t WHERE t.userId = :userId AND t.status = 'ACTIVE' ORDER BY t.id DESC")
    Token findLatestActiveTokenByUserId(@Param("userId") Long userId);


    @Query("SELECT t.id, t.status, t.updatedAt FROM Token t WHERE t.userId = :userId ORDER BY t.id DESC")
    List<Object[]> findLatestTokenStatusAndUpdatedAtByUserId(@Param("userId") Long userId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Token t SET t.status = :status, t.updatedAt = :updatedAt WHERE t.id = :tokenId")
    void updateTokenStatusAndUpdatedAtById(@Param("tokenId") Long tokenId,
                                           @Param("status") TokenStatus status,
                                           @Param("updatedAt") LocalDateTime updatedAt);


    default void logTokenDetails(Long userId) {
        Token token = findById(userId).orElse(null);
        if (token != null) {
            String logMessage = String.format(
                    "TokenINFO: user-id: %d , created_at: %s , status: %s , username: %s , Token: %s",
                    token.getUserId(),
                    token.getCreatedAt(),
                    token.getStatus(),
                    token.getUsername(),
                    token.getToken()
            );
            logger.info(logMessage);
        }
    }

    @Modifying
    @Query(nativeQuery = true, value = """
        DELETE FROM token
        WHERE id NOT IN (
            SELECT id
            FROM (
                SELECT id
                FROM token
                WHERE user_id = :userId
                ORDER BY id DESC
                LIMIT 2
            ) subquery
        ) AND user_id = :userId
    """)
    void keepOnlyLastTwoTokens(@Param("userId") Long userId);
}
