package com.hairbook.hairbook_backend.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Réponse à une requête de rafraîchissement de token contenant les nouveaux tokens")
public class TokenRefreshResponse {

    @Schema(description = "Le nouveau token d'accès (JWT)", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "Le nouveau refresh token", example = "550e8400-e29b-41d4-a716-446655440001")
    private String refreshToken;

    @Schema(description = "Le type de token", example = "Bearer", defaultValue = "Bearer")
    private String tokenType = "Bearer";

    @Schema(description = "Durée de validité du token d'accès en secondes", example = "3600")
    private Long expiresIn;

    public TokenRefreshResponse() {
    }

    public TokenRefreshResponse(String accessToken, String refreshToken, String tokenType, Long expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

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
