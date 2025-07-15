package com.hairbook.hairbook_backend.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Intercepteur Spring permettant de journaliser les requêtes et réponses HTTP,
 * ainsi qu'une classe utilitaire pour effectuer différents types de logs (info, debug, warn, error)
 * avec ou sans formatage avancé.
 * <p>
 * Ce composant est automatiquement détecté par Spring via {@code @Component}.
 * Il implémente l'interface {@link HandlerInterceptor} pour capturer le cycle de vie des requêtes web.
 */
@Component
public class LoggingService implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);

    /**
     * Intercepte une requête HTTP avant son traitement par le contrôleur.
     * Log le verbe HTTP, l'URI, l'adresse IP, ainsi que les en-têtes et les paramètres si le niveau DEBUG est activé.
     *
     * @param request  la requête HTTP
     * @param response la réponse HTTP
     * @param handler  le handler (contrôleur) ciblé
     * @return {@code true} pour poursuivre la chaîne d'exécution
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("[REQUEST] {} {} - IP: {}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr());

        if (logger.isDebugEnabled()) {
            Map<String, String> headers = Collections.list(request.getHeaderNames())
                    .stream()
                    .collect(Collectors.toMap(
                            headerName -> headerName,
                            request::getHeader
                    ));
            logger.debug("Headers: {}", headers);

            Map<String, String[]> parameters = request.getParameterMap();
            if (!parameters.isEmpty()) {
                Map<String, String> params = parameters.entrySet()
                        .stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> Arrays.toString(e.getValue())
                        ));
                logger.debug("Parameters: {}", params);
            }
        }

        return true;
    }

    /**
     * Intercepte une requête après l'exécution du contrôleur mais avant le rendu de la vue.
     * Log la méthode HTTP, l'URI et le statut de la réponse.
     *
     * @param request      la requête HTTP
     * @param response     la réponse HTTP
     * @param handler      le handler (contrôleur) utilisé
     * @param modelAndView la vue générée (si applicable)
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        logger.info("[RESPONSE] {} {} - Status: {}", request.getMethod(), request.getRequestURI(), response.getStatus());
    }

    /**
     * Méthode appelée après le rendu de la vue. Log toute exception non capturée.
     *
     * @param request  la requête HTTP
     * @param response la réponse HTTP
     * @param handler  le handler (contrôleur)
     * @param ex       une éventuelle exception levée
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex != null) {
            logger.error("[EXCEPTION] {} {} - Exception: {}", request.getMethod(), request.getRequestURI(), ex.getMessage(), ex);
        }
    }

    /**
     * Log un message au niveau INFO avec prise en charge du format {@link MessageFormat} si applicable.
     *
     * @param clazz   la classe appelante
     * @param message le message à logguer
     * @param args    les arguments à insérer dans le message
     */
    public static void logInfo(Class<?> clazz, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(clazz);
        if (message.contains("{0}") || message.contains("{1}")) {
            logger.info(MessageFormat.format(message, args));
        } else {
            logger.info(message, args);
        }
    }

    /**
     * Log une erreur avec trace complète de l'exception.
     *
     * @param clazz     la classe appelante
     * @param message   le message d'erreur
     * @param throwable l'exception à logguer
     */
    public static void logError(Class<?> clazz, String message, Throwable throwable) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.error(message, throwable);
    }

    /**
     * Log une erreur avec message formaté. Gère les formats SLF4J ou MessageFormat.
     *
     * @param clazz   la classe appelante
     * @param message le message à logguer
     * @param args    les arguments à insérer
     */
    public static void logError(Class<?> clazz, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(clazz);
        if (message.contains("{0}") || message.contains("{1}")) {
            logger.error(MessageFormat.format(message, args));
        } else {
            logger.error(message, args);
        }
    }

    /**
     * Log un avertissement (niveau WARN) avec format dynamique.
     *
     * @param clazz   la classe appelante
     * @param message le message à logguer
     * @param args    les arguments à insérer
     */
    public static void logWarn(Class<?> clazz, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(clazz);
        if (message.contains("{0}") || message.contains("{1}")) {
            logger.warn(MessageFormat.format(message, args));
        } else {
            logger.warn(message, args);
        }
    }

    /**
     * Log un message en niveau DEBUG, utile pour les développeurs.
     *
     * @param clazz   la classe appelante
     * @param message le message à logguer
     * @param args    les arguments à insérer
     */
    public static void logDebug(Class<?> clazz, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(clazz);
        if (message.contains("{0}") || message.contains("{1}")) {
            logger.debug(MessageFormat.format(message, args));
        } else {
            logger.debug(message, args);
        }
    }

    /**
     * Journalise une exception avec un message personnalisé et trace complète.
     *
     * @param clazz   la classe appelante
     * @param message message d'erreur
     * @param e       exception à journaliser
     */
    public static void logException(Class<?> clazz, String message, Exception e) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.error(message + ": " + e.getMessage(), e);
    }

    /**
     * Journalise une action utilisateur importante avec identifiant, action et détails.
     *
     * @param clazz   la classe appelante
     * @param userId  identifiant de l'utilisateur
     * @param action  action réalisée
     * @param details détails supplémentaires
     */
    public static void logUserAction(Class<?> clazz, Long userId, String action, String details) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.info("User [{}] - Action: {} - Details: {}", userId, action, details);
    }
}
