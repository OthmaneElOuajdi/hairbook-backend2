package com.hairbook.hairbook_backend.dto.loyalty;

import com.hairbook.hairbook_backend.entity.LoyaltyPoints.PointsType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "DTO représentant une transaction de points de fidélité pour un utilisateur")
public class LoyaltyPointsDto {

    @Schema(description = "Identifiant unique de la transaction de points", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identifiant de l'utilisateur concerné", example = "1")
    private Long userId;

    @Schema(description = "Nom complet de l'utilisateur", example = "John Doe", accessMode = Schema.AccessMode.READ_ONLY)
    private String userName;

    @Schema(description = "Nombre de points attribués ou retirés", example = "50")
    private Integer points;

    @Schema(description = "Type de transaction", example = "GAIN")
    private PointsType type;

    @Schema(description = "Description de la transaction", example = "Points gagnés pour rendez-vous complété")
    private String description;

    @Schema(description = "Identifiant du rendez-vous lié à cette transaction", example = "1")
    private Long appointmentId;

    @Schema(description = "Date et heure de création de la transaction", example = "2024-01-15T10:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    public LoyaltyPointsDto() {}

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

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getPoints() {
        return points;
    }

    public PointsType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

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
