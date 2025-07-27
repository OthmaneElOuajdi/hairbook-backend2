package com.hairbook.hairbook_backend.repository;

import com.hairbook.hairbook_backend.entity.Appointment;
import com.hairbook.hairbook_backend.entity.AppointmentStatus;
import com.hairbook.hairbook_backend.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Repository pour l'entité Appointment - Fournit des méthodes pour gérer et interroger les rendez-vous")
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Schema(description = "Récupère tous les rendez-vous d'un utilisateur")
    List<Appointment> findByUser(User user);

    @Schema(description = "Récupère les rendez-vous à venir d'un utilisateur")
    List<Appointment> findByUserAndStartTimeAfter(User user, LocalDateTime startTime);

    @Schema(description = "Récupère les rendez-vous d'un utilisateur selon leur statut")
    List<Appointment> findByUserAndStatus(User user, AppointmentStatus status);

    @Schema(description = "Récupère les rendez-vous planifiés entre deux dates")
    List<Appointment> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    @Schema(description = "Récupère les rendez-vous terminés entre deux dates")
    List<Appointment> findByEndTimeBetween(LocalDateTime start, LocalDateTime end);

    @Schema(description = "Récupère les rendez-vous en cours dans une plage horaire")
    List<Appointment> findByStartTimeBeforeAndEndTimeAfter(LocalDateTime start, LocalDateTime end);

    @Schema(description = "Récupère les rendez-vous ayant un certain statut")
    List<Appointment> findByStatus(AppointmentStatus status);

    @Schema(description = "Récupère les rendez-vous ayant un certain statut dans une plage horaire")
    List<Appointment> findByStatusAndStartTimeBetween(AppointmentStatus status, LocalDateTime startTime, LocalDateTime endTime);

    @Schema(description = "Récupère les rendez-vous qui chevauchent une période donnée, en excluant ceux annulés ou non honorés")
    @Query("""
           SELECT a FROM Appointment a
           WHERE ((a.startTime <= :endTime) AND (a.endTime >= :startTime))
           AND a.status NOT IN ('CANCELLED', 'NO_SHOW')
           """)
    List<Appointment> findOverlappingAppointments(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    @Schema(description = "Statistiques : Nombre de rendez-vous par jour entre deux dates - N'inclut pas les rendez-vous annulés ou manqués")
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

    @Schema(description = "Statistiques : Nombre de rendez-vous par service entre deux dates - N'inclut pas les rendez-vous annulés ou manqués")
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
