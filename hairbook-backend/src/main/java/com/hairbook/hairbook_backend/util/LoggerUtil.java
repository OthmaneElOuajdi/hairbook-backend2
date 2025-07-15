package com.hairbook.hairbook_backend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe utilitaire pour simplifier et centraliser la journalisation des événements,
 * actions utilisateurs, erreurs et événements système au sein de l'application.
 * <p>
 * Cette classe repose sur l'API SLF4J pour fournir une interface de logging unifiée.
 * Elle propose des méthodes pour créer des loggers et écrire des messages formatés
 * dans les logs selon le type d'événement.
 */
public class LoggerUtil {

    /**
     * Crée un logger SLF4J pour la classe spécifiée.
     *
     * @param clazz la classe pour laquelle le logger est destiné
     * @return un objet {@link Logger} associé à la classe
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * Journalise une action effectuée par un utilisateur.
     * <p>
     * Le message est enregistré au niveau INFO, incluant l'identifiant utilisateur,
     * l'action réalisée et les détails supplémentaires.
     *
     * @param logger  le logger à utiliser
     * @param userId  l'identifiant de l'utilisateur
     * @param action  l'action effectuée (ex. : "Connexion", "Mise à jour du profil")
     * @param details détails supplémentaires sur l'action (ex. : "succès", "échec")
     */
    public static void logUserAction(Logger logger, Long userId, String action, String details) {
        logger.info("User [{}] - {} : {}", userId, action, details);
    }

    /**
     * Journalise une erreur dans un contexte donné.
     * <p>
     * Le message est enregistré au niveau ERROR avec des informations sur le contexte,
     * l'erreur et l'exception levée.
     *
     * @param logger    le logger à utiliser
     * @param context   contexte ou composant dans lequel l'erreur est survenue
     * @param error     message d'erreur descriptif
     * @param exception exception associée à l'erreur
     */
    public static void logError(Logger logger, String context, String error, Throwable exception) {
        logger.error("Error in {} : {} - Exception: {}", context, error, exception.getMessage());
    }

    /**
     * Journalise un événement lié au fonctionnement du système.
     * <p>
     * Le message est enregistré au niveau INFO, utile pour tracer l'activité
     * de composants techniques comme des planificateurs, des services internes, etc.
     *
     * @param logger    le logger à utiliser
     * @param component nom du composant système concerné (ex. : "Scheduler", "MailService")
     * @param event     description de l'événement (ex. : "Tâche exécutée", "Service démarré")
     */
    public static void logSystemEvent(Logger logger, String component, String event) {
        logger.info("System [{}] - {}", component, event);
    }
}
