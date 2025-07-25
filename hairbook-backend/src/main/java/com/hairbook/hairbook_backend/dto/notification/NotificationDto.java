package com.hairbook.hairbook_backend.dto.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "DTO représentant une notification utilisateur")
public class NotificationDto {

    @Schema(description = "Identifiant unique de la notification", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identifiant de l'utilisateur auquel la notification est destinée", example = "1")
    private Long userId;

    @Schema(description = "Titre de la notification", example = "Rendez-vous confirmé")
    private String title;

    @Schema(description = "Contenu du message de la notification", example = "Votre rendez-vous du 15/01/2024 à 14h00 a été confirmé")
    private String message;

    @Schema(description = "Type de notification", example = "INFO")
    private String type;

    @Schema(description = "Indique si la notification a été lue", example = "false", defaultValue = "false")
    private boolean read;

    @Schema(description = "Date et heure de création de la notification", example = "2024-01-15T10:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "URL d'action associée à la notification", example = "/appointments/1")
    private String actionUrl;

    public NotificationDto() {}

    public NotificationDto(Long id, Long userId, String title, String message, String type,
                           boolean read, LocalDateTime createdAt, String actionUrl) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.read = read;
        this.createdAt = createdAt;
        this.actionUrl = actionUrl;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public boolean isRead() {
        return read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }
}
