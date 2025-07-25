package com.hairbook.hairbook_backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Énumération représentant les différents statuts possibles d'un rendez-vous dans l'application Hairbook")
public enum AppointmentStatus {

    @Schema(description = "Rendez-vous planifié, mais pas encore confirmé")
    SCHEDULED,

    @Schema(description = "Rendez-vous confirmé par le salon ou le client")
    CONFIRMED,

    @Schema(description = "Rendez-vous effectué avec succès")
    COMPLETED,

    @Schema(description = "Rendez-vous annulé par le client")
    CANCELLED,

    @Schema(description = "Le client ne s'est pas présenté au rendez-vous sans annulation")
    NO_SHOW
}
