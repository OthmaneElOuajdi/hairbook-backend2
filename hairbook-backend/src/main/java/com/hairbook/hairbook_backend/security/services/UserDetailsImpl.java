package com.hairbook.hairbook_backend.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hairbook.hairbook_backend.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implémentation personnalisée de {@link UserDetails} utilisée par Spring Security pour gérer
 * les détails d'authentification et d'autorisation d'un utilisateur.
 *
 * Cette classe encapsule l'entité {@link User} et expose ses informations d'une manière compatible
 * avec les mécanismes d'authentification de Spring Security.
 *
 * Les rôles sont transformés en objets {@link GrantedAuthority} pour être compris par le système de sécurité.
 *
 * @see User
 * @see org.springframework.security.core.userdetails.UserDetailsService
 */
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructeur principal.
     *
     * @param id           identifiant de l'utilisateur
     * @param username     nom d'utilisateur
     * @param email        adresse e-mail
     * @param password     mot de passe (non exposé en JSON)
     * @param firstName    prénom
     * @param lastName     nom de famille
     * @param phoneNumber  numéro de téléphone
     * @param authorities  rôles/autorités de l'utilisateur
     */
    public UserDetailsImpl(Long id, String username, String email, String password, String firstName, String lastName, String phoneNumber,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.authorities = authorities;
    }

    /**
     * Construit une instance de {@code UserDetailsImpl} à partir d'une entité {@link User}.
     *
     * @param user l'entité utilisateur
     * @return une instance de {@code UserDetailsImpl}
     */
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * @return l'identifiant unique de l'utilisateur
     */
    public Long getId() {
        return id;
    }

    /**
     * @return l'adresse e-mail de l'utilisateur
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return le prénom de l'utilisateur
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return le nom de famille de l'utilisateur
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return le numéro de téléphone de l'utilisateur
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return le mot de passe de l'utilisateur (masqué dans les réponses JSON)
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * @return le nom d'utilisateur utilisé pour l'authentification
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * @return {@code true} si le compte n'est pas expiré
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @return {@code true} si le compte n'est pas verrouillé
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return {@code true} si les identifiants ne sont pas expirés
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @return {@code true} si le compte est activé
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Vérifie l'égalité des utilisateurs basée sur leur identifiant.
     *
     * @param o objet à comparer
     * @return {@code true} si les identifiants sont égaux
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
