package com.hairbook.hairbook_backend.service;

import com.hairbook.hairbook_backend.dto.ai.HairSimulationRequest;
import com.hairbook.hairbook_backend.dto.ai.HairSimulationResponse;
import com.hairbook.hairbook_backend.util.LoggerUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Service chargé de simuler une coiffure à l’aide d’une API d’intelligence artificielle externe.
 * <p>
 * Ce service prend en entrée une image (encodée en base64) et des préférences capillaires
 * (style, couleur, instructions) et retourne une image générée simulant le résultat.
 * <p>
 * L’appel à l’API distante est pour l’instant simulé. En production, le code
 * est prêt à utiliser une instance de {@code RestTemplate}.
 */
@Service
public class AiHairSimulationService {

    private static final Logger logger = LoggerUtil.getLogger(AiHairSimulationService.class);

    @Value("${hairbook.ai.api-url:https://api.hairsimulation.ai/generate}")
    private String aiApiUrl;

    @Value("${hairbook.ai.api-key:}")
    private String aiApiKey;

    /**
     * Constructeur par défaut.
     */
    public AiHairSimulationService() {
        // Par défaut, rien à initialiser pour l’instant
    }

    /**
     * Simule une coiffure à partir d'une image et de préférences fournies par l'utilisateur.
     *
     * @param request Un objet contenant l'image en base64, le style de coiffure,
     *                la couleur, et d’éventuelles instructions supplémentaires.
     * @return Un objet {@link HairSimulationResponse} contenant l’image simulée (en base64),
     * un identifiant de traitement, un indicateur de succès, et un message utilisateur.
     */
    public HairSimulationResponse simulateHairStyle(HairSimulationRequest request) {
        try {
            logger.info("Démarrage de la simulation de coiffure");

            // Vérifie la configuration de la clé API
            if (aiApiKey == null || aiApiKey.isEmpty()) {
                logger.error("Clé API pour la simulation de coiffure non configurée");
                return new HairSimulationResponse(
                        null,
                        null,
                        false,
                        "Service de simulation non configuré. Contactez l'administrateur."
                );
            }

            // Préparation des en-têtes pour la requête
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-API-Key", aiApiKey);

            // Construction du corps de la requête
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("image", request.getImageBase64());
            requestBody.put("hairStyle", request.getHairStyle());
            requestBody.put("hairColor", request.getHairColor());
            requestBody.put("instructions", request.getAdditionalInstructions());

            // Exemple de code pour appel réel à une API REST (commenté volontairement)
            // HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            // RestTemplate restTemplate = new RestTemplate();
            // Map<String, Object> response = restTemplate.postForObject(aiApiUrl, entity, Map.class);

            // Simulation d'une réponse API
            String processId = UUID.randomUUID().toString();
            logger.info("Simulation de coiffure terminée avec succès, processId: {}", processId);

            return new HairSimulationResponse(
                    "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD...", // Image simulée fictive
                    processId,
                    true,
                    "Simulation réussie"
            );

        } catch (Exception e) {
            logger.error("Erreur lors de la simulation de coiffure", e);
            return new HairSimulationResponse(
                    null,
                    null,
                    false,
                    "Erreur lors de la simulation: " + e.getMessage()
            );
        }
    }
}
