package com.hairbook.hairbook_backend.dto.auth;

import jakarta.validation.constraints.NotBlank;

/**
 * Requête de rafraîchissement de token JWT.
 * Utilisée pour demander un nouveau token d'accès à l'aide d'un refresh token valide.
 */
public class TokenRefreshRequest {

    /**
     * Token de rafraîchissement (obligatoire)
     */
    @NotBlank(message = "Le refresh token est obligatoire")
    private String refreshToken;

    /**
     * Constructeur par défaut.
     */
    public TokenRefreshRequest() {
    }

    /**
     * Constructeur avec paramètre.
     *
     * @param refreshToken le token de rafraîchissement
     */
    public TokenRefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Récupère le token de rafraîchissement.
     *
     * @return le refresh token
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Définit le token de rafraîchissement.
     *
     * @param refreshToken le refresh token à définir
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
