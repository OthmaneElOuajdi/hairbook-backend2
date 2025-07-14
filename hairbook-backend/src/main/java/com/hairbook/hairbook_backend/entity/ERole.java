package com.hairbook.hairbook_backend.entity;

/**
 * Représente les différents rôles qu'un utilisateur peut avoir dans l'application Hairbook.
 * <p>
 * Chaque rôle détermine les permissions d'accès aux fonctionnalités de l'application :
 * </p>
 * <ul>
 *   <li>{@code ROLE_VISITOR} : peut consulter les informations publiques et voir les créneaux disponibles.</li>
 *   <li>{@code ROLE_MEMBER} : peut réserver, modifier ou annuler un rendez-vous.</li>
 *   <li>{@code ROLE_ADMIN} : dispose de tous les droits, y compris l'administration du salon.</li>
 * </ul>
 *
 * @author —
 */
public enum ERole {

    /**
     * Rôle pour un visiteur non authentifié.
     * Accès limité à la consultation des informations publiques et des créneaux disponibles.
     */
    ROLE_VISITOR,

    /**
     * Rôle pour un utilisateur inscrit (membre).
     * Peut effectuer des actions comme réserver, modifier ou annuler un rendez-vous.
     */
    ROLE_MEMBER,

    /**
     * Rôle pour un administrateur.
     * Accès complet à la gestion du salon, des utilisateurs et des rendez-vous.
     */
    ROLE_ADMIN
}
