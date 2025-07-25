package com.hairbook.hairbook_backend.dto.stats;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;

@Schema(description = "DTO représentant un ensemble de statistiques liées aux rendez-vous et aux clients")
public class StatisticsResponse {

    @Schema(description = "Nombre total de rendez-vous sur la période", example = "150", accessMode = Schema.AccessMode.READ_ONLY)
    private long totalAppointments;

    @Schema(description = "Nombre de rendez-vous complétés", example = "120", accessMode = Schema.AccessMode.READ_ONLY)
    private long completedAppointments;

    @Schema(description = "Nombre de rendez-vous annulés", example = "20", accessMode = Schema.AccessMode.READ_ONLY)
    private long cancelledAppointments;

    @Schema(description = "Nombre de rendez-vous non honorés (no-show)", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
    private long noShowAppointments;

    @Schema(description = "Taux de complétion des rendez-vous (en %)", example = "80.0", accessMode = Schema.AccessMode.READ_ONLY)
    private double completionRate;

    @Schema(description = "Nombre de rendez-vous regroupés par jour", accessMode = Schema.AccessMode.READ_ONLY)
    private List<Map<String, Object>> appointmentsByDay;

    @Schema(description = "Nombre de rendez-vous regroupés par service", accessMode = Schema.AccessMode.READ_ONLY)
    private List<Map<String, Object>> appointmentsByService;

    @Schema(description = "Nombre de rendez-vous regroupés par créneau horaire", accessMode = Schema.AccessMode.READ_ONLY)
    private List<Map<String, Object>> appointmentsByTimeSlot;

    @Schema(description = "Nombre total de clients", example = "75", accessMode = Schema.AccessMode.READ_ONLY)
    private long totalClients;

    @Schema(description = "Nombre de clients ayant pris rendez-vous dans la période", example = "50", accessMode = Schema.AccessMode.READ_ONLY)
    private long activeClients;

    @Schema(description = "Nombre de nouveaux clients inscrits ce mois-ci", example = "15", accessMode = Schema.AccessMode.READ_ONLY)
    private long newClientsThisMonth;

    @Schema(description = "Nombre de clients récurrents", example = "35", accessMode = Schema.AccessMode.READ_ONLY)
    private long returningClients;

    public StatisticsResponse() {
    }

    public StatisticsResponse(long totalAppointments,
                              long completedAppointments,
                              long cancelledAppointments,
                              long noShowAppointments,
                              double completionRate,
                              List<Map<String, Object>> appointmentsByDay,
                              List<Map<String, Object>> appointmentsByService,
                              List<Map<String, Object>> appointmentsByTimeSlot,
                              long totalClients,
                              long activeClients,
                              long newClientsThisMonth,
                              long returningClients) {
        this.totalAppointments = totalAppointments;
        this.completedAppointments = completedAppointments;
        this.cancelledAppointments = cancelledAppointments;
        this.noShowAppointments = noShowAppointments;
        this.completionRate = completionRate;
        this.appointmentsByDay = appointmentsByDay;
        this.appointmentsByService = appointmentsByService;
        this.appointmentsByTimeSlot = appointmentsByTimeSlot;
        this.totalClients = totalClients;
        this.activeClients = activeClients;
        this.newClientsThisMonth = newClientsThisMonth;
        this.returningClients = returningClients;
    }

    public long getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(long totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public long getCompletedAppointments() {
        return completedAppointments;
    }

    public void setCompletedAppointments(long completedAppointments) {
        this.completedAppointments = completedAppointments;
    }

    public long getCancelledAppointments() {
        return cancelledAppointments;
    }

    public void setCancelledAppointments(long cancelledAppointments) {
        this.cancelledAppointments = cancelledAppointments;
    }

    public long getNoShowAppointments() {
        return noShowAppointments;
    }

    public void setNoShowAppointments(long noShowAppointments) {
        this.noShowAppointments = noShowAppointments;
    }

    public double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(double completionRate) {
        this.completionRate = completionRate;
    }

    public List<Map<String, Object>> getAppointmentsByDay() {
        return appointmentsByDay;
    }

    public void setAppointmentsByDay(List<Map<String, Object>> appointmentsByDay) {
        this.appointmentsByDay = appointmentsByDay;
    }

    public List<Map<String, Object>> getAppointmentsByService() {
        return appointmentsByService;
    }

    public void setAppointmentsByService(List<Map<String, Object>> appointmentsByService) {
        this.appointmentsByService = appointmentsByService;
    }

    public List<Map<String, Object>> getAppointmentsByTimeSlot() {
        return appointmentsByTimeSlot;
    }

    public void setAppointmentsByTimeSlot(List<Map<String, Object>> appointmentsByTimeSlot) {
        this.appointmentsByTimeSlot = appointmentsByTimeSlot;
    }

    public long getTotalClients() {
        return totalClients;
    }

    public void setTotalClients(long totalClients) {
        this.totalClients = totalClients;
    }

    public long getActiveClients() {
        return activeClients;
    }

    public void setActiveClients(long activeClients) {
        this.activeClients = activeClients;
    }

    public long getNewClientsThisMonth() {
        return newClientsThisMonth;
    }

    public void setNewClientsThisMonth(long newClientsThisMonth) {
        this.newClientsThisMonth = newClientsThisMonth;
    }

    public long getReturningClients() {
        return returningClients;
    }

    public void setReturningClients(long returningClients) {
        this.returningClients = returningClients;
    }
}
