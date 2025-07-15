package com.hairbook.hairbook_backend.dto.ai;

import jakarta.validation.constraints.NotBlank;

/**
 * Requête pour simuler une coiffure à l'aide d'une IA.
 * Contient les paramètres nécessaires à la génération d'une image avec une coiffure simulée.
 */
public class HairSimulationRequest {

    /**
     * Image de l'utilisateur encodée en base64.
     */
    @NotBlank(message = "L'image est obligatoire")
    private String imageBase64;

    /**
     * Nom ou identifiant du style de coiffure souhaité.
     */
    @NotBlank(message = "Le style de coiffure est obligatoire")
    private String hairStyle;

    /**
     * Couleur de cheveux souhaitée (optionnelle).
     */
    private String hairColor;

    /**
     * Instructions additionnelles pour affiner la simulation (optionnel).
     */
    private String additionalInstructions;

    /**
     * Constructeur par défaut.
     */
    public HairSimulationRequest() {
    }

    /**
     * Constructeur avec tous les champs.
     *
     * @param imageBase64           image en base64
     * @param hairStyle             style de coiffure demandé
     * @param hairColor             couleur de cheveux souhaitée
     * @param additionalInstructions instructions supplémentaires
     */
    public HairSimulationRequest(String imageBase64, String hairStyle, String hairColor, String additionalInstructions) {
        this.imageBase64 = imageBase64;
        this.hairStyle = hairStyle;
        this.hairColor = hairColor;
        this.additionalInstructions = additionalInstructions;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getHairStyle() {
        return hairStyle;
    }

    public void setHairStyle(String hairStyle) {
        this.hairStyle = hairStyle;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getAdditionalInstructions() {
        return additionalInstructions;
    }

    public void setAdditionalInstructions(String additionalInstructions) {
        this.additionalInstructions = additionalInstructions;
    }
}
