package com.hairbook.hairbook_backend.dto;

import com.hairbook.hairbook_backend.entity.AppointmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "DTO pour les rendez-vous, utilisé pour transférer les données entre les couches")
public class AppointmentDto {

    @Schema(description = "Identifiant du rendez-vous", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identifiant de l'utilisateur lié au rendez-vous", example = "1")
    private Long userId;

    @Schema(description = "Identifiant du service lié au rendez-vous", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "L'ID du service est obligatoire")
    private Long serviceId;

    @Schema(description = "Nom du service (pour affichage uniquement)", example = "Coupe et brushing", accessMode = Schema.AccessMode.READ_ONLY)
    private String serviceName;

    @Schema(description = "Heure de début du rendez-vous", example = "2024-01-15T10:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "L'heure de début est obligatoire")
    private LocalDateTime startTime;

    @Schema(description = "Heure de fin du rendez-vous", example = "2024-01-15T11:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "L'heure de fin est obligatoire")
    private LocalDateTime endTime;

    @Schema(description = "Statut du rendez-vous", example = "SCHEDULED")
    private AppointmentStatus status;

    @Schema(description = "Notes supplémentaires", example = "Préfère les cheveux courts")
    private String notes;

    @Schema(description = "Nom complet de l'utilisateur (pour affichage)", example = "John Doe", accessMode = Schema.AccessMode.READ_ONLY)
    private String userName;

    @Schema(description = "Adresse e-mail de l'utilisateur (pour affichage)", example = "john.doe@example.com", accessMode = Schema.AccessMode.READ_ONLY)
    private String userEmail;

    @Schema(description = "Numéro de téléphone de l'utilisateur (pour affichage)", example = "0123456789", accessMode = Schema.AccessMode.READ_ONLY)
    private String userPhone;

    public AppointmentDto() {
    }

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
