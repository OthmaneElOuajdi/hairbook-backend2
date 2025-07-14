package com.hairbook.hairbook_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Représente une opération liée aux points de fidélité d’un utilisateur dans l’application Hairbook.
 *
 * <p>Chaque enregistrement indique un nombre de points, leur type (gagnés, utilisés, expirés, ajustés),
 * une description optionnelle, et peut être lié à un rendez-vous précis.</p>
 *
 * <p>Cette entité est mappée à la table <strong>loyalty_points</strong>.</p>
 *
 * @see User
 * @see Appointment
 */
@Entity
@Table(name = "loyalty_points")
public class LoyaltyPoints {

    /**
     * Identifiant unique de l'enregistrement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Utilisateur auquel sont liés les points.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Nombre de points impliqués dans l’opération.
     */
    @Column(nullable = false)
    private Integer points;

    /**
     * Type d’opération de fidélité.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PointsType type;

    /**
     * Description facultative de l’opération (ex : "bonus d'anniversaire").
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Rendez-vous associé à l'opération (facultatif).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    /**
     * Date et heure de création de l'enregistrement.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Initialise le champ {@code createdAt} lors de la création de l’entité.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Enumération des types d’opérations de points de fidélité.
     */
    public enum PointsType {
        /** Points gagnés par l'utilisateur. */
        EARNED,

        /** Points utilisés pour un avantage. */
        REDEEMED,

        /** Points expirés automatiquement. */
        EXPIRED,

        /** Ajustement manuel (positif ou négatif). */
        ADJUSTED
    }

    // ----- Getters & Setters -----

    /** @return l'identifiant de l'opération */
    public Long getId() { return id; }

    /** @param id identifiant à définir */
    public void setId(Long id) { this.id = id; }

    /** @return utilisateur concerné */
    public User getUser() { return user; }

    /** @param user utilisateur à associer */
    public void setUser(User user) { this.user = user; }

    /** @return nombre de points */
    public Integer getPoints() { return points; }

    /** @param points nombre de points à définir */
    public void setPoints(Integer points) { this.points = points; }

    /** @return type d'opération */
    public PointsType getType() { return type; }

    /** @param type type d'opération à définir */
    public void setType(PointsType type) { this.type = type; }

    /** @return description optionnelle */
    public String getDescription() { return description; }

    /** @param description texte à définir */
    public void setDescription(String description) { this.description = description; }

    /** @return rendez-vous lié à l'opération */
    public Appointment getAppointment() { return appointment; }

    /** @param appointment rendez-vous à associer */
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }

    /** @return date de création de l’enregistrement */
    public LocalDateTime getCreatedAt() { return createdAt; }

    /** @param createdAt date à définir manuellement si nécessaire */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // ----- Constructeurs -----

    /**
     * Constructeur sans argument requis par JPA.
     */
    public LoyaltyPoints() {}

    /**
     * Constructeur complet.
     *
     * @param id identifiant de l’opération
     * @param user utilisateur concerné
     * @param points nombre de points
     * @param type type d’opération
     * @param description texte explicatif
     * @param appointment rendez-vous lié
     * @param createdAt date de création
     */
    public LoyaltyPoints(Long id, User user, Integer points, PointsType type, String description,
                         Appointment appointment, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.points = points;
        this.type = type;
        this.description = description;
        this.appointment = appointment;
        this.createdAt = createdAt;
    }
}
