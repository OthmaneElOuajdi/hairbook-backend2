package com.hairbook.hairbook_backend.util;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@Schema(description = "Classe utilitaire centralisant différentes méthodes de validation des données au sein de l'application - Repose sur Jakarta Bean Validation")
@Component
public class ValidationUtils {

    private static final Validator validator;

    // Expression régulière pour valider les adresses e-mail
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    // Expression régulière pour valider les numéros de téléphone
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^(\\+\\d{1,3}[- ]?)?\\d{9,15}$"
    );

    // Initialisation du validateur unique à l'application
    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Schema(description = "Valide un objet en utilisant les annotations de validation - Retourne une map des erreurs de validation")
    public static <T> Map<String, String> validate(T object) {
        Map<String, String> errors = new HashMap<>();
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        for (ConstraintViolation<T> violation : violations) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return errors;
    }

    @Schema(description = "Vérifie si une adresse e-mail est syntaxiquement valide")
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    @Schema(description = "Vérifie si un numéro de téléphone est valide (optionnellement avec indicatif)")
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    @Schema(description = "Vérifie si une chaîne est vide ou nulle")
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    @Schema(description = "Vérifie si une date est dans le futur par rapport au moment actuel")
    public static boolean isFutureDate(java.time.LocalDateTime date) {
        return date != null && date.isAfter(java.time.LocalDateTime.now());
    }

    @Schema(description = "Vérifie si un identifiant est valide (positif et non nul)")
    public static boolean isValidId(Long id) {
        return id != null && id > 0;
    }

    @Schema(description = "Vérifie si un montant est valide (positif ou nul)")
    public static boolean isValidAmount(Double amount) {
        return amount != null && amount >= 0;
    }

    @Schema(description = "Vérifie si une durée en minutes est valide (strictement positive)")
    public static boolean isValidDuration(Integer durationMinutes) {
        return durationMinutes != null && durationMinutes > 0;
    }
}
