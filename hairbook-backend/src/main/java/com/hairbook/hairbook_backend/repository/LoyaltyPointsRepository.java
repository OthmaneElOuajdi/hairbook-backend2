package com.hairbook.hairbook_backend.repository;

import com.hairbook.hairbook_backend.entity.LoyaltyPoints;
import com.hairbook.hairbook_backend.entity.LoyaltyPoints.PointsType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Repository pour l'entité LoyaltyPoints - Fournit des méthodes d'accès aux données relatives aux points de fidélité des utilisateurs")
@Repository
public interface LoyaltyPointsRepository extends JpaRepository<LoyaltyPoints, Long> {

    @Schema(description = "Récupère tous les points de fidélité d'un utilisateur")
    List<LoyaltyPoints> findByUserId(Long userId);

    @Schema(description = "Récupère les points de fidélité d'un utilisateur avec pagination")
    Page<LoyaltyPoints> findByUserId(Long userId, Pageable pageable);

    @Schema(description = "Récupère les points de fidélité d'un utilisateur selon le type")
    List<LoyaltyPoints> findByUserIdAndType(Long userId, PointsType type);

    @Schema(description = "Récupère les points associés à un rendez-vous spécifique")
    List<LoyaltyPoints> findByAppointmentId(Long appointmentId);

    @Schema(description = "Récupère les points créés dans une période donnée")
    List<LoyaltyPoints> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Schema(description = "Calcule la somme totale des points d'un utilisateur, tous types confondus")
    @Query("SELECT SUM(lp.points) FROM LoyaltyPoints lp WHERE lp.user.id = :userId")
    Integer getTotalPointsByUserId(@Param("userId") Long userId);

    @Schema(description = "Calcule la somme totale des points d'un type spécifique pour un utilisateur")
    @Query("SELECT SUM(lp.points) FROM LoyaltyPoints lp WHERE lp.user.id = :userId AND lp.type = :type")
    Integer getTotalPointsByUserIdAndType(@Param("userId") Long userId, @Param("type") PointsType type);

    @Schema(description = "Calcule le total de points disponibles pour un utilisateur (points gagnés + ajustés - utilisés - expirés)")
    @Query("""
           SELECT COALESCE(SUM(CASE WHEN lp.type = 'EARNED' OR lp.type = 'ADJUSTED' THEN lp.points ELSE 0 END), 0) -
                  COALESCE(SUM(CASE WHEN lp.type = 'REDEEMED' OR lp.type = 'EXPIRED' THEN lp.points ELSE 0 END), 0)
           FROM LoyaltyPoints lp WHERE lp.user.id = :userId
           """)
    Integer getAvailablePointsByUserId(@Param("userId") Long userId);

    @Schema(description = "Récupère la date de la dernière activité (ajout ou utilisation de points) pour un utilisateur")
    @Query("SELECT MAX(lp.createdAt) FROM LoyaltyPoints lp WHERE lp.user.id = :userId")
    LocalDateTime getLastActivityDateByUserId(@Param("userId") Long userId);

    @Schema(description = "Récupère la date de la première activité liée aux points pour un utilisateur")
    @Query("SELECT MIN(lp.createdAt) FROM LoyaltyPoints lp WHERE lp.user.id = :userId")
    LocalDateTime getFirstActivityDateByUserId(@Param("userId") Long userId);
}
