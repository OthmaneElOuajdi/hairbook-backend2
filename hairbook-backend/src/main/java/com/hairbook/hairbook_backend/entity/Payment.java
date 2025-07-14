package com.hairbook.hairbook_backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Représente un paiement associé à un rendez-vous dans l'application Hairbook.
 *
 * <p>Chaque paiement contient le montant, le mode de paiement, le statut, une référence au rendez-vous,
 * ainsi que des métadonnées pour le suivi (transaction, devise, notes, timestamps).</p>
 *
 * <p>Cette entité est mappée à la table <strong>payments</strong> dans la base de données.</p>
 *
 * @see Appointment
 */
@Entity
@Table(name = "payments")
public class Payment {

    /**
     * Identifiant unique du paiement (généré automatiquement).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Rendez-vous lié à ce paiement (relation ManyToOne).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    /**
     * Montant du paiement.
     */
    @Column(nullable = false)
    private BigDecimal amount;

    /**
     * Statut actuel du paiement (ex. : en attente, complété, annulé...).
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    /**
     * Méthode utilisée pour le paiement.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    /**
     * Devise utilisée pour le paiement (par défaut : EUR).
     */
    @Column(nullable = false)
    private String currency = "EUR";

    /**
     * Identifiant de transaction externe (fourni par le prestataire de paiement).
     */
    private String transactionId;

    /**
     * Identifiant de l’intention de paiement (ex. Stripe).
     */
    private String paymentIntentId;

    /**
     * Notes ou commentaires additionnels liés au paiement.
     */
    @Column(columnDefinition = "TEXT")
    private String notes;

    /**
     * Date de création du paiement (définie automatiquement).
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Date de dernière mise à jour du paiement.
     */
    private LocalDateTime updatedAt;

    /**
     * Initialise les champs {@code createdAt} et {@code updatedAt} à la création.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    /**
     * Met à jour le champ {@code updatedAt} à chaque modification.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ----- Enums internes -----

    /**
     * Statuts possibles pour un paiement.
     */
    public enum PaymentStatus {
        /** Paiement en attente de traitement. */
        PENDING,

        /** Paiement effectué avec succès. */
        COMPLETED,

        /** Paiement échoué. */
        FAILED,

        /** Paiement remboursé. */
        REFUNDED,

        /** Paiement annulé. */
        CANCELLED
    }

    /**
     * Méthodes de paiement disponibles.
     */
    public enum PaymentMethod {
        /** Carte bancaire. */
        CREDIT_CARD,

        /** Paiement via PayPal. */
        PAYPAL,

        /** Virement bancaire. */
        BANK_TRANSFER,

        /** Paiement en espèces. */
        CASH,

        /** Autre méthode de paiement. */
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

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Payment() {}

    /**
     * Constructeur complet.
     *
     * @param id identifiant du paiement
     * @param appointment rendez-vous lié
     * @param amount montant payé
     * @param status statut du paiement
     * @param method méthode de paiement
     * @param currency devise utilisée
     * @param transactionId identifiant de transaction externe
     * @param paymentIntentId identifiant d’intention de paiement
     * @param notes remarques éventuelles
     * @param createdAt date de création
     * @param updatedAt date de mise à jour
     */
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
