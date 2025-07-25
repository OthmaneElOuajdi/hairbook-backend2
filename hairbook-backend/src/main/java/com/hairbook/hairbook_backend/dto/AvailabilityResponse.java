package com.hairbook.hairbook_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Réponse indiquant la disponibilité d'un créneau horaire ou d'une ressource")
public class AvailabilityResponse {

    @Schema(description = "Indique si la disponibilité est confirmée", example = "true")
    private boolean available;

    @Schema(description = "Message d'information ou d'erreur associé à la disponibilité", example = "Créneau disponible")
    private String message;

    public AvailabilityResponse() {
    }

    public AvailabilityResponse(boolean available, String message) {
        this.available = available;
        this.message = message;
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
}
