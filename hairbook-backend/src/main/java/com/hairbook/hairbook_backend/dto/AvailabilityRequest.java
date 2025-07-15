package com.hairbook.hairbook_backend.dto;

import java.time.LocalDateTime;

/**
 * Requête de disponibilité pour un service à une date et heure donnée.
 */
public class AvailabilityRequest {

    /**
     * ID du service concerné.
     */
    private Long serviceId;

    /**
     * Date et heure de début souhaitée pour la réservation.
     */
    private LocalDateTime startTime;

    /**
     * Constructeur par défaut.
     */
    public AvailabilityRequest() {
    }

    /**
     * Constructeur avec paramètres.
     *
     * @param serviceId identifiant du service
     * @param startTime date et heure de début souhaitée
     */
    public AvailabilityRequest(Long serviceId, LocalDateTime startTime) {
        this.serviceId = serviceId;
        this.startTime = startTime;
    }

    /**
     * Retourne l'ID du service.
     *
     * @return identifiant du service
     */
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * Définit l'ID du service.
     *
     * @param serviceId identifiant du service
     */
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * Retourne la date et l'heure de début souhaitée.
     *
     * @return date et heure de début
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Définit la date et l'heure de début souhaitée.
     *
     * @param startTime date et heure de début
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
