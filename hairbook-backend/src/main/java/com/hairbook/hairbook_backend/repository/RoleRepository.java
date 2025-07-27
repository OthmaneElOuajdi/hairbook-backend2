package com.hairbook.hairbook_backend.repository;

import com.hairbook.hairbook_backend.entity.ERole;
import com.hairbook.hairbook_backend.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Schema(description = "Repository pour l'entité Role - Permet l'accès aux rôles définis dans le système")
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Schema(description = "Recherche un rôle par son nom (valeur de l'énumération ERole)")
    Optional<Role> findByName(ERole name);
}
