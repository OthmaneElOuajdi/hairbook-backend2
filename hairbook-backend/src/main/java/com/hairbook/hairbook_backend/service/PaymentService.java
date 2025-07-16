package com.hairbook.hairbook_backend.service;

import com.hairbook.hairbook_backend.dto.payment.PaymentDto;
import com.hairbook.hairbook_backend.entity.Appointment;
import com.hairbook.hairbook_backend.entity.Payment;
import com.hairbook.hairbook_backend.entity.Payment.PaymentStatus;
import com.hairbook.hairbook_backend.exception.ResourceNotFoundException;
import com.hairbook.hairbook_backend.repository.AppointmentRepository;
import com.hairbook.hairbook_backend.repository.PaymentRepository;
import com.hairbook.hairbook_backend.dto.payment.PaymentRequest;
import com.hairbook.hairbook_backend.util.LoggingService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import jakarta.annotation.PostConstruct;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service responsable de la gestion des paiements Stripe.
 * Gère la création, confirmation, annulation, remboursement et récupération des paiements.
 */
@Service
public class PaymentService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private NotificationService notificationService;

    /**
     * Initialise la clé API Stripe après injection.
     */
    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    /**
     * Crée une intention de paiement Stripe pour un rendez-vous donné.
     *
     * @param paymentRequest données nécessaires à la création du paiement
     * @return un map contenant le clientSecret et les informations du paiement
     */
    @Transactional
    public Map<String, Object> createPaymentIntent(PaymentRequest paymentRequest) {
        try {
            Appointment appointment = appointmentRepository.findById(paymentRequest.getAppointmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Rendez-vous", "id", paymentRequest.getAppointmentId()));

            long amountInCents = paymentRequest.getAmount().multiply(new BigDecimal(100)).longValue();

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency(paymentRequest.getCurrency().toLowerCase())
                    .setDescription("Paiement pour rendez-vous #" + appointment.getId())
                    .putMetadata("appointmentId", appointment.getId().toString())
                    .setReceiptEmail(appointment.getUser().getEmail())
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            Payment payment = new Payment();
            payment.setAppointment(appointment);
            payment.setAmount(paymentRequest.getAmount());
            payment.setStatus(PaymentStatus.PENDING);
            payment.setMethod(paymentRequest.getMethod());
            payment.setCurrency(paymentRequest.getCurrency());
            payment.setPaymentIntentId(paymentIntent.getId());
            payment.setNotes(paymentRequest.getNotes());

            paymentRepository.save(payment);

            Map<String, Object> response = new HashMap<>();
            response.put("paymentId", payment.getId());
            response.put("clientSecret", paymentIntent.getClientSecret());
            response.put("amount", paymentRequest.getAmount());
            response.put("currency", paymentRequest.getCurrency());

            return response;
        } catch (StripeException e) {
            LoggingService.logError(this.getClass(), "Erreur lors de la création de l'intention de paiement Stripe", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la création du paiement: " + e.getMessage());
        }
    }

    /**
     * Confirme un paiement après traitement par Stripe.
     *
     * @param paymentIntentId ID de l'intention de paiement
     * @param transactionId   ID de la transaction Stripe
     * @return le paiement confirmé sous forme de DTO
     */
    @Transactional
    public PaymentDto confirmPayment(String paymentIntentId, String transactionId) {
        Payment payment = paymentRepository.findByPaymentIntentId(paymentIntentId)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", "paymentIntentId", paymentIntentId));

        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setTransactionId(transactionId);
        payment = paymentRepository.save(payment);

        Appointment appointment = payment.getAppointment();
        String title = "Paiement confirmé";
        String message = "Votre paiement de " + payment.getAmount() + " " + payment.getCurrency() +
                " pour votre rendez-vous du " + appointment.getStartTime().toLocalDate() +
                " a été confirmé. Merci!";
        String actionUrl = "/appointments/" + appointment.getId();

        notificationService.createSystemNotification(appointment.getUser().getId(), title, message, actionUrl);
        notificationService.sendEmailNotification(
                appointment.getUser().getId(),
                "Confirmation de paiement - Hairbook",
                "Bonjour " + appointment.getUser().getFirstName() + ",\n\n" +
                        "Nous vous confirmons que votre paiement de " + payment.getAmount() + " " + payment.getCurrency() +
                        " pour votre rendez-vous du " + appointment.getStartTime().toLocalDate() +
                        " a bien été reçu.\n\n" +
                        "Référence de transaction: " + transactionId + "\n\n" +
                        "Merci pour votre confiance.\n\nL'équipe Hairbook",
                actionUrl
        );

        return convertToDto(payment);
    }

    /**
     * Annule un paiement si celui-ci n’a pas encore été complété.
     *
     * @param paymentId ID du paiement à annuler
     * @return le paiement mis à jour sous forme de DTO
     */
    @Transactional
    public PaymentDto cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", "id", paymentId));

        if (payment.getStatus() == PaymentStatus.COMPLETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossible d'annuler un paiement déjà complété");
        }

        try {
            if (payment.getPaymentIntentId() != null) {
                PaymentIntent paymentIntent = PaymentIntent.retrieve(payment.getPaymentIntentId());
                paymentIntent.cancel();
            }

            payment.setStatus(PaymentStatus.CANCELLED);
            return convertToDto(paymentRepository.save(payment));
        } catch (StripeException e) {
            LoggingService.logError(this.getClass(), "Erreur lors de l'annulation du paiement Stripe", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de l'annulation du paiement: " + e.getMessage());
        }
    }

    /**
     * Rembourse un paiement complété.
     *
     * @param paymentId ID du paiement à rembourser
     * @param reason    raison du remboursement
     * @return le paiement mis à jour sous forme de DTO
     */
    @Transactional
    public PaymentDto refundPayment(Long paymentId, String reason) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", "id", paymentId));

        if (payment.getStatus() != PaymentStatus.COMPLETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seuls les paiements complétés peuvent être remboursés");
        }

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("payment_intent", payment.getPaymentIntentId());
            params.put("reason", "requested_by_customer");

            com.stripe.model.Refund.create(params);

            payment.setStatus(PaymentStatus.REFUNDED);
            payment.setNotes(payment.getNotes() + "\nRemboursement: " + reason);
            payment = paymentRepository.save(payment);

            Appointment appointment = payment.getAppointment();
            String title = "Remboursement effectué";
            String message = "Votre paiement de " + payment.getAmount() + " " + payment.getCurrency() +
                    " pour votre rendez-vous du " + appointment.getStartTime().toLocalDate() + " a été remboursé.";

            notificationService.createSystemNotification(appointment.getUser().getId(), title, message, null);
            notificationService.sendEmailNotification(
                    appointment.getUser().getId(),
                    "Confirmation de remboursement - Hairbook",
                    "Bonjour " + appointment.getUser().getFirstName() + ",\n\n" +
                            "Votre paiement de " + payment.getAmount() + " " + payment.getCurrency() +
                            " pour votre rendez-vous du " + appointment.getStartTime().toLocalDate() +
                            " a bien été remboursé.\n\n" +
                            "Raison: " + reason + "\n\nCordialement,\nL'équipe Hairbook",
                    null
            );

            return convertToDto(payment);
        } catch (StripeException e) {
            LoggingService.logError(this.getClass(), "Erreur lors du remboursement du paiement Stripe", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors du remboursement: " + e.getMessage());
        }
    }

    /**
     * Récupère tous les paiements.
     *
     * @return liste de tous les paiements sous forme de DTO
     */
    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Récupère les paiements paginés.
     *
     * @param pageable pagination
     * @return page de paiements sous forme de DTO
     */
    public Page<PaymentDto> getPaymentsPaginated(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(this::convertToDto);
    }

    /**
     * Récupère un paiement par son identifiant.
     *
     * @param paymentId ID du paiement
     * @return le paiement sous forme de DTO
     */
    public PaymentDto getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", "id", paymentId));
    }

    /**
     * Récupère les paiements effectués par un utilisateur.
     *
     * @param userId ID de l'utilisateur
     * @return liste des paiements de l'utilisateur
     */
    public List<PaymentDto> getUserPayments(Long userId) {
        return paymentRepository.findByAppointmentUserId(userId)
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Récupère les paiements d’un utilisateur avec pagination.
     *
     * @param userId   ID de l'utilisateur
     * @param pageable pagination
     * @return page de paiements
     */
    public Page<PaymentDto> getUserPaymentsPaginated(Long userId, Pageable pageable) {
        return paymentRepository.findByAppointmentUserId(userId, pageable).map(this::convertToDto);
    }

    /**
     * Récupère les paiements effectués entre deux dates.
     *
     * @param start date de début
     * @param end   date de fin
     * @return liste des paiements dans la période donnée
     */
    public List<PaymentDto> getPaymentsByPeriod(LocalDateTime start, LocalDateTime end) {
        return paymentRepository.findByCreatedAtBetween(start, end).stream()
                .map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Récupère tous les paiements associés à un rendez-vous.
     *
     * @param appointmentId ID du rendez-vous
     * @return liste des paiements liés
     */
    public List<PaymentDto> getPaymentsByAppointment(Long appointmentId) {
        return paymentRepository.findByAppointmentId(appointmentId).stream()
                .map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Convertit une entité Payment en DTO.
     *
     * @param payment entité Payment
     * @return DTO correspondant
     */
    private PaymentDto convertToDto(Payment payment) {
        PaymentDto dto = new PaymentDto();
        dto.setId(payment.getId());
        dto.setAppointmentId(payment.getAppointment().getId());
        dto.setServiceName(payment.getAppointment().getService().getName());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setMethod(payment.getMethod());
        dto.setCurrency(payment.getCurrency());
        dto.setTransactionId(payment.getTransactionId());
        dto.setPaymentIntentId(payment.getPaymentIntentId());
        dto.setNotes(payment.getNotes());
        dto.setCreatedAt(payment.getCreatedAt());
        dto.setUpdatedAt(payment.getUpdatedAt());

        dto.setClientName(payment.getAppointment().getUser().getFirstName() + " " +
                payment.getAppointment().getUser().getLastName());
        dto.setClientEmail(payment.getAppointment().getUser().getEmail());
        dto.setAppointmentDate(payment.getAppointment().getStartTime());

        return dto;
    }
}
