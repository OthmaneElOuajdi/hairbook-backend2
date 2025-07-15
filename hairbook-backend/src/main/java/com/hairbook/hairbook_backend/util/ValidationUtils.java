package com.hairbook.hairbook_backend.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Classe utilitaire centralisant différentes méthodes de validation des données
 * au sein de l'application (objets, emails, numéros, chaînes, dates, etc.).
 * <p>
 * Repose sur Jakarta Bean Validation (JSR-380) pour valider les objets via annotations.
 */
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

    /**
     * Valide un objet en utilisant les annotations de validation (ex: @NotNull, @Email).
     *
     * @param object l'objet à valider
     * @param <T>    le type de l'objet
     * @return une map contenant les erreurs de validation, où la clé est le champ fautif
     *         et la valeur est le message d'erreur associé. Vide s'il n'y a pas d'erreurs.
     */
    public static <T> Map<String, String> validate(T object) {
        Map<String, String> errors = new HashMap<>();
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        for (ConstraintViolation<T> violation : violations) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return errors;
    }

    /**
     * Vérifie si une adresse e-mail est syntaxiquement valide.
     *
     * @param email l'adresse e-mail à tester
     * @return {@code true} si elle est valide, {@code false} sinon
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Vérifie si un numéro de téléphone est valide (optionnellement avec indicatif).
     *
     * @param phoneNumber le numéro de téléphone à tester
     * @return {@code true} s'il est valide, {@code false} sinon
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    /**
     * Vérifie si une chaîne est vide ou nulle.
     *
     * @param str la chaîne à tester
     * @return {@code true} si elle est vide ou nulle, {@code false} sinon
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Vérifie si une date est dans le futur par rapport au moment actuel.
     *
     * @param date la date à tester
     * @return {@code true} si la date est future, {@code false} sinon
     */
    public static boolean isFutureDate(java.time.LocalDateTime date) {
        return date != null && date.isAfter(java.time.LocalDateTime.now());
    }

    /**
     * Vérifie si un identifiant est valide (positif et non nul).
     *
     * @param id l'identifiant à tester
     * @return {@code true} si l'ID est valide, {@code false} sinon
     */
    public static boolean isValidId(Long id) {
        return id != null && id > 0;
    }

    /**
     * Vérifie si un montant est valide (positif ou nul).
     *
     * @param amount le montant à tester
     * @return {@code true} si le montant est valide, {@code false} sinon
     */
    public static boolean isValidAmount(Double amount) {
        return amount != null && amount >= 0;
    }

    /**
     * Vérifie si une durée en minutes est valide (strictement positive).
     *
     * @param durationMinutes la durée en minutes à tester
     * @return {@code true} si la durée est valide, {@code false} sinon
     */
    public static boolean isValidDuration(Integer durationMinutes) {
        return durationMinutes != null && durationMinutes > 0;
    }
}
