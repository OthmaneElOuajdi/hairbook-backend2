package com.hairbook.hairbook_backend.repository;

import com.hairbook.hairbook_backend.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour l'entité {@link Notification}.
 * Fournit des méthodes pour récupérer et manipuler les notifications des utilisateurs.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Récupère toutes les notifications d’un utilisateur triées par date de création décroissante.
     *
     * @param userId l’identifiant de l’utilisateur
     * @return liste des notifications
     */
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    /**
     * Récupère les notifications d’un utilisateur avec pagination,
     * triées par date de création décroissante.
     *
     * @param userId   l’identifiant de l’utilisateur
     * @param pageable paramètres de pagination
     * @return page de notifications
     */
    Page<Notification> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    /**
     * Récupère les notifications lues ou non lues d’un utilisateur, triées par date de création.
     *
     * @param userId l’identifiant de l’utilisateur
     * @param read   état de lecture (true pour lues, false pour non lues)
     * @return liste des notifications
     */
    List<Notification> findByUserIdAndReadOrderByCreatedAtDesc(Long userId, boolean read);

    /**
     * Compte le nombre de notifications lues ou non lues pour un utilisateur donné.
     *
     * @param userId l’identifiant de l’utilisateur
     * @param read   état de lecture
     * @return nombre de notifications correspondantes
     */
    long countByUserIdAndRead(Long userId, boolean read);

    /**
     * Récupère toutes les notifications créées avant une date donnée.
     * Utile pour les nettoyages périodiques.
     *
     * @param dateTime date limite
     * @return liste des notifications plus anciennes que la date spécifiée
     */
    List<Notification> findByCreatedAtBefore(LocalDateTime dateTime);
}
