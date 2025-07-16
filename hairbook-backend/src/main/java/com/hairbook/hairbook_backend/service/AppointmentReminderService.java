package com.hairbook.hairbook_backend.service;

import com.hairbook.hairbook_backend.entity.Appointment;
import com.hairbook.hairbook_backend.entity.AppointmentStatus;
import com.hairbook.hairbook_backend.entity.User;
import com.hairbook.hairbook_backend.repository.AppointmentRepository;
import com.hairbook.hairbook_backend.util.LoggingService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Service chargé de l’envoi automatique de rappels pour les rendez-vous.
 * <p>
 * Deux types de rappels sont gérés :
 * <ul>
 *     <li>Rappels quotidiens envoyés la veille du rendez-vous à 10h00</li>
 *     <li>Rappels horaires envoyés 2 heures avant le rendez-vous</li>
 * </ul>
 * Les rappels sont envoyés par email, notification interne et SMS si possible.
 */
@Service
@EnableScheduling
public class AppointmentReminderService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentReminderService.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'à' HH:mm");

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationService notificationService;

    /**
     * Envoie un rappel quotidien pour tous les rendez-vous confirmés du lendemain.
     * <p>
     * Ce rappel est envoyé une fois par jour à 10h00. Il inclut :
     * <ul>
     *     <li>Un email de rappel</li>
     *     <li>Une notification système</li>
     *     <li>Un email texte secondaire</li>
     *     <li>Un SMS (si le numéro est disponible)</li>
     * </ul>
     */
    @Scheduled(cron = "0 0 10 * * ?")
    public void sendDailyReminders() {
        logger.info("Début de l'envoi des rappels quotidiens");

        LocalDateTime startOfTomorrow = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfTomorrow = startOfTomorrow.plusDays(1);

        List<Appointment> appointments = appointmentRepository.findByStatusAndStartTimeBetween(
                AppointmentStatus.CONFIRMED, startOfTomorrow, endOfTomorrow);

        logger.info("Nombre de rappels à envoyer : {}", appointments.size());

        for (Appointment appointment : appointments) {
            try {
                User client = appointment.getUser();
                String formattedDateTime = appointment.getStartTime().format(formatter);

                // Email HTML
                emailService.sendAppointmentReminder(
                        client.getEmail(),
                        appointment.getId(),
                        client.getFirstName() + " " + client.getLastName(),
                        appointment.getService().getName(),
                        formattedDateTime
                );

                // Notification système
                String title = "Rappel de rendez-vous pour demain";
                String message = "Vous avez rendez-vous demain à " + formattedDateTime + " pour " + appointment.getService().getName();
                String actionUrl = "/appointments/" + appointment.getId();
                notificationService.createSystemNotification(client.getId(), title, message, actionUrl);

                // Email texte
                notificationService.sendEmailNotification(
                        client.getId(),
                        "Rappel : Votre rendez-vous de demain",
                        "Bonjour " + client.getFirstName() + ",\n\n"
                                + "Nous vous rappelons que vous avez rendez-vous demain à " + formattedDateTime + " pour " + appointment.getService().getName() + ".\n"
                                + "Nous vous attendons à l'heure indiquée.\n\n"
                                + "À bientôt,\n"
                                + "L'équipe Hairbook",
                        actionUrl
                );

                // SMS
                if (client.getPhoneNumber() != null && !client.getPhoneNumber().isEmpty()) {
                    notificationService.sendSmsNotification(
                            client.getId(),
                            "Rappel rendez-vous",
                            "Rappel: Vous avez RDV demain à " + formattedDateTime + " pour " + appointment.getService().getName() + ". Hairbook."
                    );
                }

                LoggingService.logInfo(this.getClass(), "Rappel envoyé pour le rendez-vous ID: {}", appointment.getId());

            } catch (MessagingException e) {
                LoggingService.logError(this.getClass(), "Erreur lors de l'envoi du rappel pour le rendez-vous ID: " + appointment.getId(), e);
            }
        }

        logger.info("Fin de l'envoi des rappels quotidiens");
    }

    /**
     * Envoie un rappel pour les rendez-vous confirmés ayant lieu dans environ 2 heures.
     * <p>
     * Cette méthode s’exécute automatiquement toutes les heures, à l’heure pile (minute 0),
     * et envoie les mêmes types de rappels que {@link #sendDailyReminders()}.
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void sendHourlyReminders() {
        logger.info("Début de l'envoi des rappels horaires");

        LocalDateTime twoHoursFromNow = LocalDateTime.now().plusHours(2);
        LocalDateTime threeHoursFromNow = LocalDateTime.now().plusHours(3);

        List<Appointment> appointments = appointmentRepository.findByStatusAndStartTimeBetween(
                AppointmentStatus.CONFIRMED, twoHoursFromNow, threeHoursFromNow);

        logger.info("Nombre de rappels horaires à envoyer : {}", appointments.size());

        for (Appointment appointment : appointments) {
            try {
                User client = appointment.getUser();
                String formattedDateTime = appointment.getStartTime().format(formatter);

                // Email HTML
                emailService.sendAppointmentReminder(
                        client.getEmail(),
                        appointment.getId(),
                        client.getFirstName() + " " + client.getLastName(),
                        appointment.getService().getName(),
                        formattedDateTime
                );

                // Notification système
                String title = "Rappel de rendez-vous imminent";
                String message = "Vous avez rendez-vous dans 2 heures à " + formattedDateTime + " pour " + appointment.getService().getName();
                String actionUrl = "/appointments/" + appointment.getId();
                notificationService.createSystemNotification(client.getId(), title, message, actionUrl);

                // Email texte
                notificationService.sendEmailNotification(
                        client.getId(),
                        "Rappel : Votre rendez-vous dans 2 heures",
                        "Bonjour " + client.getFirstName() + ",\n\n"
                                + "Nous vous rappelons que vous avez rendez-vous dans 2 heures à " + formattedDateTime + " pour " + appointment.getService().getName() + ".\n"
                                + "Nous vous attendons à l'heure indiquée.\n\n"
                                + "À bientôt,\n"
                                + "L'équipe Hairbook",
                        actionUrl
                );

                // SMS
                if (client.getPhoneNumber() != null && !client.getPhoneNumber().isEmpty()) {
                    notificationService.sendSmsNotification(
                            client.getId(),
                            "Rappel rendez-vous",
                            "Rappel: Vous avez RDV dans 2h à " + formattedDateTime + " pour " + appointment.getService().getName() + ". Hairbook."
                    );
                }

                LoggingService.logInfo(this.getClass(), "Rappel horaire envoyé pour le rendez-vous ID: {}", appointment.getId());

            } catch (MessagingException e) {
                LoggingService.logError(this.getClass(), "Erreur lors de l'envoi du rappel horaire pour le rendez-vous ID: " + appointment.getId(), e);
            }
        }

        logger.info("Fin de l'envoi des rappels horaires");
    }
}
