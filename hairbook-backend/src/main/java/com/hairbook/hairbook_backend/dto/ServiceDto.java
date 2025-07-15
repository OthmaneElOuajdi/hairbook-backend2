package com.hairbook.hairbook_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * DTO représentant un service proposé par le salon.
 * Utilisé pour transférer des données entre le client et le serveur.
 */
public class ServiceDto {

    /**
     * Identifiant unique du service.
     */
    private Long id;

    /**
     * Nom du service.
     * Obligatoire, entre 2 et 100 caractères.
     */
    @NotBlank(message = "Le nom du service est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom du service doit contenir entre 2 et 100 caractères")
    private String name;

    /**
     * Description optionnelle du service.
     * Maximum 500 caractères.
     */
    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;

    /**
     * Prix du service.
     * Doit être supérieur ou égal à 0.
     */
    @NotNull(message = "Le prix est obligatoire")
    @Min(value = 0, message = "Le prix doit être supérieur ou égal à 0")
    private BigDecimal price;

    /**
     * Durée du service en minutes.
     * Doit être au minimum de 5 minutes.
     */
    @NotNull(message = "La durée est obligatoire")
    @Min(value = 5, message = "La durée minimale est de 5 minutes")
    private Integer durationMinutes;

    /**
     * URL de l'image associée au service (optionnelle).
     */
    private String imageUrl;

    /**
     * Indique si le service est actif.
     * Par défaut, true.
     */
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
