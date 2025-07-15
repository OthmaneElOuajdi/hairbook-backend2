package com.hairbook.hairbook_backend.dto.payment;

import com.hairbook.hairbook_backend.entity.Payment.PaymentMethod;
import com.hairbook.hairbook_backend.entity.Payment.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO représentant les informations de paiement liées à un rendez-vous.
 */
public class PaymentDto {

    // --- Champs principaux ---

    /** Identifiant du paiement */
    private Long id;

    /** Identifiant du rendez-vous associé */
    private Long appointmentId;

    /** Nom du service réservé */
    private String serviceName;

    /** Montant payé */
    private BigDecimal amount;

    /** Statut du paiement (ex: COMPLETED, PENDING) */
    private PaymentStatus status;

    /** Méthode de paiement (ex: CARD, CASH) */
    private PaymentMethod method;

    /** Devise utilisée (ex: EUR, USD) */
    private String currency;

    /** Identifiant de transaction (Stripe, etc.) */
    private String transactionId;

    /** Identifiant d’intention de paiement (Stripe, etc.) */
    private String paymentIntentId;

    /** Notes internes ou commentaires de paiement */
    private String notes;

    /** Date de création du paiement */
    private LocalDateTime createdAt;

    /** Date de dernière mise à jour */
    private LocalDateTime updatedAt;

    // --- Informations complémentaires pour affichage ---

    /** Nom complet du client */
    private String clientName;

    /** Email du client */
    private String clientEmail;

    /** Date du rendez-vous associé */
    private LocalDateTime appointmentDate;

    // --- Constructeurs ---

    /**
     * Constructeur vide.
     */
    public PaymentDto() {}

    /**
     * Constructeur complet avec tous les champs.
     */
    public PaymentDto(Long id, Long appointmentId, String serviceName, BigDecimal amount,
                      PaymentStatus status, PaymentMethod method, String currency,
                      String transactionId, String paymentIntentId, String notes,
                      LocalDateTime createdAt, LocalDateTime updatedAt,
                      String clientName, String clientEmail, LocalDateTime appointmentDate) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.serviceName = serviceName;
        this.amount = amount;
        this.status = status;
        this.method = method;
        this.currency = currency;
        this.transactionId = transactionId;
        this.paymentIntentId = paymentIntentId;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.appointmentDate = appointmentDate;
    }

    // --- Getters ---

    public Long getId() {
        return id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public String getCurrency() {
        return currency;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    // --- Setters ---

    public void setId(Long id) {
        this.id = id;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setPaymentIntentId(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
