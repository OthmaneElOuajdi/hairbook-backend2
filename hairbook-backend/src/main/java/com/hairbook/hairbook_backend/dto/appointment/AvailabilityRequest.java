package com.hairbook.hairbook_backend.dto.appointment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "Requête de vérification de disponibilité pour un service à une date et heure données")
public class AvailabilityRequest {

    @Schema(description = "Identifiant du service demandé", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "L'ID du service est obligatoire")
    private Long serviceId;

    @Schema(description = "Date et heure de début souhaitées pour le rendez-vous", example = "2024-01-15T10:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La date et l'heure de début sont obligatoires")
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
