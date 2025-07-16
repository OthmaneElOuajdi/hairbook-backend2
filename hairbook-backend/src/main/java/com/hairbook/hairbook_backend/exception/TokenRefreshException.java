package com.hairbook.hairbook_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception levée lorsqu'une tentative de rafraîchissement de jeton (token) échoue.
 * 
 * Cette exception est mappée sur le code HTTP 403 FORBIDDEN, ce qui signifie que
 * le client n'a pas l'autorisation d'accéder à la ressource même s'il est authentifié.
 *
 * Exemple de cas d'utilisation :
 * - Jeton expiré ou invalide
 * - Jeton déjà utilisé
 * - Jeton non reconnu
 * 
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TokenRefreshException(String message) {
        super(message);
    }
}
