package com.hairbook.hairbook_backend.exception;

/**
 * Classe abstraite représentant une sous-erreur d'API.
 * Peut être étendue pour modéliser différentes catégories d'erreurs.
 */
abstract class ApiSubError {
}

/**
 * Représente une erreur de validation spécifique à un champ ou à un objet
 * dans une requête, utilisée pour les réponses d'erreur structurées.
 */
class ApiValidationError extends ApiSubError {

    /** Nom de l'objet (par exemple : nom d'une classe DTO) */
    private String object;

    /** Nom du champ ayant échoué à la validation (peut être null pour une erreur d'objet global) */
    private String field;

    /** Valeur rejetée pour le champ, si disponible */
    private Object rejectedValue;

    /** Message décrivant l'erreur */
    private String message;

    /**
     * Constructeur pour une erreur de validation sur un champ spécifique.
     *
     * @param object         nom de l'objet
     * @param field          champ concerné
     * @param rejectedValue  valeur rejetée
     * @param message        message d'erreur
     */
    public ApiValidationError(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    /**
     * Constructeur pour une erreur de validation globale sur un objet.
     *
     * @param object  nom de l'objet
     * @param message message d'erreur
     */
    public ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    /** @return le nom de l'objet concerné */
    public String getObject() {
        return object;
    }

    /** @param object définit le nom de l'objet */
    public void setObject(String object) {
        this.object = object;
    }

    /** @return le champ concerné par l'erreur */
    public String getField() {
        return field;
    }

    /** @param field définit le champ concerné */
    public void setField(String field) {
        this.field = field;
    }

    /** @return la valeur rejetée */
    public Object getRejectedValue() {
        return rejectedValue;
    }

    /** @param rejectedValue définit la valeur rejetée */
    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    /** @return le message d'erreur */
    public String getMessage() {
        return message;
    }

    /** @param message définit le message d'erreur */
    public void setMessage(String message) {
        this.message = message;
    }

    /** Compare deux erreurs pour l'égalité logique */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApiValidationError)) return false;

        ApiValidationError that = (ApiValidationError) o;

        if (object != null ? !object.equals(that.object) : that.object != null) return false;
        if (field != null ? !field.equals(that.field) : that.field != null) return false;
        if (rejectedValue != null ? !rejectedValue.equals(that.rejectedValue) : that.rejectedValue != null)
            return false;
        return message != null ? message.equals(that.message) : that.message == null;
    }

    /** Calcule le hashcode pour cette erreur */
    @Override
    public int hashCode() {
        int result = object != null ? object.hashCode() : 0;
        result = 31 * result + (field != null ? field.hashCode() : 0);
        result = 31 * result + (rejectedValue != null ? rejectedValue.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
