package com.hairbook.hairbook_backend.util;

import io.swagger.v3.oas.annotations.media.Schema;
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

@Schema(description = "Intercepteur Spring permettant de journaliser les requêtes et réponses HTTP, ainsi qu'une classe utilitaire pour effectuer différents types de logs avec formatage avancé")
@Component
public class LoggingService implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);

    @Schema(description = "Intercepte une requête HTTP avant son traitement par le contrôleur - Log le verbe HTTP, l'URI, l'adresse IP")
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

    @Schema(description = "Intercepte une requête après l'exécution du contrôleur mais avant le rendu de la vue - Log la méthode HTTP, l'URI et le statut")
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        logger.info("[RESPONSE] {} {} - Status: {}", request.getMethod(), request.getRequestURI(), response.getStatus());
    }

    @Schema(description = "Méthode appelée après le rendu de la vue - Log toute exception non capturée")
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex != null) {
            logger.error("[EXCEPTION] {} {} - Exception: {}", request.getMethod(), request.getRequestURI(), ex.getMessage(), ex);
        }
    }

    @Schema(description = "Log un message au niveau INFO avec prise en charge du format MessageFormat si applicable")
    public static void logInfo(Class<?> clazz, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(clazz);
        if (message.contains("{0}") || message.contains("{1}")) {
            logger.info(MessageFormat.format(message, args));
        } else {
            logger.info(message, args);
        }
    }

    @Schema(description = "Log une erreur avec trace complète de l'exception")
    public static void logError(Class<?> clazz, String message, Throwable throwable) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.error(message, throwable);
    }

    @Schema(description = "Log une erreur avec message formaté - Gère les formats SLF4J ou MessageFormat")
    public static void logError(Class<?> clazz, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(clazz);
        if (message.contains("{0}") || message.contains("{1}")) {
            logger.error(MessageFormat.format(message, args));
        } else {
            logger.error(message, args);
        }
    }

    @Schema(description = "Log un avertissement (niveau WARN) avec format dynamique")
    public static void logWarn(Class<?> clazz, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(clazz);
        if (message.contains("{0}") || message.contains("{1}")) {
            logger.warn(MessageFormat.format(message, args));
        } else {
            logger.warn(message, args);
        }
    }

    @Schema(description = "Log un message en niveau DEBUG, utile pour les développeurs")
    public static void logDebug(Class<?> clazz, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(clazz);
        if (message.contains("{0}") || message.contains("{1}")) {
            logger.debug(MessageFormat.format(message, args));
        } else {
            logger.debug(message, args);
        }
    }

    @Schema(description = "Journalise une exception avec un message personnalisé et trace complète")
    public static void logException(Class<?> clazz, String message, Exception e) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.error(message + ": " + e.getMessage(), e);
    }

    @Schema(description = "Journalise une action utilisateur importante avec identifiant, action et détails")
    public static void logUserAction(Class<?> clazz, Long userId, String action, String details) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.info("User [{}] - Action: {} - Details: {}", userId, action, details);
    }
}
