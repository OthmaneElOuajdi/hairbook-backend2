package com.hairbook.hairbook_backend.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Contributeur personnalisé pour l'endpoint Actuator {@code /actuator/info}.
 * <p>
 * Ce composant permet d'ajouter des métadonnées personnalisées concernant l'application
 * Hairbook dans l'API Actuator. Ces données peuvent inclure des informations de build,
 * des fonctionnalités supportées, et les moyens de contact.
 * </p>
 *
 * <p>Les informations retournées sont structurées sous les clés suivantes :</p>
 * <ul>
 *   <li>{@code application} : nom, description, version</li>
 *   <li>{@code features} : les fonctionnalités supportées (authentification, paiement, etc.)</li>
 *   <li>{@code contact} : équipe de développement, email et site web</li>
 * </ul>
 *
 * <p>Ce contributeur est utile pour fournir des informations aux outils de monitoring,
 * de déploiement ou lors du support technique.</p>
 *
 * @see org.springframework.boot.actuate.info.InfoContributor
 * @see org.springframework.boot.actuate.autoconfigure.info.InfoEndpointAutoConfiguration
 */
@Component
public class CustomInfoContributor implements InfoContributor {

    /**
     * Propriétés de build automatiquement injectées par Spring Boot
     * si {@code build-info.properties} est présent dans le classpath.
     * <p>
     * Permet notamment de récupérer dynamiquement la version de l'application.
     */
    @Autowired(required = false)
    private BuildProperties buildProperties;

    /**
     * Construit les informations personnalisées et les injecte dans l'objet {@link Info.Builder}
     * utilisé par l'endpoint {@code /actuator/info}.
     *
     * @param builder le constructeur d'information à enrichir
     */
    @Override
    public void contribute(Info.Builder builder) {
        // Détails de l'application
        Map<String, Object> appDetails = new HashMap<>();
        appDetails.put("name", "Hairbook Backend");
        appDetails.put("description", "API backend pour l'application de réservation de salon de coiffure Hairbook");
        appDetails.put("version", Optional.ofNullable(buildProperties)
                .map(BuildProperties::getVersion)
                .orElse("dev"));

        // Fonctionnalités de l'application
        Map<String, Object> features = new HashMap<>();
        features.put("authentication", "JWT avec refresh token");
        features.put("reservation", "Système de réservation de rendez-vous");
        features.put("payment", "Intégration avec Stripe");
        features.put("notifications", "Emails et notifications in-app");

        // Informations de contact
        Map<String, Object> contact = new HashMap<>();
        contact.put("developers", "Équipe Hairbook");
        contact.put("website", "https://hairbook.com");
        contact.put("email", "contact@hairbook.com");

        // Ajout au builder
        builder.withDetail("application", appDetails);
        builder.withDetail("features", features);
        builder.withDetail("contact", contact);
    }
}
