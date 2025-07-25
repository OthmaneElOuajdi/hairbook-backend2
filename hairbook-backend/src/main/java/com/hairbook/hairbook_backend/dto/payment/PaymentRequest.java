package com.hairbook.hairbook_backend.dto.payment;

import com.hairbook.hairbook_backend.entity.Payment.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "DTO représentant une demande de paiement client pour un rendez-vous")
public class PaymentRequest {

    @Schema(description = "ID du rendez-vous associé au paiement", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "L'ID du rendez-vous est obligatoire")
    private Long appointmentId;

    @Schema(description = "Montant du paiement", example = "45.50", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0")
    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être positif")
    private BigDecimal amount;

    @Schema(description = "Méthode de paiement choisie", example = "CARD", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La méthode de paiement est obligatoire")
    private PaymentMethod method;

    @Schema(description = "Devise utilisée", example = "EUR", defaultValue = "EUR")
    private String currency = "EUR";

    @Schema(description = "Notes ou remarques optionnelles sur le paiement", example = "Paiement pour coupe de cheveux")
    private String notes;

    @Schema(description = "Jeton de carte pour le paiement par carte bancaire", example = "tok_1234567890")
    private String cardToken;

    @Schema(description = "ID de la commande PayPal si méthode PayPal utilisée", example = "PAYPAL-ORDER-123")
    private String paypalOrderId;

    public PaymentRequest() {}

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

    public Long getAppointmentId() {
        return appointmentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public String getCurrency() {
        return currency;
    }

    public String getNotes() {
        return notes;
    }

    public String getCardToken() {
        return cardToken;
    }

    public String getPaypalOrderId() {
        return paypalOrderId;
    }

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
