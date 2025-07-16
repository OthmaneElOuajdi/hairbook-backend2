package com.hairbook.hairbook_backend.service;

import com.hairbook.hairbook_backend.entity.ERole;
import com.hairbook.hairbook_backend.entity.Role;
import com.hairbook.hairbook_backend.entity.User;
import com.hairbook.hairbook_backend.repository.RoleRepository;
import com.hairbook.hairbook_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service métier pour la gestion des utilisateurs.
 * <p>
 * Fournit des opérations pour récupérer, modifier, supprimer et compter les utilisateurs,
 * ainsi que pour gérer leurs rôles.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Récupère tous les utilisateurs présents en base.
     *
     * @return la liste complète des utilisateurs
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur
     * @return l'utilisateur correspondant
     * @throws ResponseStatusException si l'utilisateur n'est pas trouvé
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
    }

    /**
     * Récupère un utilisateur par son nom d'utilisateur (username).
     *
     * @param username le nom d'utilisateur
     * @return l'utilisateur correspondant
     * @throws ResponseStatusException si l'utilisateur n'est pas trouvé
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
    }

    /**
     * Met à jour les informations d'un utilisateur existant.
     *
     * @param id           l'identifiant de l'utilisateur à modifier
     * @param userDetails  les nouvelles données utilisateur
     * @return l'utilisateur mis à jour
     * @throws ResponseStatusException si le username ou l'email sont déjà utilisés
     */
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);

        if (!user.getUsername().equals(userDetails.getUsername()) &&
                userRepository.existsByUsername(userDetails.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ce nom d'utilisateur est déjà pris");
        }

        if (!user.getEmail().equals(userDetails.getEmail()) &&
                userRepository.existsByEmail(userDetails.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cet email est déjà utilisé");
        }

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setPhoneNumber(userDetails.getPhoneNumber());

        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return userRepository.save(user);
    }

    /**
     * Met à jour les rôles attribués à un utilisateur.
     *
     * @param id       l'identifiant de l'utilisateur
     * @param strRoles liste des rôles sous forme de chaînes ("admin", "visitor", etc.)
     * @return l'utilisateur mis à jour avec ses nouveaux rôles
     * @throws RuntimeException si un rôle est introuvable
     */
    public User updateUserRoles(Long id, Set<String> strRoles) {
        User user = getUserById(id);
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role memberRole = roleRepository.findByName(ERole.ROLE_MEMBER)
                    .orElseThrow(() -> new RuntimeException("Erreur: Role membre non trouvé."));
            roles.add(memberRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Erreur: Role admin non trouvé."));
                        roles.add(adminRole);
                        break;
                    case "visitor":
                        Role visitorRole = roleRepository.findByName(ERole.ROLE_VISITOR)
                                .orElseThrow(() -> new RuntimeException("Erreur: Role visiteur non trouvé."));
                        roles.add(visitorRole);
                        break;
                    default:
                        Role memberRole = roleRepository.findByName(ERole.ROLE_MEMBER)
                                .orElseThrow(() -> new RuntimeException("Erreur: Role membre non trouvé."));
                        roles.add(memberRole);
                }
            });
        }

        user.setRoles(roles);
        return userRepository.save(user);
    }

    /**
     * Supprime un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur à supprimer
     */
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    /**
     * Compte le nombre d'utilisateurs ayant un rôle donné.
     *
     * @param roleName le rôle à rechercher
     * @return le nombre d'utilisateurs avec ce rôle
     * @throws RuntimeException si le rôle est introuvable
     */
    public long countUsersByRole(ERole roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Erreur: Role non trouvé."));
        return userRepository.countByRolesContaining(role);
    }

    /**
     * Récupère tous les utilisateurs ayant un rôle spécifique.
     *
     * @param roleName le rôle à rechercher
     * @return la liste des utilisateurs associés à ce rôle
     * @throws RuntimeException si le rôle est introuvable
     */
    public List<User> getUsersByRole(ERole roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Erreur: Role non trouvé."));
        return userRepository.findByRolesContaining(role);
    }
}
