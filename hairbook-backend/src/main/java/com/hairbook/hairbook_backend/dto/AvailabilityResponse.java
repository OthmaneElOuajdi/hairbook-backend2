package com.hairbook.hairbook_backend.dto;

/**
 * Réponse indiquant la disponibilité d'un créneau horaire ou d'une ressource.
 */
public class AvailabilityResponse {

    /**
     * Indique si la disponibilité est confirmée.
     */
    private boolean available;

    /**
     * Message d'information ou d'erreur associé à la disponibilité.
     */
    private String message;

    // --- Constructeurs ---

    /**
     * Constructeur par défaut.
     */
    public AvailabilityResponse() {
    }

    /**
     * Constructeur avec tous les champs.
     *
     * @param available disponibilité (true ou false)
     * @param message   message associé
     */
    public AvailabilityResponse(boolean available, String message) {
        this.available = available;
        this.message = message;
    }

    // --- Getters et Setters ---

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
