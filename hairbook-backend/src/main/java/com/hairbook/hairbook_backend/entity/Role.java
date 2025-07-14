package com.hairbook.hairbook_backend.entity;

import jakarta.persistence.*;

/**
 * Représente un rôle attribué à un utilisateur dans l'application Hairbook.
 *
 * <p>Chaque rôle est associé à un nom défini dans l'énumération {@link ERole},
 * et permet de contrôler les autorisations d'accès d'un utilisateur.</p>
 *
 * <p>Cette entité est mappée à la table <strong>roles</strong> en base de données.</p>
 *
 * @see ERole
 * @see User
 */
@Entity
@Table(name = "roles")
public class Role {

    /**
     * Identifiant unique du rôle (clé primaire auto-générée).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nom du rôle, représenté par une valeur de l'énumération {@link ERole}.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    // ----- Getters et Setters -----

    /**
     * Retourne l'identifiant du rôle.
     * @return l'identifiant du rôle
     */
    public Integer getId() {
        return id;
    }

    /**
     * Définit l'identifiant du rôle.
     * @param id l'identifiant à définir
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retourne le nom du rôle (ex : ROLE_ADMIN, ROLE_MEMBER).
     * @return le nom du rôle
     */
    public ERole getName() {
        return name;
    }

    /**
     * Définit le nom du rôle.
     * @param name nom du rôle à définir
     */
    public void setName(ERole name) {
        this.name = name;
    }

    // ----- Constructeurs -----

    /**
     * Constructeur sans argument requis par JPA.
     */
    public Role() {}

    /**
     * Constructeur avec nom de rôle.
     * @param name nom du rôle à créer
     */
    public Role(ERole name) {
        this.name = name;
    }

    /**
     * Constructeur complet avec identifiant et nom.
     * @param id identifiant du rôle
     * @param name nom du rôle
     */
    public Role(Integer id, ERole name) {
        this.id = id;
        this.name = name;
    }
}
