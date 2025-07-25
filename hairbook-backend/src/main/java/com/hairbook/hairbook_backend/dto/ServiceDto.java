package com.hairbook.hairbook_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "DTO représentant un service proposé par le salon")
public class ServiceDto {

    @Schema(description = "Identifiant unique du service", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nom du service", example = "Coupe et brushing", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 100, minLength = 2)
    @NotBlank(message = "Le nom du service est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom du service doit contenir entre 2 et 100 caractères")
    private String name;

    @Schema(description = "Description optionnelle du service", example = "Coupe personnalisée avec brushing professionnel", maxLength = 500)
    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;

    @Schema(description = "Prix du service en euros", example = "45.50", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0")
    @NotNull(message = "Le prix est obligatoire")
    @Min(value = 0, message = "Le prix doit être supérieur ou égal à 0")
    private BigDecimal price;

    @Schema(description = "Durée du service en minutes", example = "90", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "5")
    @NotNull(message = "La durée est obligatoire")
    @Min(value = 5, message = "La durée minimale est de 5 minutes")
    private Integer durationMinutes;

    @Schema(description = "URL de l'image associée au service", example = "https://example.com/images/coupe-brushing.jpg")
    private String imageUrl;

    @Schema(description = "Indique si le service est actif", example = "true", defaultValue = "true")
    private boolean active = true;

    // --- Constructeurs manuels ---

    public ServiceDto() {
        // Constructeur par défaut
    }

    public ServiceDto(Long id, String name, String description, BigDecimal price,
                      Integer durationMinutes, String imageUrl, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationMinutes = durationMinutes;
        this.imageUrl = imageUrl;
        this.active = active;
    }

    // --- Getters et Setters ---

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }
    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}
