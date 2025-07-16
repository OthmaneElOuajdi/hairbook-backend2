package com.hairbook.hairbook_backend.service;

import com.hairbook.hairbook_backend.dto.notification.NotificationDto;
import com.hairbook.hairbook_backend.entity.Notification;
import com.hairbook.hairbook_backend.entity.User;
import com.hairbook.hairbook_backend.exception.ResourceNotFoundException;
import com.hairbook.hairbook_backend.repository.NotificationRepository;
import com.hairbook.hairbook_backend.repository.UserRepository;
import com.hairbook.hairbook_backend.util.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsable de la gestion des notifications utilisateurs.
 * <p>
 * Permet la création, l'envoi, la récupération, la lecture, la suppression,
 * et la conversion des notifications (système, email, SMS).
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;

    /**
     * Crée une notification système pour un utilisateur.
     *
     * @param userId    identifiant de l'utilisateur concerné
     * @param title     titre de la notification
     * @param message   contenu du message
     * @param actionUrl lien d'action optionnel
     * @return DTO de la notification créée
     */
    @Transactional
    public NotificationDto createSystemNotification(Long userId, String title, String message, String actionUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", userId));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType("SYSTEM");
        notification.setActionUrl(actionUrl);
        notification.setRead(false);

        notification = notificationRepository.save(notification);
        return convertToDto(notification);
    }

    /**
     * Envoie un email et crée une notification EMAIL en base.
     * Méthode asynchrone.
     *
     * @param userId    identifiant de l'utilisateur
     * @param title     sujet de l'e-mail
     * @param message   contenu de l'e-mail
     * @param actionUrl lien associé à la notification
     */
    @Async
    @Transactional
    public void sendEmailNotification(Long userId, String title, String message, String actionUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", userId));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType("EMAIL");
        notification.setActionUrl(actionUrl);
        notification.setRead(false);

        notificationRepository.save(notification);

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject(title);
            mailMessage.setText(message);
            emailSender.send(mailMessage);
            LoggingService.logInfo(this.getClass(), "Email envoyé à {}: {}", user.getEmail(), title);
        } catch (Exception e) {
            LoggingService.logError(this.getClass(), "Erreur lors de l'envoi d'email à " + user.getEmail(), e);
        }
    }

    /**
     * Envoie un SMS (simulé) et crée une notification SMS.
     * Méthode asynchrone.
     *
     * @param userId  identifiant de l'utilisateur
     * @param title   titre de la notification
     * @param message contenu du SMS
     */
    @Async
    @Transactional
    public void sendSmsNotification(Long userId, String title, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", userId));

        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            LoggingService.logWarn(this.getClass(),
                    "Impossible d'envoyer un SMS à l'utilisateur {} : numéro de téléphone manquant", userId);
            return;
        }

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType("SMS");
        notification.setRead(false);

        notificationRepository.save(notification);

        LoggingService.logInfo(this.getClass(), "SMS envoyé à {}: {}", user.getPhoneNumber(), message);
    }

    /**
     * Récupère toutes les notifications d'un utilisateur, triées par date décroissante.
     *
     * @param userId identifiant de l'utilisateur
     * @return liste des notifications sous forme de DTO
     */
    public List<NotificationDto> getUserNotifications(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return notifications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les notifications paginées d’un utilisateur.
     *
     * @param userId   identifiant de l'utilisateur
     * @param pageable pagination
     * @return page de notifications sous forme de DTO
     */
    public Page<NotificationDto> getUserNotificationsPaginated(Long userId, Pageable pageable) {
        Page<Notification> notificationsPage =
                notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        return notificationsPage.map(this::convertToDto);
    }

    /**
     * Marque une notification comme lue.
     *
     * @param notificationId identifiant de la notification
     * @return notification mise à jour sous forme de DTO
     */
    @Transactional
    public NotificationDto markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", notificationId));

        notification.setRead(true);
        notification = notificationRepository.save(notification);
        return convertToDto(notification);
    }

    /**
     * Marque toutes les notifications d’un utilisateur comme lues.
     *
     * @param userId identifiant de l'utilisateur
     */
    @Transactional
    public void markAllAsRead(Long userId) {
        List<Notification> unreadNotifications =
                notificationRepository.findByUserIdAndReadOrderByCreatedAtDesc(userId, false);

        unreadNotifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(unreadNotifications);
    }

    /**
     * Compte le nombre de notifications non lues pour un utilisateur.
     *
     * @param userId identifiant de l'utilisateur
     * @return nombre de notifications non lues
     */
    public long countUnreadNotifications(Long userId) {
        return notificationRepository.countByUserIdAndRead(userId, false);
    }

    /**
     * Supprime toutes les notifications créées avant une date donnée.
     *
     * @param olderThan date limite de conservation
     */
    @Transactional
    public void deleteOldNotifications(LocalDateTime olderThan) {
        List<Notification> oldNotifications = notificationRepository.findByCreatedAtBefore(olderThan);
        notificationRepository.deleteAll(oldNotifications);
        LoggingService.logInfo(this.getClass(), "{} anciennes notifications supprimées", oldNotifications.size());
    }

    /**
     * Convertit une entité {@link Notification} en objet {@link NotificationDto}.
     *
     * @param notification entité Notification
     * @return DTO correspondant
     */
    private NotificationDto convertToDto(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setUserId(notification.getUser().getId());
        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setType(notification.getType());
        dto.setRead(notification.isRead());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setActionUrl(notification.getActionUrl());
        return dto;
    }
}
