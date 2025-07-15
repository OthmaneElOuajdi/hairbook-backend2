package com.hairbook.hairbook_backend.dto.loyalty;

import java.time.LocalDateTime;

/**
 * DTO représentant un résumé global du programme de fidélité d'un utilisateur.
 * Il contient des informations telles que le total de points, le niveau de fidélité,
 * les points disponibles, l'historique d'activité, etc.
 */
public class LoyaltySummaryDto {

    /** Identifiant de l'utilisateur */
    private Long userId;

    /** Nom complet de l'utilisateur */
    private String userName;

    /** Total cumulé de points de fidélité (tous types confondus) */
    private Integer totalPoints;

    /** Nombre de points gagnés */
    private Integer pointsEarned;

    /** Nombre de points utilisés */
    private Integer pointsRedeemed;

    /** Nombre de points expirés */
    private Integer pointsExpired;

    /** Points encore disponibles pour être utilisés */
    private Integer availablePoints;

    /** Niveau de fidélité de l'utilisateur (ex: 1, 2, 3...) */
    private Integer tier;

    /** Nom du niveau de fidélité (ex: Argent, Or, Platine) */
    private String tierName;

    /** Nombre de points requis pour atteindre le niveau suivant */
    private Integer pointsToNextTier;

    /** Date de la dernière activité liée à la fidélité (gain ou utilisation) */
    private LocalDateTime lastActivity;

    /** Date d'inscription au programme de fidélité */
    private LocalDateTime memberSince;

    /**
     * Constructeur par défaut
     */
    public LoyaltySummaryDto() {
    }

    /**
     * Constructeur avec tous les champs
     */
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
