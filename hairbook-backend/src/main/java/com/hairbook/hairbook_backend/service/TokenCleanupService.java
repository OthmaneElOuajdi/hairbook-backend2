package com.hairbook.hairbook_backend.service;

import com.hairbook.hairbook_backend.repository.RefreshTokenRepository;
import com.hairbook.hairbook_backend.util.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Service responsable de la suppression automatique des jetons de rafraîchissement (refresh tokens)
 * expirés ou révoqués, afin de libérer de l’espace et renforcer la sécurité.
 * <p>
 * Les méthodes sont exécutées à intervalle régulier grâce à l’annotation {@link Scheduled}.
 */
@Service
public class TokenCleanupService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    /**
     * Supprime les tokens expirés de la base de données.
     * <p>
     * Cette tâche s'exécute automatiquement tous les jours à minuit (heure serveur).
     * Elle supprime tous les tokens dont la date d'expiration est antérieure à maintenant.
     */
    @Scheduled(cron = "0 0 0 * * ?") // Tous les jours à minuit
    @Transactional
    public void cleanupExpiredTokens() {
        LoggingService.logInfo(this.getClass(), "Démarrage du nettoyage des tokens expirés");

        try {
            int deletedCount = refreshTokenRepository.deleteByExpiryDateBefore(Instant.now());
            LoggingService.logInfo(this.getClass(), "{0} tokens expirés ont été supprimés", deletedCount);
        } catch (Exception e) {
            LoggingService.logError(this.getClass(), "Erreur lors du nettoyage des tokens expirés: {0}", e);
        }
    }

    /**
     * Supprime les tokens révoqués et expirés depuis plus de 30 jours.
     * <p>
     * Cette tâche s’exécute automatiquement chaque dimanche à minuit.
     * Elle supprime les tokens révoqués dont la date d’expiration est antérieure à 30 jours.
     */
    @Scheduled(cron = "0 0 0 ? * SUN") // Tous les dimanches à minuit
    @Transactional
    public void cleanupRevokedTokens() {
        LoggingService.logInfo(this.getClass(), "Démarrage du nettoyage des tokens révoqués");

        try {
            Instant thirtyDaysAgo = Instant.now().minusSeconds(30L * 24 * 60 * 60);
            int deletedCount = refreshTokenRepository.deleteByRevokedTrueAndExpiryDateBefore(thirtyDaysAgo);
            LoggingService.logInfo(this.getClass(), "{0} tokens révoqués ont été supprimés", deletedCount);
        } catch (Exception e) {
            LoggingService.logError(this.getClass(), "Erreur lors du nettoyage des tokens révoqués: {0}", e);
        }
    }
}
