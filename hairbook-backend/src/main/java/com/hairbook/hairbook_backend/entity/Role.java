package com.hairbook.hairbook_backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Schema(description = "Représente un rôle attribué à un utilisateur dans l'application Hairbook")
@Entity
@Table(name = "roles")
public class Role {

    @Schema(description = "Identifiant unique du rôle", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Nom du rôle", example = "ROLE_USER", requiredMode = Schema.RequiredMode.REQUIRED)
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    // ----- Constructeurs -----

    public Role() {}

    public Role(ERole name) {
        this.name = name;
    }

    public Role(Integer id, ERole name) {
        this.id = id;
        this.name = name;
    }

    // ----- Getters et Setters -----

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

}
