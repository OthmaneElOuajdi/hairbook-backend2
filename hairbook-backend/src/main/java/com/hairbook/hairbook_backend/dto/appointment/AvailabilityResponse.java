package com.hairbook.hairbook_backend.dto.appointment;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Réponse à une requête de disponibilité pour un rendez-vous")
public class AvailabilityResponse {

    @Schema(description = "Indique si le créneau demandé est disponible", example = "true", accessMode = Schema.AccessMode.READ_ONLY)
    private boolean available;

    @Schema(description = "Message explicatif ou informatif à afficher à l'utilisateur", example = "Créneau disponible", accessMode = Schema.AccessMode.READ_ONLY)
    private String message;

    @Schema(description = "Liste de créneaux alternatifs proposés si le créneau demandé est indisponible", accessMode = Schema.AccessMode.READ_ONLY)
    private List<LocalDateTime> alternativeSlots;

    public AvailabilityResponse() {
    }

    public AvailabilityResponse(boolean available, String message) {
        this.available = available;
        this.message = message;
        this.alternativeSlots = null;
    }

    public AvailabilityResponse(boolean available, String message, List<LocalDateTime> alternativeSlots) {
        this.available = available;
        this.message = message;
        this.alternativeSlots = alternativeSlots;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LocalDateTime> getAlternativeSlots() {
        return alternativeSlots;
    }

    public void setAlternativeSlots(List<LocalDateTime> alternativeSlots) {
        this.alternativeSlots = alternativeSlots;
    }
}
