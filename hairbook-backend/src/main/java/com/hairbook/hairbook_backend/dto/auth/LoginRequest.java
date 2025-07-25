package com.hairbook.hairbook_backend.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Requête de connexion contenant les identifiants d'un utilisateur")
public class LoginRequest {

    @Schema(description = "Nom d'utilisateur ou email", example = "johndoe", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String username;

    @Schema(description = "Mot de passe", example = "motdepasse123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
