package com.hairbook.hairbook_backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Schema(description = "Représente une notification destinée à un utilisateur dans l'application Hairbook")
@Entity
@Table(name = "notifications")
public class Notification {

    @Schema(description = "Identifiant unique de la notification", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Utilisateur destinataire de la notification")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Schema(description = "Titre de la notification", example = "Rendez-vous confirmé", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(nullable = false)
    private String title;

    @Schema(description = "Corps du message de la notification", example = "Votre rendez-vous du 15 janvier à 10h00 a été confirmé", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Schema(description = "Type de notification", example = "EMAIL", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(nullable = false)
    private String type;

    @Schema(description = "Indique si la notification a été lue par l'utilisateur", example = "false")
    @Column(nullable = false)
    private boolean read = false;

    @Schema(description = "Date et heure de création de la notification", example = "2024-01-15T10:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Schema(description = "URL optionnelle liée à une action à effectuer par l'utilisateur", example = "/appointments/123", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String actionUrl;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ----- Getters & Setters -----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    // ----- Constructeurs -----

    public Notification() {}

    public Notification(Long id, User user, String title, String message, String type,
                        boolean read, LocalDateTime createdAt, String actionUrl) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.message = message;
        this.type = type;
        this.read = read;
        this.createdAt = createdAt;
        this.actionUrl = actionUrl;
    }
}
