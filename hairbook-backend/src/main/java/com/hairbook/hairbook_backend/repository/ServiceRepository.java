package com.hairbook.hairbook_backend.repository;

import com.hairbook.hairbook_backend.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité {@link Service}.
 * Fournit des méthodes de requêtes personnalisées pour gérer les services proposés.
 */
@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    /**
     * Recherche un service par son nom.
     *
     * @param name le nom du service
     * @return un {@link Optional} contenant le service s'il est trouvé
     */
    Optional<Service> findByName(String name);

    /**
     * Récupère tous les services actifs (disponibles).
     *
     * @return une liste de services dont le champ {@code active} est à {@code true}
     */
    List<Service> findByActiveTrue();

    /**
     * Vérifie si un service existe avec le nom spécifié.
     *
     * @param name le nom du service
     * @return {@code true} si un service avec ce nom existe, sinon {@code false}
     */
    Boolean existsByName(String name);
}
