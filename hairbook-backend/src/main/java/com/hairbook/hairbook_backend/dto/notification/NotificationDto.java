package com.hairbook.hairbook_backend.dto.notification;

import java.time.LocalDateTime;

/**
 * DTO représentant une notification utilisateur.
 * Utilisé pour transférer les données de notification entre le backend et le frontend.
 */
public class NotificationDto {

    /** Identifiant unique de la notification */
    private Long id;

    /** Identifiant de l'utilisateur auquel la notification est destinée */
    private Long userId;

    /** Titre de la notification */
    private String title;

    /** Contenu du message de la notification */
    private String message;

    /** Type de notification (ex: INFO, ALERT, REMINDER) */
    private String type;

    /** Indique si la notification a été lue */
    private boolean read;

    /** Date et heure de création de la notification */
    private LocalDateTime createdAt;

    /** URL d'action associée à la notification (redirige l'utilisateur vers un contenu pertinent) */
    private String actionUrl;

    /**
     * Constructeur par défaut.
     */
    public NotificationDto() {}

    /**
     * Constructeur avec tous les paramètres.
     *
     * @param id identifiant de la notification
     * @param userId identifiant de l'utilisateur
     * @param title titre de la notification
     * @param message contenu de la notification
     * @param type type de notification
     * @param read statut de lecture
     * @param createdAt date de création
     * @param actionUrl lien d'action
     */
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

    // --- Getters ---

    /** @return l'identifiant de la notification */
    public Long getId() {
        return id;
    }

    /** @return l'identifiant de l'utilisateur concerné */
    public Long getUserId() {
        return userId;
    }

    /** @return le titre de la notification */
    public String getTitle() {
        return title;
    }

    /** @return le contenu du message */
    public String getMessage() {
        return message;
    }

    /** @return le type de la notification */
    public String getType() {
        return type;
    }

    /** @return true si la notification a été lue */
    public boolean isRead() {
        return read;
    }

    /** @return la date de création de la notification */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /** @return l'URL d'action liée à cette notification */
    public String getActionUrl() {
        return actionUrl;
    }

    // --- Setters ---

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
