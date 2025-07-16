package com.hairbook.hairbook_backend.actuator;

import com.hairbook.hairbook_backend.repository.RefreshTokenRepository;
import com.hairbook.hairbook_backend.service.RefreshTokenService;
import com.hairbook.hairbook_backend.util.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Endpoint personnalisé Actuator exposé à {@code /actuator/refreshtokens}.
 * <p>
 * Ce composant fournit des statistiques en temps réel sur l'état des refresh tokens
 * utilisés pour l'authentification dans l'application Hairbook.
 * </p>
 *
 * <p>Les informations retournées incluent :</p>
 * <ul>
 *   <li>Nombre total de tokens</li>
 *   <li>Nombre de tokens actifs (non expirés et non révoqués)</li>
 *   <li>Nombre de tokens expirés</li>
 *   <li>Nombre de tokens révoqués</li>
 *   <li>Nombre d'utilisateurs uniques avec des tokens actifs</li>
 *   <li>Date de la dernière exécution du processus de nettoyage</li>
 * </ul>
 *
 * <p>En cas d'erreur, un message explicite est retourné.</p>
 *
 * @see org.springframework.boot.actuate.endpoint.annotation.Endpoint
 * @see org.springframework.boot.actuate.endpoint.annotation.ReadOperation
 */
@Component
@Endpoint(id = "refreshtokens")
public class RefreshTokenEndpoint {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    /**
     * Récupère les statistiques relatives aux refresh tokens.
     *
     * <p>Cette méthode est exposée via l'Actuator comme une opération de lecture.</p>
     *
     * @return une {@link Map} contenant les statistiques ou une erreur
     */
    @ReadOperation
    public Map<String, Object> refreshTokenStats() {
        try {
            Map<String, Object> stats = new HashMap<>();

            long totalTokens = refreshTokenRepository.count();
            stats.put("totalTokens", totalTokens);

            long activeTokens = refreshTokenRepository
                    .countByExpiryDateAfterAndRevokedFalse(Instant.now());
            stats.put("activeTokens", activeTokens);

            long expiredTokens = refreshTokenRepository
                    .countByExpiryDateBefore(Instant.now());
            stats.put("expiredTokens", expiredTokens);

            long revokedTokens = refreshTokenRepository.countByRevokedTrue();
            stats.put("revokedTokens", revokedTokens);

            long uniqueUsersWithActiveTokens = refreshTokenRepository
                    .countDistinctUserIdByExpiryDateAfterAndRevokedFalse(Instant.now());
            stats.put("activeUsers", uniqueUsersWithActiveTokens);

            stats.put("lastCleanupRun", refreshTokenService.getLastCleanupRun());

            LoggingService.logInfo(this.getClass(),
                    "Refresh token statistics retrieved via Actuator endpoint");

            return stats;
        } catch (Exception e) {
            LoggingService.logError(this.getClass(),
                    "Error retrieving refresh token statistics", e);

            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to retrieve refresh token statistics");
            error.put("message", e.getMessage());
            return error;
        }
    }
}
