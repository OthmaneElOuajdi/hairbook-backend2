package com.hairbook.hairbook_backend.repository;

import com.hairbook.hairbook_backend.entity.ERole;
import com.hairbook.hairbook_backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository pour l'entité {@link Role}.
 * Permet l'accès aux rôles définis dans le système.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Recherche un rôle par son nom (valeur de l'énumération {@link ERole}).
     *
     * @param name le nom du rôle à rechercher
     * @return un {@link Optional} contenant le rôle s'il est trouvé
     */
    Optional<Role> findByName(ERole name);
}
