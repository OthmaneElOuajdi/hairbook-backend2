package com.hairbook.hairbook_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Requête de disponibilité pour un service à une date et heure donnée")
public class AvailabilityRequest {

    @Schema(description = "ID du service concerné", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long serviceId;

    @Schema(description = "Date et heure de début souhaitée pour la réservation", example = "2024-01-15T10:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime startTime;

    public AvailabilityRequest() {
    }

    public AvailabilityRequest(Long serviceId, LocalDateTime startTime) {
        this.serviceId = serviceId;
        this.startTime = startTime;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
