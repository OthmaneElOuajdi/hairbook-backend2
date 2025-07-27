package com.hairbook.hairbook_backend.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Classe abstraite représentant une sous-erreur d'API - Peut être étendue pour modéliser différentes catégories d'erreurs")
abstract class ApiSubError {
}

@Schema(description = "Représente une erreur de validation spécifique à un champ ou à un objet dans une requête - Utilisée pour les réponses d'erreur structurées")
class ApiValidationError extends ApiSubError {

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ApiValidationError(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    @Override
    public int hashCode() {
        int result = object != null ? object.hashCode() : 0;
        result = 31 * result + (field != null ? field.hashCode() : 0);
        result = 31 * result + (rejectedValue != null ? rejectedValue.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
