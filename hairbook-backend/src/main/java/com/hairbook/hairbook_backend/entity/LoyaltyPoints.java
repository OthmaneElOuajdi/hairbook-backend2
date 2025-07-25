package com.hairbook.hairbook_backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Schema(description = "Représente une opération liée aux points de fidélité d'un utilisateur dans l'application Hairbook")
@Entity
@Table(name = "loyalty_points")
public class LoyaltyPoints {

    @Schema(description = "Identifiant unique de l'enregistrement", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Utilisateur auquel sont liés les points")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Schema(description = "Nombre de points impliqués dans l'opération", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(nullable = false)
    private Integer points;

    @Schema(description = "Type d'opération de fidélité", example = "EARNED", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PointsType type;

    @Schema(description = "Description facultative de l'opération", example = "Points gagnés pour rendez-vous", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Column(columnDefinition = "TEXT")
    private String description;

    @Schema(description = "Rendez-vous associé à l'opération", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @Schema(description = "Date et heure de création de l'enregistrement", example = "2024-01-15T10:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Schema(description = "Énumération des types d'opérations de points de fidélité")
    public enum PointsType {
        EARNED,
        REDEEMED,
        EXPIRED,
        ADJUSTED
    }

    // ----- Getters & Setters -----

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Integer getPoints() { return points; }

    public void setPoints(Integer points) { this.points = points; }

    public PointsType getType() { return type; }

    public void setType(PointsType type) { this.type = type; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Appointment getAppointment() { return appointment; }

    public void setAppointment(Appointment appointment) { this.appointment = appointment; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // ----- Constructeurs -----

    public LoyaltyPoints() {}

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
