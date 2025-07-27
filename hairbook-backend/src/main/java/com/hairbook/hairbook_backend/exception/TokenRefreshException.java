package com.hairbook.hairbook_backend.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Schema(description = "Exception levée lorsqu'une tentative de rafraîchissement de jeton (token) échoue - Mappée sur le code HTTP 403 FORBIDDEN pour les jetons expirés, invalides, déjà utilisés ou non reconnus")
@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TokenRefreshException(String message) {
        super(message);
    }
}
