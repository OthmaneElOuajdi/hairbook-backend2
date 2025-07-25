package com.hairbook.hairbook_backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Représente un paiement associé à un rendez-vous dans l'application Hairbook")
@Entity
@Table(name = "payments")
public class Payment {

    @Schema(description = "Identifiant unique du paiement", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Rendez-vous lié à ce paiement")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @Schema(description = "Montant du paiement en euros", example = "45.50", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(nullable = false)
    private BigDecimal amount;

    @Schema(description = "Statut actuel du paiement", example = "COMPLETED", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Schema(description = "Méthode utilisée pour le paiement", example = "CREDIT_CARD", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Schema(description = "Devise utilisée pour le paiement", example = "EUR", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(nullable = false)
    private String currency = "EUR";

    @Schema(description = "Identifiant de transaction externe", example = "txn_1234567890", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String transactionId;

    @Schema(description = "Identifiant de l'intention de paiement", example = "pi_1234567890", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String paymentIntentId;

    @Schema(description = "Notes ou commentaires additionnels liés au paiement", example = "Paiement effectué avec succès", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Column(columnDefinition = "TEXT")
    private String notes;

    @Schema(description = "Date de création du paiement", example = "2024-01-15T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Schema(description = "Date de dernière mise à jour du paiement", example = "2024-01-15T10:35:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ----- Enums internes -----

    @Schema(description = "Statuts possibles pour un paiement")
    public enum PaymentStatus {
        PENDING,
        COMPLETED,
        FAILED,
        REFUNDED,
        CANCELLED
    }

    @Schema(description = "Méthodes de paiement disponibles")
    public enum PaymentMethod {
        CREDIT_CARD,
        PAYPAL,
        BANK_TRANSFER,
        CASH,
        OTHER
    }

    // ----- Getters & Setters -----

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Appointment getAppointment() { return appointment; }

    public void setAppointment(Appointment appointment) { this.appointment = appointment; }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public PaymentStatus getStatus() { return status; }

    public void setStatus(PaymentStatus status) { this.status = status; }

    public PaymentMethod getMethod() { return method; }

    public void setMethod(PaymentMethod method) { this.method = method; }

    public String getCurrency() { return currency; }

    public void setCurrency(String currency) { this.currency = currency; }

    public String getTransactionId() { return transactionId; }

    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getPaymentIntentId() { return paymentIntentId; }

    public void setPaymentIntentId(String paymentIntentId) { this.paymentIntentId = paymentIntentId; }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // ----- Constructeurs -----

    public Payment() {}

    public Payment(Long id, Appointment appointment, BigDecimal amount, PaymentStatus status,
                   PaymentMethod method, String currency, String transactionId, String paymentIntentId,
                   String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.appointment = appointment;
        this.amount = amount;
        this.status = status;
        this.method = method;
        this.currency = currency;
        this.transactionId = transactionId;
        this.paymentIntentId = paymentIntentId;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
