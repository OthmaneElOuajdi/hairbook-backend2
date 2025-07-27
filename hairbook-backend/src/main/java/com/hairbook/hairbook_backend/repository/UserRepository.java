package com.hairbook.hairbook_backend.repository;

import com.hairbook.hairbook_backend.entity.Role;
import com.hairbook.hairbook_backend.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Schema(description = "Repository pour l'entité User - Fournit des méthodes de requêtes personnalisées en plus des opérations CRUD de base")
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Schema(description = "Recherche un utilisateur par son nom d'utilisateur")
    Optional<User> findByUsername(String username);

    @Schema(description = "Recherche un utilisateur par son adresse e-mail")
    Optional<User> findByEmail(String email);

    @Schema(description = "Vérifie si un utilisateur existe avec le nom d'utilisateur donné")
    Boolean existsByUsername(String username);

    @Schema(description = "Vérifie si un utilisateur existe avec l'adresse e-mail donnée")
    Boolean existsByEmail(String email);

    @Schema(description = "Compte le nombre d'utilisateurs possédant un rôle spécifique")
    Long countByRolesContaining(Role role);

    @Schema(description = "Récupère tous les utilisateurs possédant un rôle spécifique")
    List<User> findByRolesContaining(Role role);
}
