package com.hairbook.hairbook_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Représente un rendez-vous pris par un utilisateur pour un service dans l'application Hairbook.
 *
 * <p>Chaque {@code Appointment} est lié à un utilisateur, un service, une période horaire, et possède un statut
 * permettant de suivre son état (planifié, confirmé, annulé, etc.).</p>
 *
 * <p>Cette entité est mappée à la table <strong>appointments</strong> dans la base de données.</p>
 *
 * @see User
 * @see Service
 * @see AppointmentStatus
 */
@Entity
@Table(name = "appointments")
public class Appointment {

    /**
     * Identifiant unique du rendez-vous.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Utilisateur qui a réservé ce rendez-vous.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Service réservé lors de ce rendez-vous.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    /**
     * Date et heure de début du rendez-vous.
     */
    @NotNull
    private LocalDateTime startTime;

    /**
     * Date et heure de fin du rendez-vous.
     */
    @NotNull
    private LocalDateTime endTime;

    /**
     * Statut actuel du rendez-vous.
     */
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    /**
     * Notes facultatives laissées par le client ou le personnel.
     */
    private String notes;

    /**
     * Date de création de la réservation (initialisée automatiquement).
     */
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * Date de dernière mise à jour (mise à jour automatiquement).
     */
    private LocalDateTime updatedAt;

    /**
     * Initialise les champs {@code createdAt} et {@code updatedAt} à la création de l'entité.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    /**
     * Met à jour automatiquement le champ {@code updatedAt} à chaque modification.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ----- Getters & Setters -----

    /** @return l'identifiant du rendez-vous */
    public Long getId() {
        return id;
    }

    /** @param id identifiant à définir */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return l'utilisateur associé */
    public User getUser() {
        return user;
    }

    /** @param user utilisateur à associer */
    public void setUser(User user) {
        this.user = user;
    }

    /** @return le service réservé */
    public Service getService() {
        return service;
    }

    /** @param service service à associer */
    public void setService(Service service) {
        this.service = service;
    }

    /** @return l’heure de début du rendez-vous */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /** @param startTime heure de début à définir */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /** @return l’heure de fin du rendez-vous */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /** @param endTime heure de fin à définir */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /** @return le statut du rendez-vous */
    public AppointmentStatus getStatus() {
        return status;
    }

    /** @param status statut à définir */
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    /** @return les notes associées au rendez-vous */
    public String getNotes() {
        return notes;
    }

    /** @param notes texte à définir */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /** @return la date de création du rendez-vous */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /** @param createdAt date de création à définir manuellement (rarement utilisé) */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /** @return la date de dernière mise à jour */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /** @param updatedAt date de mise à jour à définir */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // ----- Constructeurs -----

    /**
     * Constructeur sans argument requis par JPA.
     */
    public Appointment() {}

    /**
     * Constructeur complet.
     *
     * @param id identifiant du rendez-vous
     * @param user utilisateur concerné
     * @param service service réservé
     * @param startTime date/heure de début
     * @param endTime date/heure de fin
     * @param status statut du rendez-vous
     * @param notes commentaires ou remarques
     * @param createdAt date de création
     * @param updatedAt date de dernière mise à jour
     */
    public Appointment(Long id, User user, Service service, LocalDateTime startTime, LocalDateTime endTime,
                       AppointmentStatus status, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.service = service;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
