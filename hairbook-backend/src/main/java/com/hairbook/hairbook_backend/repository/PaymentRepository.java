package com.hairbook.hairbook_backend.repository;

import com.hairbook.hairbook_backend.entity.Payment;
import com.hairbook.hairbook_backend.entity.Payment.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité {@link Payment}.
 * Fournit des méthodes pour effectuer des opérations CRUD
 * ainsi que des recherches spécifiques sur les paiements.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Récupère tous les paiements associés à un rendez-vous spécifique.
     *
     * @param appointmentId l'identifiant du rendez-vous
     * @return liste de paiements
     */
    List<Payment> findByAppointmentId(Long appointmentId);

    /**
     * Recherche un paiement par son identifiant de transaction.
     *
     * @param transactionId identifiant de la transaction
     * @return un paiement s'il est trouvé
     */
    Optional<Payment> findByTransactionId(String transactionId);

    /**
     * Recherche un paiement par son identifiant d’intention de paiement (par ex. Stripe).
     *
     * @param paymentIntentId identifiant de l’intention de paiement
     * @return un paiement s'il est trouvé
     */
    Optional<Payment> findByPaymentIntentId(String paymentIntentId);

    /**
     * Récupère les paiements ayant un certain statut.
     *
     * @param status le statut de paiement (PENDING, COMPLETED, etc.)
     * @return liste de paiements
     */
    List<Payment> findByStatus(PaymentStatus status);

    /**
     * Récupère tous les paiements d’un utilisateur (via les rendez-vous).
     *
     * @param userId identifiant de l'utilisateur
     * @return liste de paiements
     */
    List<Payment> findByAppointmentUserId(Long userId);

    /**
     * Récupère les paiements d’un utilisateur avec pagination.
     *
     * @param userId   identifiant de l'utilisateur
     * @param pageable configuration de pagination
     * @return page de paiements
     */
    Page<Payment> findByAppointmentUserId(Long userId, Pageable pageable);

    /**
     * Récupère les paiements effectués entre deux dates.
     *
     * @param start date de début
     * @param end   date de fin
     * @return liste de paiements
     */
    List<Payment> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Récupère les paiements effectués entre deux dates avec pagination.
     *
     * @param start    date de début
     * @param end      date de fin
     * @param pageable configuration de pagination
     * @return page de paiements
     */
    Page<Payment> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
