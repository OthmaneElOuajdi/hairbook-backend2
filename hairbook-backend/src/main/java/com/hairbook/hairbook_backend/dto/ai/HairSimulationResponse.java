package com.hairbook.hairbook_backend.dto.ai;

/**
 * Réponse d'une simulation de coiffure générée par l'IA.
 * Contient l'image résultante encodée en base64, un identifiant de traitement, un indicateur de succès et un message.
 */
public class HairSimulationResponse {

    /**
     * Image de la coiffure simulée encodée en base64.
     */
    private String resultImageBase64;

    /**
     * Identifiant unique du traitement de la simulation.
     */
    private String processId;

    /**
     * Indique si la simulation a réussi.
     */
    private boolean success;

    /**
     * Message d'information ou d'erreur relatif à la simulation.
     */
    private String message;

    /**
     * Constructeur par défaut.
     */
    public HairSimulationResponse() {
    }

    /**
     * Constructeur pour les cas où seule la réussite et le message sont connus.
     *
     * @param success si la simulation a réussi
     * @param message message informatif ou d'erreur
     */
    public HairSimulationResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Constructeur principal avec tous les champs.
     *
     * @param resultImageBase64 image résultante en base64
     * @param processId         identifiant de traitement
     * @param success           indicateur de succès
     * @param message           message d'information ou d'erreur
     */
    public HairSimulationResponse(String resultImageBase64, String processId, boolean success, String message) {
        this.resultImageBase64 = resultImageBase64;
        this.processId = processId;
        this.success = success;
        this.message = message;
    }

    public String getResultImageBase64() {
        return resultImageBase64;
    }

    public void setResultImageBase64(String resultImageBase64) {
        this.resultImageBase64 = resultImageBase64;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
