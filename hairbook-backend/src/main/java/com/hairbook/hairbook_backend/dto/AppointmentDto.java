package com.hairbook.hairbook_backend.dto;

import com.hairbook.hairbook_backend.entity.AppointmentStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * DTO pour les rendez-vous, utilisé pour transférer les données entre les couches.
 */
public class AppointmentDto {

    /**
     * Identifiant du rendez-vous.
     */
    private Long id;

    /**
     * Identifiant de l'utilisateur lié au rendez-vous.
     */
    private Long userId;

    /**
     * Identifiant du service lié au rendez-vous.
     */
    @NotNull(message = "L'ID du service est obligatoire")
    private Long serviceId;

    /**
     * Nom du service (pour affichage uniquement).
     */
    private String serviceName;

    /**
     * Heure de début du rendez-vous.
     */
    @NotNull(message = "L'heure de début est obligatoire")
    private LocalDateTime startTime;

    /**
     * Heure de fin du rendez-vous.
     */
    @NotNull(message = "L'heure de fin est obligatoire")
    private LocalDateTime endTime;

    /**
     * Statut du rendez-vous.
     */
    private AppointmentStatus status;

    /**
     * Notes supplémentaires.
     */
    private String notes;

    // Informations utilisateur (pour affichage)

    /**
     * Nom complet de l'utilisateur.
     */
    private String userName;

    /**
     * Adresse e-mail de l'utilisateur.
     */
    private String userEmail;

    /**
     * Numéro de téléphone de l'utilisateur.
     */
    private String userPhone;

    /**
     * Constructeur par défaut.
     */
    public AppointmentDto() {
    }

    /**
     * Constructeur avec paramètres.
     *
     * @param id           identifiant du rendez-vous
     * @param userId       identifiant de l'utilisateur
     * @param serviceId    identifiant du service
     * @param serviceName  nom du service
     * @param startTime    heure de début
     * @param endTime      heure de fin
     * @param status       statut du rendez-vous
     * @param notes        remarques éventuelles
     * @param userName     nom complet de l'utilisateur
     * @param userEmail    email de l'utilisateur
     * @param userPhone    téléphone de l'utilisateur
     */
    public AppointmentDto(Long id, Long userId, Long serviceId, String serviceName, LocalDateTime startTime,
                          LocalDateTime endTime, AppointmentStatus status, String notes,
                          String userName, String userEmail, String userPhone) {
        this.id = id;
        this.userId = userId;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.notes = notes;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
    }

    // Getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
