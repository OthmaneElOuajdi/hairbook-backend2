package com.hairbook.hairbook_backend.exception;

import com.hairbook.hairbook_backend.util.LoggingService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire global des exceptions pour l'application Hairbook.
 * Centralise la capture des erreurs fréquentes afin de retourner des réponses JSON cohérentes.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Gère les erreurs de validation sur les arguments des méthodes contrôleurs.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Erreur de validation");

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        errors.forEach((field, message) -> {
            apiError.addSubError(new ApiValidationError(ex.getBindingResult().getObjectName(), field, null, message));
        });

        return buildResponseEntity(apiError);
    }

    /**
     * Gère l'exception lorsqu'une entité n'est pas trouvée en base.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Gère les accès refusés (rôle ou autorisation insuffisants).
     */
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDenied(org.springframework.security.access.AccessDeniedException ex) {
        LoggingService.logError(this.getClass(), "Accès refusé: {0}", ex);
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
        apiError.setMessage("Accès refusé: vous n'avez pas les permissions nécessaires");
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Gère les identifiants incorrects lors de l'authentification.
     */
    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
        apiError.setMessage("Identifiants incorrects");
        return buildResponseEntity(apiError);
    }

    /**
     * Gère les violations d'intégrité en base de données (ex: duplication).
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT);
        apiError.setMessage("Violation de contrainte de base de données");
        apiError.setDebugMessage(ex.getMostSpecificCause().getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Gère les erreurs de validation au niveau des contraintes (annotations @Valid).
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Erreur de validation");
        ex.getConstraintViolations().forEach(violation -> {
            apiError.addSubError(
                new ApiValidationError(
                    violation.getRootBeanClass().getSimpleName(),
                    violation.getPropertyPath().toString(),
                    violation.getInvalidValue(),
                    violation.getMessage()
                )
            );
        });
        return buildResponseEntity(apiError);
    }

    /**
     * Gère les ressources personnalisées non trouvées (ex: ID inexistant).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Gère les erreurs dues à des paramètres manquants dans la requête HTTP.
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        LoggingService.logError(this.getClass(), "Paramètre manquant: {0}", ex);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Le paramètre '" + ex.getParameterName() + "' est manquant");
        return buildResponseEntity(apiError);
    }

    /**
     * Gère les erreurs de parsing JSON (format incorrect).
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        LoggingService.logError(this.getClass(), "Format JSON invalide: {0}", ex);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Format JSON invalide");
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Gère les erreurs de type de paramètre dans les requêtes (ex: Long attendu mais String fourni).
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        LoggingService.logError(this.getClass(), "Type de paramètre incorrect: {0}", ex);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Le paramètre '" + ex.getName() + "' devrait être de type '" +
                          ex.getRequiredType().getSimpleName() + "'");
        return buildResponseEntity(apiError);
    }

    /**
     * Gère les erreurs 404 quand aucun contrôleur ne correspond à la requête.
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        LoggingService.logError(this.getClass(), "Ressource non trouvée: {0}", ex);
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage("Ressource non trouvée: " + ex.getRequestURL());
        return buildResponseEntity(apiError);
    }

    /**
     * Gère les erreurs d'accès aux fichiers (droit refusé).
     */
    @ExceptionHandler(java.nio.file.AccessDeniedException.class)
    protected ResponseEntity<Object> handleFileAccessDenied(java.nio.file.AccessDeniedException ex) {
        LoggingService.logError(this.getClass(), "Accès au fichier refusé: {0}", ex);
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
        apiError.setMessage("Accès au fichier refusé");
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Gère toutes les autres exceptions non spécifiées.
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex) {
        LoggingService.logException(this.getClass(), "Erreur inattendue", ex);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage("Une erreur inattendue s'est produite");
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Construit une réponse HTTP contenant l'objet ApiError.
     *
     * @param apiError l'objet représentant l'erreur
     * @return la réponse HTTP construite
     */
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
