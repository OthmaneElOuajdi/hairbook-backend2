package com.hairbook.hairbook_backend.repository;

import com.hairbook.hairbook_backend.entity.Role;
import com.hairbook.hairbook_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité {@link User}.
 * Fournit des méthodes de requêtes personnalisées en plus des opérations CRUD de base.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Recherche un utilisateur par son nom d'utilisateur.
     *
     * @param username le nom d'utilisateur
     * @return un {@link Optional} contenant l'utilisateur s'il est trouvé
     */
    Optional<User> findByUsername(String username);

    /**
     * Recherche un utilisateur par son adresse e-mail.
     *
     * @param email l'adresse e-mail
     * @return un {@link Optional} contenant l'utilisateur s'il est trouvé
     */
    Optional<User> findByEmail(String email);

    /**
     * Vérifie si un utilisateur existe avec le nom d'utilisateur donné.
     *
     * @param username le nom d'utilisateur
     * @return true si l'utilisateur existe, false sinon
     */
    Boolean existsByUsername(String username);

    /**
     * Vérifie si un utilisateur existe avec l'adresse e-mail donnée.
     *
     * @param email l'adresse e-mail
     * @return true si l'utilisateur existe, false sinon
     */
    Boolean existsByEmail(String email);

    /**
     * Compte le nombre d'utilisateurs possédant un rôle spécifique.
     *
     * @param role le rôle à rechercher
     * @return le nombre d'utilisateurs avec ce rôle
     */
    Long countByRolesContaining(Role role);

    /**
     * Récupère tous les utilisateurs possédant un rôle spécifique.
     *
     * @param role le rôle à rechercher
     * @return une liste d'utilisateurs ayant ce rôle
     */
    List<User> findByRolesContaining(Role role);
}
