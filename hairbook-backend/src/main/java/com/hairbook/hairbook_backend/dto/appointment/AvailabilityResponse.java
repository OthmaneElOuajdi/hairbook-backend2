package com.hairbook.hairbook_backend.dto.appointment;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Réponse à une requête de disponibilité pour un rendez-vous.
 * Fournit l'information sur la disponibilité, un message explicatif,
 * ainsi que des créneaux alternatifs le cas échéant.
 */
public class AvailabilityResponse {

    /**
     * Indique si le créneau demandé est disponible.
     */
    private boolean available;

    /**
     * Message explicatif ou informatif à afficher à l'utilisateur.
     */
    private String message;

    /**
     * Liste de créneaux alternatifs proposés si le créneau demandé est indisponible.
     */
    private List<LocalDateTime> alternativeSlots;

    /**
     * Constructeur par défaut.
     */
    public AvailabilityResponse() {
    }

    /**
     * Constructeur avec disponibilité et message.
     * Utilisé lorsqu'aucun créneau alternatif n'est proposé.
     *
     * @param available disponibilité du créneau demandé
     * @param message   message explicatif
     */
    public AvailabilityResponse(boolean available, String message) {
        this.available = available;
        this.message = message;
        this.alternativeSlots = null;
    }

    /**
     * Constructeur complet avec tous les champs.
     *
     * @param available         disponibilité du créneau demandé
     * @param message           message explicatif
     * @param alternativeSlots  liste des créneaux alternatifs proposés
     */
    public AvailabilityResponse(boolean available, String message, List<LocalDateTime> alternativeSlots) {
        this.available = available;
        this.message = message;
        this.alternativeSlots = alternativeSlots;
    }

    /**
     * Indique si le créneau est disponible.
     *
     * @return true si disponible, sinon false
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Définit la disponibilité du créneau.
     *
     * @param available true si disponible, sinon false
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Obtient le message informatif.
     *
     * @return message à afficher
     */
    public String getMessage() {
        return message;
    }

    /**
     * Définit le message à afficher.
     *
     * @param message le message à définir
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Obtient la liste des créneaux alternatifs.
     *
     * @return liste de créneaux disponibles en alternative
     */
    public List<LocalDateTime> getAlternativeSlots() {
        return alternativeSlots;
    }

    /**
     * Définit les créneaux alternatifs.
     *
     * @param alternativeSlots liste des alternatives à définir
     */
    public void setAlternativeSlots(List<LocalDateTime> alternativeSlots) {
        this.alternativeSlots = alternativeSlots;
    }
}
