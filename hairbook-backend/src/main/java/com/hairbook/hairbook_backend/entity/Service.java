package com.hairbook.hairbook_backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "Représente un service proposé par le salon dans l'application Hairbook")
@Entity
@Table(name = "services")
public class Service {

    @Schema(description = "Identifiant unique du service", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nom du service unique", example = "Coupe et brushing", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Column(unique = true)
    private String name;

    @Schema(description = "Description détaillée du service", example = "Coupe personnalisée selon vos envies avec brushing inclus", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Column(columnDefinition = "TEXT")
    private String description;

    @Schema(description = "Prix du service en euros", example = "45.50", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Positive
    private BigDecimal price;

    @Schema(description = "Durée estimée du service en minutes", example = "60", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Positive
    private Integer durationMinutes;

    @Schema(description = "URL vers une image représentant le service", example = "https://example.com/images/coupe-brushing.jpg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String imageUrl;

    @Schema(description = "Indique si le service est actuellement actif et disponible à la réservation", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Boolean active = true;

    // ----- Constructeurs -----

    public Service() {}

    public Service(Long id, String name, String description, BigDecimal price, Integer durationMinutes, String imageUrl, Boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationMinutes = durationMinutes;
        this.imageUrl = imageUrl;
        this.active = active;
    }
    
    // ----- Getters & Setters -----

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active != null && active;
    }
}
