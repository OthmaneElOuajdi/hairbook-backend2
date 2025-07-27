package com.hairbook.hairbook_backend.util;

import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Schema(description = "Classe utilitaire pour simplifier et centraliser la journalisation des événements, actions utilisateurs, erreurs et événements système au sein de l'application")
public class LoggerUtil {

    @Schema(description = "Crée un logger SLF4J pour la classe spécifiée")
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    @Schema(description = "Journalise une action effectuée par un utilisateur - Le message est enregistré au niveau INFO")
    public static void logUserAction(Logger logger, Long userId, String action, String details) {
        logger.info("User [{}] - {} : {}", userId, action, details);
    }

    @Schema(description = "Journalise une erreur dans un contexte donné - Le message est enregistré au niveau ERROR")
    public static void logError(Logger logger, String context, String error, Throwable exception) {
        logger.error("Error in {} : {} - Exception: {}", context, error, exception.getMessage());
    }

    @Schema(description = "Journalise un événement lié au fonctionnement du système - Utile pour tracer l'activité de composants techniques")
    public static void logSystemEvent(Logger logger, String component, String event) {
        logger.info("System [{}] - {}", component, event);
    }
}
