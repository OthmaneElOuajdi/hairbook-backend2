package com.hairbook.hairbook_backend.dto.loyalty;

import com.hairbook.hairbook_backend.entity.LoyaltyPoints.PointsType;

import java.time.LocalDateTime;

/**
 * DTO représentant une transaction de points de fidélité pour un utilisateur.
 * Contient les informations nécessaires à l'affichage et à la gestion des points côté frontend.
 */
public class LoyaltyPointsDto {

    /** Identifiant unique de la transaction de points */
    private Long id;

    /** Identifiant de l'utilisateur concerné */
    private Long userId;

    /** Nom complet de l'utilisateur */
    private String userName;

    /** Nombre de points attribués ou retirés */
    private Integer points;

    /** Type de transaction (GAIN ou UTILISATION) */
    private PointsType type;

    /** Description de la transaction */
    private String description;

    /** Identifiant du rendez-vous lié à cette transaction (facultatif) */
    private Long appointmentId;

    /** Date et heure de création de la transaction */
    private LocalDateTime createdAt;

    /**
     * Constructeur par défaut.
     */
    public LoyaltyPointsDto() {}

    /**
     * Constructeur avec tous les champs.
     *
     * @param id identifiant de la transaction
     * @param userId identifiant de l'utilisateur
     * @param userName nom complet de l'utilisateur
     * @param points nombre de points
     * @param type type de transaction (GAIN ou UTILISATION)
     * @param description description de l'opération
     * @param appointmentId identifiant du rendez-vous lié (si applicable)
     * @param createdAt date de création
     */
    public LoyaltyPointsDto(Long id, Long userId, String userName, Integer points, PointsType type,
                            String description, Long appointmentId, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.points = points;
        this.type = type;
        this.description = description;
        this.appointmentId = appointmentId;
        this.createdAt = createdAt;
    }

    // --- Getters ---

    /** @return identifiant de la transaction */
    public Long getId() {
        return id;
    }

    /** @return identifiant de l'utilisateur */
    public Long getUserId() {
        return userId;
    }

    /** @return nom complet de l'utilisateur */
    public String getUserName() {
        return userName;
    }

    /** @return nombre de points (positif ou négatif selon le type) */
    public Integer getPoints() {
        return points;
    }

    /** @return type de transaction (GAIN ou UTILISATION) */
    public PointsType getType() {
        return type;
    }

    /** @return description de la transaction */
    public String getDescription() {
        return description;
    }

    /** @return identifiant du rendez-vous lié (ou null) */
    public Long getAppointmentId() {
        return appointmentId;
    }

    /** @return date de création de la transaction */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // --- Setters ---

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void setType(PointsType type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
