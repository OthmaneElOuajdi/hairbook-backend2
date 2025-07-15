package com.hairbook.hairbook_backend.dto.auth;

import jakarta.validation.constraints.NotBlank;

/**
 * Représente une requête de connexion contenant les identifiants d'un utilisateur.
 */
public class LoginRequest {

    /** Nom d'utilisateur ou email (ne doit pas être vide) */
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String username;

    /** Mot de passe (ne doit pas être vide) */
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    /**
     * Retourne le nom d'utilisateur ou email fourni par l'utilisateur.
     *
     * @return nom d'utilisateur
     */
    public String getUsername() {
        return username;
    }

    /**
     * Définit le nom d'utilisateur ou email à utiliser pour la connexion.
     *
     * @param username nom d'utilisateur
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retourne le mot de passe fourni par l'utilisateur.
     *
     * @return mot de passe
     */
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe à utiliser pour la connexion.
     *
     * @param password mot de passe
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
