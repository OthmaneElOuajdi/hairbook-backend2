package com.hairbook.hairbook_backend.dto.auth;

import java.util.List;

/**
 * Représente la réponse envoyée après une authentification réussie.
 * Contient les informations sur le token JWT, l'utilisateur, et ses rôles.
 */
public class JwtResponse {

    /** Token d'accès JWT */
    private String token;

    /** Token de rafraîchissement */
    private String refreshToken;

    /** Type du token, par défaut "Bearer" */
    private String type = "Bearer";

    /** Durée de validité du token en secondes */
    private Long expiresIn;

    /** Identifiant de l'utilisateur */
    private Long id;

    /** Nom d'utilisateur */
    private String username;

    /** Adresse email de l'utilisateur */
    private String email;

    /** Prénom de l'utilisateur */
    private String firstName;

    /** Nom de famille de l'utilisateur */
    private String lastName;

    /** Liste des rôles de l'utilisateur */
    private List<String> roles;

    /**
     * Constructeur complet
     *
     * @param token        Le token JWT
     * @param refreshToken Le token de rafraîchissement
     * @param expiresIn    Durée de validité du token
     * @param id           Identifiant de l'utilisateur
     * @param username     Nom d'utilisateur
     * @param email        Adresse email
     * @param firstName    Prénom
     * @param lastName     Nom de famille
     * @param roles        Rôles de l'utilisateur
     */
    public JwtResponse(String token, String refreshToken, Long expiresIn, Long id, String username, String email, String firstName, String lastName, List<String> roles) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }

    /**
     * Constructeur par défaut
     */
    public JwtResponse() {
    }

    // --- Getters et Setters ---

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
