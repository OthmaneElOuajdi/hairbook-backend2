package com.hairbook.hairbook_backend.dto.payment;

import com.hairbook.hairbook_backend.entity.Payment.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * DTO représentant une demande de paiement client.
 * Ce DTO est utilisé lors de la création d'un paiement pour un rendez-vous.
 */
public class PaymentRequest {

    /** ID du rendez-vous associé au paiement (obligatoire) */
    @NotNull(message = "L'ID du rendez-vous est obligatoire")
    private Long appointmentId;

    /** Montant du paiement (obligatoire et positif) */
    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être positif")
    private BigDecimal amount;

    /** Méthode de paiement choisie (ex: CARD, CASH, PAYPAL) */
    @NotNull(message = "La méthode de paiement est obligatoire")
    private PaymentMethod method;

    /** Devise utilisée (par défaut : EUR) */
    private String currency = "EUR";

    /** Notes ou remarques optionnelles sur le paiement */
    private String notes;

    /** Jeton de carte pour le paiement par carte bancaire (Stripe, etc.) */
    private String cardToken;

    /** ID de la commande PayPal, si méthode PayPal utilisée */
    private String paypalOrderId;

    // --- Constructeurs ---

    /**
     * Constructeur par défaut.
     */
    public PaymentRequest() {}

    /**
     * Constructeur avec tous les paramètres.
     */
    public PaymentRequest(Long appointmentId, BigDecimal amount, PaymentMethod method,
                          String currency, String notes, String cardToken, String paypalOrderId) {
        this.appointmentId = appointmentId;
        this.amount = amount;
        this.method = method;
        this.currency = currency;
        this.notes = notes;
        this.cardToken = cardToken;
        this.paypalOrderId = paypalOrderId;
    }

    // --- Getters ---

    /** @return l'ID du rendez-vous associé */
    public Long getAppointmentId() {
        return appointmentId;
    }

    /** @return le montant à payer */
    public BigDecimal getAmount() {
        return amount;
    }

    /** @return la méthode de paiement sélectionnée */
    public PaymentMethod getMethod() {
        return method;
    }

    /** @return la devise du paiement */
    public String getCurrency() {
        return currency;
    }

    /** @return les notes associées au paiement */
    public String getNotes() {
        return notes;
    }

    /** @return le jeton de carte bancaire (Stripe) */
    public String getCardToken() {
        return cardToken;
    }

    /** @return l'identifiant de commande PayPal */
    public String getPaypalOrderId() {
        return paypalOrderId;
    }

    // --- Setters ---

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public void setPaypalOrderId(String paypalOrderId) {
        this.paypalOrderId = paypalOrderId;
    }
}
