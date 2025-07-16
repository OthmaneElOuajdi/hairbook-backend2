package com.hairbook.hairbook_backend.service;

import com.hairbook.hairbook_backend.entity.Service;
import com.hairbook.hairbook_backend.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Service métier pour la gestion des prestations (services coiffure, soins, etc.).
 */
@Component
public class HairServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    /**
     * Récupère toutes les prestations disponibles (actives ou non).
     *
     * @return liste complète des services
     */
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    /**
     * Récupère uniquement les prestations actives (visibles pour les clients).
     *
     * @return liste des services actifs
     */
    public List<Service> getActiveServices() {
        return serviceRepository.findByActiveTrue();
    }

    /**
     * Récupère une prestation par son identifiant.
     *
     * @param id identifiant du service
     * @return entité Service correspondante
     * @throws ResponseStatusException si le service n'existe pas
     */
    public Service getServiceById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service non trouvé"));
    }

    /**
     * Crée une nouvelle prestation.
     *
     * @param service objet Service à créer
     * @return prestation créée
     * @throws ResponseStatusException si un service avec le même nom existe déjà
     */
    public Service createService(Service service) {
        if (serviceRepository.existsByName(service.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un service avec ce nom existe déjà");
        }
        return serviceRepository.save(service);
    }

    /**
     * Met à jour une prestation existante.
     *
     * @param id identifiant du service à mettre à jour
     * @param serviceDetails détails mis à jour
     * @return prestation mise à jour
     * @throws ResponseStatusException si un autre service utilise déjà ce nom
     */
    public Service updateService(Long id, Service serviceDetails) {
        Service service = getServiceById(id);

        if (!service.getName().equals(serviceDetails.getName()) &&
            serviceRepository.existsByName(serviceDetails.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un service avec ce nom existe déjà");
        }

        service.setName(serviceDetails.getName());
        service.setDescription(serviceDetails.getDescription());
        service.setPrice(serviceDetails.getPrice());
        service.setDurationMinutes(serviceDetails.getDurationMinutes());
        service.setImageUrl(serviceDetails.getImageUrl());
        service.setActive(serviceDetails.isActive());

        return serviceRepository.save(service);
    }

    /**
     * Active ou désactive une prestation (visibilité dans l'interface client).
     *
     * @param id     identifiant du service
     * @param active nouvel état actif/inactif
     * @return service mis à jour
     */
    public Service toggleServiceStatus(Long id, boolean active) {
        Service service = getServiceById(id);
        service.setActive(active);
        return serviceRepository.save(service);
    }

    /**
     * Supprime une prestation du système.
     *
     * @param id identifiant du service à supprimer
     */
    public void deleteService(Long id) {
        Service service = getServiceById(id);
        serviceRepository.delete(service);
    }
}
