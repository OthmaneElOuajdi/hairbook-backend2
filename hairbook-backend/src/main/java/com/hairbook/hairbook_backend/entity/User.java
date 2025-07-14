package com.hairbook.hairbook_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Représente un utilisateur dans le système Hairbook.
 *
 * <p>La classe {@code User} est une entité persistée dans la base de données,
 * avec des contraintes de validation pour les champs principaux.</p>
 *
 * <p>Chaque utilisateur possède un ou plusieurs rôles {@link Role} qui déterminent ses autorisations.</p>
 *
 * @see Role
 */
@Entity
@Table(name = "users",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       })
public class User {

    /**
     * Identifiant unique de l'utilisateur (généré automatiquement).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom d'utilisateur (obligatoire, max 20 caractères, unique).
     */
    @NotBlank
    @Size(max = 20)
    private String username;

    /**
     * Adresse e-mail de l'utilisateur (obligatoire, max 50 caractères, unique, format valide).
     */
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    /**
     * Mot de passe chiffré de l'utilisateur (obligatoire, max 120 caractères).
     */
    @NotBlank
    @Size(max = 120)
    private String password;

    /**
     * Prénom de l'utilisateur (obligatoire, max 50 caractères).
     */
    @NotBlank
    @Size(max = 50)
    private String firstName;

    /**
     * Nom de famille de l'utilisateur (obligatoire, max 50 caractères).
     */
    @NotBlank
    @Size(max = 50)
    private String lastName;

    /**
     * Numéro de téléphone de l'utilisateur (optionnel, max 15 caractères).
     */
    @Size(max = 15)
    private String phoneNumber;

    /**
     * Rôles associés à l'utilisateur.
     * <p>Un utilisateur peut avoir plusieurs rôles (ManyToMany).</p>
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    /**
     * Constructeur sans argument requis par JPA.
     */
    public User() {}

    /**
     * Constructeur personnalisé pour créer un utilisateur avec les champs essentiels.
     *
     * @param username le nom d'utilisateur
     * @param email l'adresse e-mail
     * @param password le mot de passe
     * @param firstName le prénom
     * @param lastName le nom de famille
     * @param phoneNumber le numéro de téléphone
     */
    public User(String username, String email, String password, String firstName, String lastName, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    // ----- Getters & Setters -----

    /** @return l'identifiant de l'utilisateur */
    public Long getId() { return id; }

    /** @param id l'identifiant à définir */
    public void setId(Long id) { this.id = id; }

    /** @return le nom d'utilisateur */
    public String getUsername() { return username; }

    /** @param username le nom d'utilisateur à définir */
    public void setUsername(String username) { this.username = username; }

    /** @return l'e-mail de l'utilisateur */
    public String getEmail() { return email; }

    /** @param email l'adresse e-mail à définir */
    public void setEmail(String email) { this.email = email; }

    /** @return le mot de passe */
    public String getPassword() { return password; }

    /** @param password le mot de passe à définir */
    public void setPassword(String password) { this.password = password; }

    /** @return le prénom */
    public String getFirstName() { return firstName; }

    /** @param firstName le prénom à définir */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /** @return le nom de famille */
    public String getLastName() { return lastName; }

    /** @param lastName le nom de famille à définir */
    public void setLastName(String lastName) { this.lastName = lastName; }

    /** @return le numéro de téléphone */
    public String getPhoneNumber() { return phoneNumber; }

    /** @param phoneNumber le numéro de téléphone à définir */
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    /** @return la liste des rôles de l'utilisateur */
    public Set<Role> getRoles() { return roles; }

    /** @param roles les rôles à associer à l'utilisateur */
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}
