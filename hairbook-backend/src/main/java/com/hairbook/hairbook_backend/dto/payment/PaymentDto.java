package com.hairbook.hairbook_backend.dto.payment;

import com.hairbook.hairbook_backend.entity.Payment.PaymentMethod;
import com.hairbook.hairbook_backend.entity.Payment.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "DTO représentant les informations de paiement liées à un rendez-vous")
public class PaymentDto {

    @Schema(description = "Identifiant du paiement", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identifiant du rendez-vous associé", example = "1")
    private Long appointmentId;

    @Schema(description = "Nom du service réservé", example = "Coupe et brushing", accessMode = Schema.AccessMode.READ_ONLY)
    private String serviceName;

    @Schema(description = "Montant payé", example = "45.50")
    private BigDecimal amount;

    @Schema(description = "Statut du paiement", example = "COMPLETED")
    private PaymentStatus status;

    @Schema(description = "Méthode de paiement", example = "CARD")
    private PaymentMethod method;

    @Schema(description = "Devise utilisée", example = "EUR", defaultValue = "EUR")
    private String currency;

    @Schema(description = "Identifiant de transaction externe", example = "pi_1234567890")
    private String transactionId;

    @Schema(description = "Identifiant d'intention de paiement Stripe", example = "pi_1234567890_secret_abc")
    private String paymentIntentId;

    @Schema(description = "Notes internes ou commentaires de paiement", example = "Paiement effectué avec succès")
    private String notes;

    @Schema(description = "Date de création du paiement", example = "2024-01-15T10:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Date de dernière mise à jour", example = "2024-01-15T10:05:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Schema(description = "Nom complet du client", example = "John Doe", accessMode = Schema.AccessMode.READ_ONLY)
    private String clientName;

    @Schema(description = "Email du client", example = "john.doe@example.com", accessMode = Schema.AccessMode.READ_ONLY)
    private String clientEmail;

    @Schema(description = "Date du rendez-vous associé", example = "2024-01-15T14:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime appointmentDate;

    public PaymentDto() {}

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
