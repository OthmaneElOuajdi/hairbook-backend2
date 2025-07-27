package com.hairbook.hairbook_backend.repository;

import com.hairbook.hairbook_backend.entity.Payment;
import com.hairbook.hairbook_backend.entity.Payment.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Schema(description = "Repository pour l'entité Payment - Fournit des méthodes pour effectuer des opérations CRUD ainsi que des recherches spécifiques sur les paiements")
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Schema(description = "Récupère tous les paiements associés à un rendez-vous spécifique")
    List<Payment> findByAppointmentId(Long appointmentId);

    @Schema(description = "Recherche un paiement par son identifiant de transaction")
    Optional<Payment> findByTransactionId(String transactionId);

    @Schema(description = "Recherche un paiement par son identifiant d'intention de paiement (par ex. Stripe)")
    Optional<Payment> findByPaymentIntentId(String paymentIntentId);

    @Schema(description = "Récupère les paiements ayant un certain statut")
    List<Payment> findByStatus(PaymentStatus status);

    @Schema(description = "Récupère tous les paiements d'un utilisateur (via les rendez-vous)")
    List<Payment> findByAppointmentUserId(Long userId);

    @Schema(description = "Récupère les paiements d'un utilisateur avec pagination")
    Page<Payment> findByAppointmentUserId(Long userId, Pageable pageable);

    @Schema(description = "Récupère les paiements effectués entre deux dates")
    List<Payment> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Schema(description = "Récupère les paiements effectués entre deux dates avec pagination")
    Page<Payment> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
