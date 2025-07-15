package com.hairbook.hairbook_backend.repository;

import com.hairbook.hairbook_backend.entity.LoyaltyPoints;
import com.hairbook.hairbook_backend.entity.LoyaltyPoints.PointsType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour l'entité {@link LoyaltyPoints}.
 * Fournit des méthodes d'accès aux données relatives aux points de fidélité des utilisateurs.
 */
@Repository
public interface LoyaltyPointsRepository extends JpaRepository<LoyaltyPoints, Long> {

    /**
     * Récupère tous les points de fidélité d’un utilisateur.
     *
     * @param userId l’identifiant de l’utilisateur
     * @return liste des points de fidélité
     */
    List<LoyaltyPoints> findByUserId(Long userId);

    /**
     * Récupère les points de fidélité d’un utilisateur avec pagination.
     *
     * @param userId   l’identifiant de l’utilisateur
     * @param pageable pagination
     * @return page de points de fidélité
     */
    Page<LoyaltyPoints> findByUserId(Long userId, Pageable pageable);

    /**
     * Récupère les points de fidélité d’un utilisateur selon le type.
     *
     * @param userId l’identifiant de l’utilisateur
     * @param type   le type de points (EARNED, REDEEMED, etc.)
     * @return liste filtrée des points
     */
    List<LoyaltyPoints> findByUserIdAndType(Long userId, PointsType type);

    /**
     * Récupère les points associés à un rendez-vous spécifique.
     *
     * @param appointmentId identifiant du rendez-vous
     * @return liste des points liés
     */
    List<LoyaltyPoints> findByAppointmentId(Long appointmentId);

    /**
     * Récupère les points créés dans une période donnée.
     *
     * @param start date de début
     * @param end   date de fin
     * @return liste des points
     */
    List<LoyaltyPoints> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Calcule la somme totale des points d’un utilisateur, tous types confondus.
     *
     * @param userId identifiant de l’utilisateur
     * @return somme totale des points
     */
    @Query("SELECT SUM(lp.points) FROM LoyaltyPoints lp WHERE lp.user.id = :userId")
    Integer getTotalPointsByUserId(@Param("userId") Long userId);

    /**
     * Calcule la somme totale des points d’un type spécifique pour un utilisateur.
     *
     * @param userId identifiant de l’utilisateur
     * @param type   type de points
     * @return somme totale des points
     */
    @Query("SELECT SUM(lp.points) FROM LoyaltyPoints lp WHERE lp.user.id = :userId AND lp.type = :type")
    Integer getTotalPointsByUserIdAndType(@Param("userId") Long userId, @Param("type") PointsType type);

    /**
     * Calcule le total de points disponibles pour un utilisateur
     * (points gagnés + ajustés - utilisés - expirés).
     *
     * @param userId identifiant de l’utilisateur
     * @return points disponibles
     */
    @Query("""
           SELECT COALESCE(SUM(CASE WHEN lp.type = 'EARNED' OR lp.type = 'ADJUSTED' THEN lp.points ELSE 0 END), 0) -
                  COALESCE(SUM(CASE WHEN lp.type = 'REDEEMED' OR lp.type = 'EXPIRED' THEN lp.points ELSE 0 END), 0)
           FROM LoyaltyPoints lp WHERE lp.user.id = :userId
           """)
    Integer getAvailablePointsByUserId(@Param("userId") Long userId);

    /**
     * Récupère la date de la dernière activité (ajout ou utilisation de points) pour un utilisateur.
     *
     * @param userId identifiant de l’utilisateur
     * @return date de dernière activité
     */
    @Query("SELECT MAX(lp.createdAt) FROM LoyaltyPoints lp WHERE lp.user.id = :userId")
    LocalDateTime getLastActivityDateByUserId(@Param("userId") Long userId);

    /**
     * Récupère la date de la première activité liée aux points pour un utilisateur.
     *
     * @param userId identifiant de l’utilisateur
     * @return date de première activité
     */
    @Query("SELECT MIN(lp.createdAt) FROM LoyaltyPoints lp WHERE lp.user.id = :userId")
    LocalDateTime getFirstActivityDateByUserId(@Param("userId") Long userId);
}
