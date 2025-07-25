package com.hairbook.hairbook_backend.dto.loyalty;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "DTO représentant un résumé global du programme de fidélité d'un utilisateur")
public class LoyaltySummaryDto {

    @Schema(description = "Identifiant de l'utilisateur", example = "1")
    private Long userId;

    @Schema(description = "Nom complet de l'utilisateur", example = "John Doe", accessMode = Schema.AccessMode.READ_ONLY)
    private String userName;

    @Schema(description = "Total cumulé de points de fidélité", example = "1250", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer totalPoints;

    @Schema(description = "Nombre de points gagnés", example = "1500", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer pointsEarned;

    @Schema(description = "Nombre de points utilisés", example = "200", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer pointsRedeemed;

    @Schema(description = "Nombre de points expirés", example = "50", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer pointsExpired;

    @Schema(description = "Points encore disponibles pour être utilisés", example = "1250", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer availablePoints;

    @Schema(description = "Niveau de fidélité de l'utilisateur", example = "2", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer tier;

    @Schema(description = "Nom du niveau de fidélité", example = "Or", accessMode = Schema.AccessMode.READ_ONLY)
    private String tierName;

    @Schema(description = "Nombre de points requis pour atteindre le niveau suivant", example = "250", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer pointsToNextTier;

    @Schema(description = "Date de la dernière activité liée à la fidélité", example = "2024-01-15T10:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime lastActivity;

    @Schema(description = "Date d'inscription au programme de fidélité", example = "2023-06-01T09:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime memberSince;

    public LoyaltySummaryDto() {
    }

    public LoyaltySummaryDto(Long userId, String userName, Integer totalPoints, Integer pointsEarned,
                             Integer pointsRedeemed, Integer pointsExpired, Integer availablePoints,
                             Integer tier, String tierName, Integer pointsToNextTier,
                             LocalDateTime lastActivity, LocalDateTime memberSince) {
        this.userId = userId;
        this.userName = userName;
        this.totalPoints = totalPoints;
        this.pointsEarned = pointsEarned;
        this.pointsRedeemed = pointsRedeemed;
        this.pointsExpired = pointsExpired;
        this.availablePoints = availablePoints;
        this.tier = tier;
        this.tierName = tierName;
        this.pointsToNextTier = pointsToNextTier;
        this.lastActivity = lastActivity;
        this.memberSince = memberSince;
    }

        // --- Getters ---

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public Integer getPointsEarned() {
        return pointsEarned;
    }

    public Integer getPointsRedeemed() {
        return pointsRedeemed;
    }

    public Integer getPointsExpired() {
        return pointsExpired;
    }

    public Integer getAvailablePoints() {
        return availablePoints;
    }

    public Integer getTier() {
        return tier;
    }

    public String getTierName() {
        return tierName;
    }

    public Integer getPointsToNextTier() {
        return pointsToNextTier;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public LocalDateTime getMemberSince() {
        return memberSince;
    }

    // --- Setters ---

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void setPointsEarned(Integer pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public void setPointsRedeemed(Integer pointsRedeemed) {
        this.pointsRedeemed = pointsRedeemed;
    }

    public void setPointsExpired(Integer pointsExpired) {
        this.pointsExpired = pointsExpired;
    }

    public void setAvailablePoints(Integer availablePoints) {
        this.availablePoints = availablePoints;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    public void setTierName(String tierName) {
        this.tierName = tierName;
    }

    public void setPointsToNextTier(Integer pointsToNextTier) {
        this.pointsToNextTier = pointsToNextTier;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }

    public void setMemberSince(LocalDateTime memberSince) {
        this.memberSince = memberSince;
    }
}
