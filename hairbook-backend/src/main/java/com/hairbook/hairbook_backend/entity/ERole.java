package com.hairbook.hairbook_backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Représente les différents rôles qu'un utilisateur peut avoir dans l'application Hairbook")
public enum ERole {

    @Schema(description = "Rôle pour un visiteur non authentifié avec accès limité")
    ROLE_VISITOR,

    @Schema(description = "Rôle pour un utilisateur inscrit pouvant réserver des rendez-vous")
    ROLE_MEMBER,

    @Schema(description = "Rôle administrateur avec accès complet à la gestion")
    ROLE_ADMIN
}
