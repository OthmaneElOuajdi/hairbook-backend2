package com.hairbook.hairbook_backend.entity;

/**
 * Enumération représentant les différents statuts possibles d’un rendez-vous dans l’application Hairbook.
 *
 * <p>Ces statuts permettent de suivre l’état d’un rendez-vous tout au long de son cycle de vie.</p>
 */
public enum AppointmentStatus {

    /**
     * Rendez-vous planifié, mais pas encore confirmé.
     */
    SCHEDULED,

    /**
     * Rendez-vous confirmé par le salon ou le client.
     */
    CONFIRMED,

    /**
     * Rendez-vous effectué avec succès.
     */
    COMPLETED,

    /**
     * Rendez-vous annulé par le client.
     */
    CANCELLED,

    /**
     * Le client ne s’est pas présenté au rendez-vous sans annulation.
     */
    NO_SHOW
}
