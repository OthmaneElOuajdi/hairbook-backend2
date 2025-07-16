package com.hairbook.hairbook_backend.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente une erreur personnalisée retournée par l'API Hairbook.
 * Cette classe permet de structurer les erreurs HTTP de manière uniforme,
 * en incluant des informations supplémentaires comme le timestamp, le message
 * d'erreur, les détails techniques et des sous-erreurs.
 */
public class ApiError {

    /** Le statut HTTP de l'erreur (ex: 404, 500). */
    private HttpStatus status;

    /** La date et l'heure où l'erreur s'est produite. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;

    /** Message lisible par l'utilisateur décrivant l'erreur. */
    private String message;

    /** Message technique utile pour le débogage. */
    private String debugMessage;

    /** Liste de sous-erreurs, typiquement pour les erreurs de validation. */
    private List<ApiSubError> subErrors = new ArrayList<>();

    /**
     * Constructeur privé par défaut, initialise le timestamp.
     */
    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    /**
     * Constructeur principal avec statut HTTP.
     * @param status le code HTTP associé à l'erreur
     */
    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    /**
     * Constructeur avec statut et message d'erreur.
     * @param status le code HTTP
     * @param message message lisible par l'utilisateur
     */
    public ApiError(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    /**
     * Constructeur avec statut, message et exception technique.
     * @param status le code HTTP
     * @param message message lisible
     * @param ex exception levée (utilisée pour le debug)
     */
    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    /**
     * Ajoute une sous-erreur à la liste.
     * @param subError l'erreur détaillée à ajouter
     */
    public void addSubError(ApiSubError subError) {
        this.subErrors.add(subError);
    }

    // Getters et setters

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public List<ApiSubError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<ApiSubError> subErrors) {
        this.subErrors = subErrors;
    }
}
