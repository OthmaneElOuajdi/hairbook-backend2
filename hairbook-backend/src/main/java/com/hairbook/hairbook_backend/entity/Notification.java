package com.hairbook.hairbook_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Représente une notification destinée à un utilisateur dans l'application Hairbook.
 *
 * <p>Les notifications peuvent être de différents types (email, push, SMS, etc.) et 
 * contenir un message, un titre, ainsi qu'une URL d'action associée.</p>
 *
 * <p>Cette entité est liée à un utilisateur et mappée à la table <strong>notifications</strong>.</p>
 *
 * @see User
 */
@Entity
@Table(name = "notifications")
public class Notification {

    /**
     * Identifiant unique de la notification (clé primaire auto-générée).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Utilisateur destinataire de la notification.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Titre de la notification.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Corps du message de la notification.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    /**
     * Type de notification (ex. : EMAIL, SMS, PUSH, SYSTEM).
     */
    @Column(nullable = false)
    private String type;

    /**
     * Indique si la notification a été lue par l'utilisateur.
     */
    @Column(nullable = false)
    private boolean read = false;

    /**
     * Date et heure de création de la notification.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * URL optionnelle liée à une action à effectuer par l'utilisateur.
     */
    private String actionUrl;

    /**
     * Initialise la date de création à la création de l'entité.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ----- Getters & Setters -----

    /** @return l'identifiant de la notification */
    public Long getId() {
        return id;
    }

    /** @param id identifiant à définir */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return l'utilisateur destinataire */
    public User getUser() {
        return user;
    }

    /** @param user utilisateur destinataire à définir */
    public void setUser(User user) {
        this.user = user;
    }

    /** @return le titre de la notification */
    public String getTitle() {
        return title;
    }

    /** @param title titre à définir */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return le message de la notification */
    public String getMessage() {
        return message;
    }

    /** @param message contenu à définir */
    public void setMessage(String message) {
        this.message = message;
    }

    /** @return le type de notification (EMAIL, PUSH, etc.) */
    public String getType() {
        return type;
    }

    /** @param type type de notification à définir */
    public void setType(String type) {
        this.type = type;
    }

    /** @return true si la notification a été lue */
    public boolean isRead() {
        return read;
    }

    /** @param read indique si la notification est lue */
    public void setRead(boolean read) {
        this.read = read;
    }

    /** @return date de création de la notification */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /** @param createdAt date à définir (utilisé uniquement si nécessaire) */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /** @return l'URL d'action associée à la notification */
    public String getActionUrl() {
        return actionUrl;
    }

    /** @param actionUrl URL à définir */
    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    // ----- Constructeurs -----

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Notification() {}

    /**
     * Constructeur complet.
     *
     * @param id identifiant
     * @param user utilisateur concerné
     * @param title titre de la notification
     * @param message contenu du message
     * @param type type de notification
     * @param read statut de lecture
     * @param createdAt date de création
     * @param actionUrl lien associé
     */
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
