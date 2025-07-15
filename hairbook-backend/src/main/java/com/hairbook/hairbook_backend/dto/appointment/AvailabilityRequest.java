package com.hairbook.hairbook_backend.dto.appointment;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Représente une requête de vérification de disponibilité pour un service à une date et heure données.
 */
public class AvailabilityRequest {

    /**
     * L'identifiant du service demandé.
     */
    @NotNull(message = "L'ID du service est obligatoire")
    private Long serviceId;

    /**
     * La date et l'heure de début souhaitées pour le rendez-vous.
     */
    @NotNull(message = "La date et l'heure de début sont obligatoires")
    private LocalDateTime startTime;

    /**
     * Constructeur par défaut requis pour la désérialisation JSON.
     */
    public AvailabilityRequest() {
        // Constructeur vide
    }

    /**
     * Constructeur avec tous les champs.
     *
     * @param serviceId l'identifiant du service
     * @param startTime la date et l'heure souhaitées
     */
    public AvailabilityRequest(Long serviceId, LocalDateTime startTime) {
        this.serviceId = serviceId;
        this.startTime = startTime;
    }

    /**
     * Obtient l'identifiant du service.
     *
     * @return l'identifiant du service
     */
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * Définit l'identifiant du service.
     *
     * @param serviceId l'identifiant à définir
     */
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * Obtient la date et l'heure de début.
     *
     * @return la date et l'heure de début
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Définit la date et l'heure de début.
     *
     * @param startTime la date et l'heure à définir
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
