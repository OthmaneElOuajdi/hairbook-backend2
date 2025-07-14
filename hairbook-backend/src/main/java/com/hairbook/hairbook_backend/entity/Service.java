package com.hairbook.hairbook_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Représente un service proposé par le salon dans l'application Hairbook.
 *
 * <p>Chaque service possède un nom unique, une description, un prix, une durée, une image associée (optionnelle)
 * et un indicateur d'activité.</p>
 *
 * <p>Cette entité est mappée à la table <strong>services</strong> en base de données.</p>
 */
@Entity
@Table(name = "services")
public class Service {

    /**
     * Identifiant unique du service (clé primaire auto-générée).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom du service (obligatoire et unique).
     */
    @NotBlank
    @Column(unique = true)
    private String name;

    /**
     * Description détaillée du service (optionnelle).
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Prix du service (obligatoire, valeur strictement positive).
     */
    @NotNull
    @Positive
    private BigDecimal price;

    /**
     * Durée estimée du service, en minutes (obligatoire, strictement positive).
     */
    @NotNull
    @Positive
    private Integer durationMinutes;

    /**
     * URL vers une image représentant le service (optionnelle).
     */
    private String imageUrl;

    /**
     * Indique si le service est actuellement actif et disponible à la réservation.
     */
    @NotNull
    private Boolean active = true;

    // ----- Getters & Setters -----

    /** @return l'identifiant du service */
    public Long getId() {
        return id;
    }

    /** @param id identifiant à définir pour le service */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return le nom du service */
    public String getName() {
        return name;
    }

    /** @param name nom du service à définir */
    public void setName(String name) {
        this.name = name;
    }

    /** @return la description du service */
    public String getDescription() {
        return description;
    }

    /** @param description texte descriptif à définir */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return le prix du service */
    public BigDecimal getPrice() {
        return price;
    }

    /** @param price montant du service à définir */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /** @return la durée du service en minutes */
    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    /** @param durationMinutes durée estimée à définir */
    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    /** @return l'URL de l'image du service */
    public String getImageUrl() {
        return imageUrl;
    }

    /** @param imageUrl URL de l'image à définir */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /** @return true si le service est actif, sinon false */
    public Boolean getActive() {
        return active;
    }

    /** @param active indique si le service est actif */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Méthode utilitaire pour vérifier si le service est actif.
     *
     * @return true si le champ {@code active} est non nul et à {@code true}, sinon false
     */
    public boolean isActive() {
        return active != null && active;
    }

    // ----- Constructeurs -----

    /**
     * Constructeur sans argument requis par JPA.
     */
    public Service() {}

    /**
     * Constructeur complet pour initialiser un service avec toutes ses propriétés.
     *
     * @param id identifiant du service
     * @param name nom du service
     * @param description description du service
     * @param price prix du service
     * @param durationMinutes durée du service en minutes
     * @param imageUrl URL de l'image du service
     * @param active état actif ou non du service
     */
    public Service(Long id, String name, String description, BigDecimal price, Integer durationMinutes, String imageUrl, Boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationMinutes = durationMinutes;
        this.imageUrl = imageUrl;
        this.active = active;
    }
}
