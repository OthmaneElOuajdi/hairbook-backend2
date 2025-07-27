package com.hairbook.hairbook_backend.repository;

import com.hairbook.hairbook_backend.entity.Service;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Schema(description = "Repository pour l'entité Service - Fournit des méthodes de requêtes personnalisées pour gérer les services proposés")
@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    @Schema(description = "Recherche un service par son nom")
    Optional<Service> findByName(String name);

    @Schema(description = "Récupère tous les services actifs (disponibles)")
    List<Service> findByActiveTrue();

    @Schema(description = "Vérifie si un service existe avec le nom spécifié")
    Boolean existsByName(String name);
}
