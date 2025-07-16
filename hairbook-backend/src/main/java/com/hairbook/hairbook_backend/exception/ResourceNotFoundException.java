package com.hairbook.hairbook_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception personnalisée utilisée lorsqu'une ressource demandée est introuvable.
 * Cette exception renvoie une réponse HTTP 404 (Not Found).
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit une exception avec un message personnalisé.
     *
     * @param message le message d'erreur
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Construit une exception avec des informations détaillées sur la ressource manquante.
     *
     * @param resourceName le nom de la ressource (ex: "Utilisateur")
     * @param fieldName    le nom du champ utilisé pour la recherche (ex: "id")
     * @param fieldValue   la valeur du champ (ex: 42)
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s non trouvé avec %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
