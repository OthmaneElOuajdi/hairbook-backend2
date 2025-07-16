package com.hairbook.hairbook_backend.security.services;

import com.hairbook.hairbook_backend.entity.User;
import com.hairbook.hairbook_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation personnalisée de {@link UserDetailsService} utilisée par Spring Security.
 *
 * Cette classe charge un utilisateur à partir de la base de données selon son nom d'utilisateur **ou** son e-mail,
 * puis le convertit en un objet {@link UserDetailsImpl} que Spring Security peut utiliser pour l'authentification.
 *
 * Elle est appelée automatiquement par Spring lors de l'authentification via un token JWT ou formulaire.
 *
 * @see UserDetailsImpl
 * @see User
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    /**
     * Charge un utilisateur à partir de son nom d'utilisateur ou de son adresse e-mail.
     * Cette méthode est invoquée par Spring Security pendant le processus d'authentification.
     *
     * @param username le nom d'utilisateur ou l'e-mail utilisé pour s'authentifier
     * @return un objet {@link UserDetails} contenant les informations de l'utilisateur
     * @throws UsernameNotFoundException si aucun utilisateur correspondant n'est trouvé
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseGet(() -> userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException(
                                "Utilisateur non trouvé avec le nom d'utilisateur ou email: " + username)));

        return UserDetailsImpl.build(user);
    }
}
