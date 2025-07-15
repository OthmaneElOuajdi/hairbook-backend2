package com.hairbook.hairbook_backend.dto.stats;

import java.util.List;
import java.util.Map;

/**
 * DTO représentant un ensemble de statistiques liées aux rendez-vous et aux clients dans Hairbook.
 *
 * <p>Contient des statistiques générales, par jour, par service, par créneau horaire, et sur les clients.</p>
 */
public class StatisticsResponse {

    // ----- Statistiques générales -----

    /** Nombre total de rendez-vous sur la période */
    private long totalAppointments;

    /** Nombre de rendez-vous complétés */
    private long completedAppointments;

    /** Nombre de rendez-vous annulés */
    private long cancelledAppointments;

    /** Nombre de rendez-vous non honorés (no-show) */
    private long noShowAppointments;

    /** Taux de complétion des rendez-vous (en %) */
    private double completionRate;

    // ----- Statistiques détaillées -----

    /** Nombre de rendez-vous regroupés par jour (ex: date → count) */
    private List<Map<String, Object>> appointmentsByDay;

    /** Nombre de rendez-vous regroupés par service */
    private List<Map<String, Object>> appointmentsByService;

    /** Nombre de rendez-vous regroupés par créneau horaire */
    private List<Map<String, Object>> appointmentsByTimeSlot;

    // ----- Statistiques clients -----

    /** Nombre total de clients */
    private long totalClients;

    /** Nombre de clients ayant pris rendez-vous dans la période */
    private long activeClients;

    /** Nombre de nouveaux clients inscrits ce mois-ci */
    private long newClientsThisMonth;

    /** Nombre de clients récurrents */
    private long returningClients;

    /**
     * Constructeur vide.
     */
    public StatisticsResponse() {
    }

    /**
     * Constructeur avec tous les champs.
     */
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

    // ----- Getters et Setters -----

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
