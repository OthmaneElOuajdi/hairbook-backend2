package com.hairbook.hairbook_backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

/**
 * Service pour l'envoi d'emails dans l'application Hairbook
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * Envoie un email simple sans formatage HTML
     *
     * @param to      adresse email du destinataire
     * @param subject sujet de l'email
     * @param text    contenu textuel de l'email
     */
    @Async
    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hairbook.salon@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    /**
     * Envoie un email HTML en utilisant un template Thymeleaf
     *
     * @param to           adresse email du destinataire
     * @param subject      sujet de l'email
     * @param templateName nom du template Thymeleaf (sans extension)
     * @param variables    variables à injecter dans le template
     * @throws MessagingException en cas d'erreur lors de l'envoi
     */
    @Async
    public void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> variables) throws MessagingException {
        Context context = new Context();
        context.setVariables(variables);
        String htmlContent = templateEngine.process(templateName, context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("hairbook.salon@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    /**
     * Envoie un email de confirmation de rendez-vous
     */
    @Async
    public void sendAppointmentConfirmation(String to, Long appointmentId, String clientName,
                                            String serviceName, String dateTime, String duration) throws MessagingException {
        Map<String, Object> variables = Map.of(
                "clientName", clientName,
                "appointmentId", appointmentId,
                "serviceName", serviceName,
                "dateTime", dateTime,
                "duration", duration,
                "cancelUrl", "http://localhost:3000/appointments/" + appointmentId + "/cancel"
        );

        sendHtmlEmail(to, "Confirmation de votre rendez-vous chez Hairbook", "appointment-confirmation", variables);
    }

    /**
     * Envoie un email de rappel de rendez-vous
     */
    @Async
    public void sendAppointmentReminder(String to, Long appointmentId, String clientName,
                                        String serviceName, String dateTime) throws MessagingException {
        Map<String, Object> variables = Map.of(
                "clientName", clientName,
                "appointmentId", appointmentId,
                "serviceName", serviceName,
                "dateTime", dateTime,
                "rescheduleUrl", "http://localhost:3000/appointments/" + appointmentId + "/reschedule"
        );

        sendHtmlEmail(to, "Rappel de votre rendez-vous chez Hairbook", "appointment-reminder", variables);
    }

    /**
     * Envoie un email de bienvenue après l'inscription
     */
    @Async
    public void sendWelcomeEmail(String to, String clientName) throws MessagingException {
        Map<String, Object> variables = Map.of(
                "clientName", clientName,
                "loginUrl", "http://localhost:3000/login"
        );

        sendHtmlEmail(to, "Bienvenue chez Hairbook !", "welcome-email", variables);
    }

    /**
     * Envoie un email de réinitialisation de mot de passe
     */
    @Async
    public void sendPasswordResetEmail(String to, String clientName, String resetToken) throws MessagingException {
        Map<String, Object> variables = Map.of(
                "clientName", clientName,
                "resetUrl", "http://localhost:3000/reset-password?token=" + resetToken,
                "expiryTime", "24 heures"
        );

        sendHtmlEmail(to, "Réinitialisation de votre mot de passe Hairbook", "password-reset", variables);
    }
}
