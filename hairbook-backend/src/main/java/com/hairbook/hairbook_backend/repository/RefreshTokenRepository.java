package com.hairbook.hairbook_backend.repository;

import com.hairbook.hairbook_backend.entity.RefreshToken;
import com.hairbook.hairbook_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité {@link RefreshToken}.
 * Gère les opérations liées à la gestion des tokens de rafraîchissement.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    /**
     * Recherche un token de rafraîchissement par sa valeur.
     *
     * @param token le token à rechercher
     * @return un {@link Optional} contenant le token s'il existe
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Récupère tous les tokens associés à un utilisateur.
     *
     * @param user l'utilisateur concerné
     * @return liste de {@link RefreshToken}
     */
    List<RefreshToken> findAllByUser(User user);

    /**
     * Révoque tous les tokens actifs d'un utilisateur.
     *
     * @param userId l'identifiant de l'utilisateur
     * @return le nombre de tokens mis à jour
     */
    @Modifying
    @Query("UPDATE RefreshToken r SET r.revoked = true WHERE r.user.id = :userId")
    int revokeAllUserTokens(@Param("userId") Long userId);

    /**
     * Supprime tous les tokens expirés.
     *
     * @param now la date actuelle
     * @return le nombre de tokens supprimés
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken r WHERE r.expiryDate < :now")
    int deleteByExpiryDateBefore(@Param("now") Instant now);

    /**
     * Supprime les tokens révoqués et expirés.
     *
     * @param date la date limite
     * @return le nombre de tokens supprimés
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken r WHERE r.revoked = true AND r.expiryDate < :date")
    int deleteByRevokedTrueAndExpiryDateBefore(@Param("date") Instant date);

    /**
     * Compte le nombre de tokens actifs (non expirés, non révoqués) pour un utilisateur.
     *
     * @param userId l'identifiant utilisateur
     * @param now    la date de comparaison
     * @return le nombre de tokens actifs
     */
    @Query("SELECT COUNT(r) FROM RefreshToken r WHERE r.user.id = :userId AND r.revoked = false AND r.expiryDate > :now")
    long countActiveTokensByUser(@Param("userId") Long userId, @Param("now") Instant now);

    /**
     * Recherche un token actif par utilisateur, user-agent et adresse IP.
     *
     * @param user       l'utilisateur
     * @param userAgent  l'agent utilisateur
     * @param ipAddress  l'adresse IP
     * @return le token correspondant s'il existe
     */
    Optional<RefreshToken> findByUserAndUserAgentAndIpAddressAndRevokedFalse(User user, String userAgent, String ipAddress);

    /**
     * Compte les tokens encore valides et non révoqués.
     *
     * @param date date de référence
     * @return nombre de tokens valides
     */
    long countByExpiryDateAfterAndRevokedFalse(Instant date);

    /**
     * Compte les tokens déjà expirés.
     *
     * @param date date de référence
     * @return nombre de tokens expirés
     */
    long countByExpiryDateBefore(Instant date);

    /**
     * Compte les tokens qui ont été révoqués.
     *
     * @return nombre de tokens révoqués
     */
    long countByRevokedTrue();

    /**
     * Compte le nombre d'utilisateurs uniques ayant au moins un token actif.
     *
     * @param now date actuelle
     * @return nombre d'utilisateurs distincts
     */
    @Query("SELECT COUNT(DISTINCT r.user.id) FROM RefreshToken r WHERE r.expiryDate > :now AND r.revoked = false")
    long countDistinctUserIdByExpiryDateAfterAndRevokedFalse(@Param("now") Instant now);

    /**
     * Compte le nombre de tokens actifs pour un utilisateur.
     *
     * @param userId l'identifiant de l'utilisateur
     * @return nombre de tokens actifs
     */
    int countByUserIdAndRevokedFalse(Long userId);

    /**
     * Supprime tous les tokens associés à un utilisateur.
     *
     * @param userId l'identifiant de l'utilisateur
     */
    @Modifying
    @Transactional
    void deleteByUserId(Long userId);

    /**
     * Récupère le prochain token actif (le plus ancien) d’un utilisateur.
     *
     * @param userId l'identifiant utilisateur
     * @return un {@link Optional} contenant le token s'il existe
     */
    Optional<RefreshToken> findFirstByUserIdAndRevokedFalseOrderByExpiryDateAsc(Long userId);
}
