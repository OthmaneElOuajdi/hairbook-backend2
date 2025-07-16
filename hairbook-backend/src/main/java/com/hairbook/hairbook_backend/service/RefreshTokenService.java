package com.hairbook.hairbook_backend.service;

import com.hairbook.hairbook_backend.entity.RefreshToken;
import com.hairbook.hairbook_backend.entity.User;
import com.hairbook.hairbook_backend.exception.TokenRefreshException;
import com.hairbook.hairbook_backend.repository.RefreshTokenRepository;
import com.hairbook.hairbook_backend.repository.UserRepository;
import com.hairbook.hairbook_backend.util.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Service de gestion des tokens de rafraîchissement (refresh tokens).
 * <p>
 * Ce service permet de créer, vérifier, révoquer, supprimer ou nettoyer automatiquement les tokens.
 */
@Service
public class RefreshTokenService {

    @Value("${hairbook.app.jwtRefreshExpirationMs:86400000}")
    private Long refreshTokenDurationMs;

    @Value("${hairbook.app.maxActiveSessions:5}")
    private int maxActiveSessions;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    private Instant lastCleanupRun = null;

    /**
     * Crée un nouveau refresh token ou met à jour un token existant pour un utilisateur
     * en fonction de l'appareil (userAgent) et de l'adresse IP.
     *
     * @param userId     ID de l'utilisateur
     * @param userAgent  informations sur l'appareil ou le navigateur
     * @param ipAddress  adresse IP du client
     * @return le token nouvellement créé ou mis à jour
     */
    @Transactional
    public RefreshToken createRefreshToken(Long userId, String userAgent, String ipAddress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new TokenRefreshException("Utilisateur non trouvé avec l'ID: " + userId));

        Optional<RefreshToken> existingToken = refreshTokenRepository
                .findByUserAndUserAgentAndIpAddressAndRevokedFalse(user, userAgent, ipAddress);

        if (existingToken.isPresent()) {
            RefreshToken token = existingToken.get();
            token.setToken(UUID.randomUUID().toString());
            token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            return refreshTokenRepository.save(token);
        }

        int activeSessions = refreshTokenRepository.countByUserIdAndRevokedFalse(userId);
        if (activeSessions >= maxActiveSessions) {
            LoggingService.logWarn(this.getClass(),
                    "L'utilisateur {} a atteint le nombre maximum de sessions actives ({})", userId, maxActiveSessions);
            revokeOldestToken(userId);
        }

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setUserAgent(userAgent);
        refreshToken.setIpAddress(ipAddress);
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Vérifie si le token est encore valide (non expiré et non révoqué).
     * Supprime le token si invalide.
     *
     * @param token le token à vérifier
     * @return le token si valide
     * @throws TokenRefreshException si le token est expiré ou révoqué
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.isExpired() || token.isRevoked()) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException("Le refresh token a expiré ou a été révoqué. Veuillez vous reconnecter.");
        }
        return token;
    }

    /**
     * Recherche un token par sa valeur.
     *
     * @param token la valeur du token
     * @return un {@link Optional} contenant le token s'il est trouvé
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Révoque tous les tokens d’un utilisateur.
     *
     * @param userId l’ID de l’utilisateur
     * @return le nombre de tokens révoqués
     */
    @Transactional
    public int revokeAllUserTokens(Long userId) {
        return refreshTokenRepository.revokeAllUserTokens(userId);
    }

    /**
     * Révoque un token spécifique par sa valeur.
     *
     * @param token la valeur du token
     * @return true si le token a été trouvé et révoqué, false sinon
     */
    @Transactional
    public boolean revokeToken(String token) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(token);

        if (refreshTokenOpt.isPresent()) {
            RefreshToken refreshToken = refreshTokenOpt.get();
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
            LoggingService.logInfo(this.getClass(), "Token révoqué pour l'utilisateur {}", refreshToken.getUser().getId());
            return true;
        }

        return false;
    }

    /**
     * Révoque le plus ancien token actif d’un utilisateur.
     *
     * @param userId l’ID de l’utilisateur
     */
    @Transactional
    private void revokeOldestToken(Long userId) {
        Optional<RefreshToken> oldestToken = refreshTokenRepository
                .findFirstByUserIdAndRevokedFalseOrderByExpiryDateAsc(userId);

        oldestToken.ifPresent(token -> {
            token.setRevoked(true);
            refreshTokenRepository.save(token);
            LoggingService.logInfo(this.getClass(), "Plus ancien token révoqué pour l'utilisateur {}", userId);
        });
    }

    /**
     * Supprime tous les tokens expirés.
     * Tâche exécutée automatiquement chaque jour à minuit.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void cleanupExpiredTokens() {
        LoggingService.logInfo(this.getClass(), "Nettoyage des refresh tokens expirés");
        int deletedCount = refreshTokenRepository.deleteByExpiryDateBefore(Instant.now());
        LoggingService.logInfo(this.getClass(), "{0} tokens expirés ont été supprimés", deletedCount);
        lastCleanupRun = Instant.now();
    }

    /**
     * Retourne la date du dernier nettoyage des tokens expirés.
     *
     * @return un objet {@link Instant} représentant la date/heure, ou {@code null} si jamais exécuté
     */
    public Instant getLastCleanupRun() {
        return lastCleanupRun;
    }

    /**
     * Supprime tous les tokens d’un utilisateur.
     *
     * @param userId l'ID de l'utilisateur
     */
    @Transactional
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
        LoggingService.logInfo(this.getClass(), "Tous les tokens de l'utilisateur {} ont été supprimés", userId);
    }

    /**
     * Crée un token de test pour un utilisateur (version sans userAgent/IP personnalisés).
     * <p>
     * Principalement utilisée pour les tests automatisés.
     *
     * @param userId ID de l’utilisateur
     * @return le token créé
     */
    @Transactional
    public RefreshToken createRefreshToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new TokenRefreshException("Utilisateur non trouvé avec l'ID: " + userId));

        int activeSessions = refreshTokenRepository.countByUserIdAndRevokedFalse(userId);
        if (activeSessions >= maxActiveSessions) {
            LoggingService.logWarn(this.getClass(),
                    "L'utilisateur {} a atteint le nombre maximum de sessions actives ({})", userId, maxActiveSessions);
            revokeOldestToken(userId);
        }

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setUserAgent("Test User Agent");
        refreshToken.setIpAddress("127.0.0.1");
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }
}
