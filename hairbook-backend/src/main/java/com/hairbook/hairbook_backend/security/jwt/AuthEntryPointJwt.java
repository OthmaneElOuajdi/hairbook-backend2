package com.hairbook.hairbook_backend.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Point d'entrée d'authentification personnalisé pour gérer les erreurs
 * lorsque l'utilisateur tente d'accéder à une ressource sécurisée sans être authentifié.
 * 
 * Cette classe est utilisée par Spring Security pour renvoyer une réponse
 * HTTP 401 (Unauthorized) sous forme JSON lorsqu'une tentative d'accès est refusée.
 * 
 * Elle est généralement déclenchée lorsqu'un JWT est manquant, expiré, ou invalide.
 * 
 * Exemple de réponse JSON :
 * <pre>
 * {
 *   "status": 401,
 *   "error": "Non autorisé",
 *   "message": "Message d'erreur",
 *   "path": "/chemin/requete"
 * }
 * </pre>
 * 
 * @author ...
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    /**
     * Méthode appelée lorsqu'une tentative d'accès à une ressource sécurisée
     * échoue à cause d'une authentification manquante ou invalide.
     *
     * @param request la requête HTTP d'origine
     * @param response la réponse HTTP qui sera envoyée au client
     * @param authException l'exception déclenchée lors de l'échec d'authentification
     * @throws IOException si une erreur d'entrée/sortie survient
     * @throws ServletException si une erreur liée au servlet survient
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        logger.error("Erreur d'authentification: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Non autorisé");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
