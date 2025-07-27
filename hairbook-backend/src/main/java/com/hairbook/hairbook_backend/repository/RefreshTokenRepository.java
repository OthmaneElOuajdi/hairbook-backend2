package com.hairbook.hairbook_backend.repository;

import com.hairbook.hairbook_backend.entity.RefreshToken;
import com.hairbook.hairbook_backend.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Schema(description = "Repository pour l'entité RefreshToken - Gère les opérations liées à la gestion des tokens de rafraîchissement")
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Schema(description = "Recherche un token de rafraîchissement par sa valeur")
    Optional<RefreshToken> findByToken(String token);

    @Schema(description = "Récupère tous les tokens associés à un utilisateur")
    List<RefreshToken> findAllByUser(User user);

    @Schema(description = "Révoque tous les tokens actifs d'un utilisateur")
    @Modifying
    @Query("UPDATE RefreshToken r SET r.revoked = true WHERE r.user.id = :userId")
    int revokeAllUserTokens(@Param("userId") Long userId);
    
    @Schema(description = "Supprime tous les tokens d'un utilisateur")
    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken r WHERE r.user.id = :userId")
    int deleteAllUserTokens(@Param("userId") Long userId);

    @Schema(description = "Supprime tous les tokens expirés")
    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken r WHERE r.expiryDate < :now")
    int deleteByExpiryDateBefore(@Param("now") Instant now);

    @Schema(description = "Supprime les tokens révoqués et expirés")
    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken r WHERE r.revoked = true AND r.expiryDate < :date")
    int deleteByRevokedTrueAndExpiryDateBefore(@Param("date") Instant date);

    @Schema(description = "Compte le nombre de tokens actifs (non expirés, non révoqués) pour un utilisateur")
    @Query("SELECT COUNT(r) FROM RefreshToken r WHERE r.user.id = :userId AND r.revoked = false AND r.expiryDate > :now")
    long countActiveTokensByUser(@Param("userId") Long userId, @Param("now") Instant now);

    @Schema(description = "Recherche un token actif par utilisateur, user-agent et adresse IP")
    Optional<RefreshToken> findByUserAndUserAgentAndIpAddressAndRevokedFalse(User user, String userAgent, String ipAddress);

    @Schema(description = "Compte les tokens encore valides et non révoqués")
    long countByExpiryDateAfterAndRevokedFalse(Instant date);

    @Schema(description = "Compte les tokens déjà expirés")
    long countByExpiryDateBefore(Instant date);

    @Schema(description = "Compte les tokens qui ont été révoqués")
    long countByRevokedTrue();

    @Schema(description = "Compte le nombre d'utilisateurs uniques ayant au moins un token actif")
    @Query("SELECT COUNT(DISTINCT r.user.id) FROM RefreshToken r WHERE r.expiryDate > :now AND r.revoked = false")
    long countDistinctUserIdByExpiryDateAfterAndRevokedFalse(@Param("now") Instant now);

    @Schema(description = "Compte le nombre de tokens actifs pour un utilisateur")
    int countByUserIdAndRevokedFalse(Long userId);

    @Schema(description = "Supprime tous les tokens associés à un utilisateur")
    @Modifying
    @Transactional
    void deleteByUserId(Long userId);

    @Schema(description = "Récupère le prochain token actif (le plus ancien) d'un utilisateur")
    Optional<RefreshToken> findFirstByUserIdAndRevokedFalseOrderByExpiryDateAsc(Long userId);
}
