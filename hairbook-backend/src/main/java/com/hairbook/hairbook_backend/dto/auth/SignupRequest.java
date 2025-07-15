package com.hairbook.hairbook_backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

/**
 * Représente une requête d'inscription contenant les informations nécessaires à la création d'un utilisateur.
 */
public class SignupRequest {

    /** Nom d'utilisateur choisi (entre 3 et 20 caractères, obligatoire) */
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 3, max = 20, message = "Le nom d'utilisateur doit contenir entre 3 et 20 caractères")
    private String username;

    /** Adresse email valide de l'utilisateur (obligatoire) */
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    /** Mot de passe sécurisé (entre 6 et 40 caractères, obligatoire) */
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, max = 40, message = "Le mot de passe doit contenir entre 6 et 40 caractères")
    private String password;

    /** Prénom de l'utilisateur (obligatoire, max 50 caractères) */
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 50, message = "Le prénom ne peut pas dépasser 50 caractères")
    private String firstName;

    /** Nom de famille de l'utilisateur (obligatoire, max 50 caractères) */
    @NotBlank(message = "Le nom de famille est obligatoire")
    @Size(max = 50, message = "Le nom de famille ne peut pas dépasser 50 caractères")
    private String lastName;

    /** Numéro de téléphone (obligatoire, 10 chiffres) */
    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Pattern(regexp = "^[0-9]{10}$", message = "Format de numéro de téléphone invalide (10 chiffres requis)")
    private String phoneNumber;

    /** Rôles attribués à l'utilisateur (ex. ROLE_USER, ROLE_ADMIN) */
    private Set<String> roles;

    /**
     * Constructeur par défaut
     */
    public SignupRequest() {
    }

    /**
     * Constructeur avec tous les champs
     *
     * @param username nom d'utilisateur
     * @param email adresse email
     * @param password mot de passe
     * @param firstName prénom
     * @param lastName nom de famille
     * @param phoneNumber numéro de téléphone
     * @param roles rôles d'accès
     */
    public SignupRequest(String username, String email, String password, String firstName,
                         String lastName, String phoneNumber, Set<String> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }

    // Getters et Setters

    /** @return le nom d'utilisateur */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /** @return l'adresse email */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /** @return le mot de passe */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /** @return le prénom */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** @return le nom de famille */
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** @return le numéro de téléphone */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** @return les rôles attribués */
    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
