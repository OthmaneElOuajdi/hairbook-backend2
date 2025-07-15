package com.hairbook.hairbook_backend.dto.auth;

/**
 * Réponse à une requête de rafraîchissement de token.
 * Fournit un nouveau token d'accès, un refresh token et des métadonnées associées.
 */
public class TokenRefreshResponse {

    /**
     * Le nouveau token d'accès (JWT).
     */
    private String accessToken;

    /**
     * Le nouveau refresh token.
     */
    private String refreshToken;

    /**
     * Le type de token, généralement "Bearer".
     */
    private String tokenType = "Bearer";

    /**
     * Durée de validité du token d'accès en secondes.
     */
    private Long expiresIn;

    /**
     * Constructeur par défaut.
     */
    public TokenRefreshResponse() {
    }

    /**
     * Constructeur avec tous les champs.
     *
     * @param accessToken  le token d'accès
     * @param refreshToken le refresh token
     * @param tokenType    le type de token (ex: "Bearer")
     * @param expiresIn    la durée de validité du token d'accès en secondes
     */
    public TokenRefreshResponse(String accessToken, String refreshToken, String tokenType, Long expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

    /**
     * Constructeur sans le type de token (valeur par défaut "Bearer").
     *
     * @param accessToken  le token d'accès
     * @param refreshToken le refresh token
     * @param expiresIn    la durée de validité du token d'accès en secondes
     */
    public TokenRefreshResponse(String accessToken, String refreshToken, Long expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
