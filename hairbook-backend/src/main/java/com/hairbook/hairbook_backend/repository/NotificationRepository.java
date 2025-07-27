package com.hairbook.hairbook_backend.repository;

import com.hairbook.hairbook_backend.entity.Notification;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Repository pour l'entité Notification - Fournit des méthodes pour récupérer et manipuler les notifications des utilisateurs")
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Schema(description = "Récupère toutes les notifications d'un utilisateur triées par date de création décroissante")
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Schema(description = "Récupère les notifications d'un utilisateur avec pagination, triées par date de création décroissante")
    Page<Notification> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    @Schema(description = "Récupère les notifications lues ou non lues d'un utilisateur, triées par date de création")
    List<Notification> findByUserIdAndReadOrderByCreatedAtDesc(Long userId, boolean read);

    @Schema(description = "Compte le nombre de notifications lues ou non lues pour un utilisateur donné")
    long countByUserIdAndRead(Long userId, boolean read);

    @Schema(description = "Récupère toutes les notifications créées avant une date donnée - Utile pour les nettoyages périodiques")
    List<Notification> findByCreatedAtBefore(LocalDateTime dateTime);
}
