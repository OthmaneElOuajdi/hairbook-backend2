package com.hairbook.hairbook_backend.repository;

import com.hairbook.hairbook_backend.entity.Appointment;
import com.hairbook.hairbook_backend.entity.AppointmentStatus;
import com.hairbook.hairbook_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour l'entité {@link Appointment}.
 * Fournit des méthodes pour gérer et interroger les rendez-vous.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    /**
     * Récupère tous les rendez-vous d’un utilisateur.
     *
     * @param user l’utilisateur concerné
     * @return liste des rendez-vous
     */
    List<Appointment> findByUser(User user);

    /**
     * Récupère les rendez-vous à venir d’un utilisateur.
     *
     * @param user      l’utilisateur concerné
     * @param startTime date/heure actuelle
     * @return liste des rendez-vous futurs
     */
    List<Appointment> findByUserAndStartTimeAfter(User user, LocalDateTime startTime);

    /**
     * Récupère les rendez-vous d’un utilisateur selon leur statut.
     *
     * @param user   l’utilisateur concerné
     * @param status le statut du rendez-vous
     * @return liste des rendez-vous filtrés
     */
    List<Appointment> findByUserAndStatus(User user, AppointmentStatus status);

    /**
     * Récupère les rendez-vous planifiés entre deux dates.
     */
    List<Appointment> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Récupère les rendez-vous terminés entre deux dates.
     */
    List<Appointment> findByEndTimeBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Récupère les rendez-vous en cours dans une plage horaire.
     *
     * @param start date/heure de début
     * @param end   date/heure de fin
     * @return liste des rendez-vous chevauchants
     */
    List<Appointment> findByStartTimeBeforeAndEndTimeAfter(LocalDateTime start, LocalDateTime end);

    /**
     * Récupère les rendez-vous ayant un certain statut.
     *
     * @param status statut du rendez-vous
     * @return liste filtrée
     */
    List<Appointment> findByStatus(AppointmentStatus status);

    /**
     * Récupère les rendez-vous ayant un certain statut dans une plage horaire.
     */
    List<Appointment> findByStatusAndStartTimeBetween(AppointmentStatus status, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Récupère les rendez-vous qui chevauchent une période donnée,
     * en excluant ceux annulés ou non honorés.
     *
     * @param startTime date/heure de début
     * @param endTime   date/heure de fin
     * @return liste des rendez-vous en conflit
     */
    @Query("""
           SELECT a FROM Appointment a
           WHERE ((a.startTime <= :endTime) AND (a.endTime >= :startTime))
           AND a.status NOT IN ('CANCELLED', 'NO_SHOW')
           """)
    List<Appointment> findOverlappingAppointments(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * Statistiques : Nombre de rendez-vous par jour entre deux dates.
     * N'inclut pas les rendez-vous annulés ou manqués.
     *
     * @param startDate date de début
     * @param endDate   date de fin
     * @return liste contenant [date, nombre de rendez-vous]
     */
    @Query("""
           SELECT FUNCTION('DATE', a.startTime) as date, COUNT(a) as count
           FROM Appointment a
           WHERE a.startTime BETWEEN :startDate AND :endDate
           AND a.status NOT IN ('CANCELLED', 'NO_SHOW')
           GROUP BY FUNCTION('DATE', a.startTime)
           ORDER BY date
           """)
    List<Object[]> countAppointmentsByDay(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Statistiques : Nombre de rendez-vous par service entre deux dates.
     * N'inclut pas les rendez-vous annulés ou manqués.
     *
     * @param startDate date de début
     * @param endDate   date de fin
     * @return liste contenant [nom du service, nombre de rendez-vous]
     */
    @Query("""
           SELECT a.service.name, COUNT(a) as count
           FROM Appointment a
           WHERE a.startTime BETWEEN :startDate AND :endDate
           AND a.status NOT IN ('CANCELLED', 'NO_SHOW')
           GROUP BY a.service.name
           ORDER BY count DESC
           """)
    List<Object[]> countAppointmentsByService(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
