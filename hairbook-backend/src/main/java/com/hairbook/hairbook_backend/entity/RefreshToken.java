package com.hairbook.hairbook_backend.entity;

import jakarta.persistence.*;

import java.time.Instant;

/**
 * Représente un jeton de rafraîchissement utilisé pour renouveler un accès utilisateur sans devoir se reconnecter.
 *
 * <p>Chaque {@code RefreshToken} est lié à un utilisateur unique, contient une date d'expiration, 
 * des métadonnées de sécurité (agent utilisateur, IP), ainsi qu'un statut indiquant s’il a été révoqué.</p>
 *
 * <p>Cette entité est mappée à la table <strong>refresh_tokens</strong> dans la base de données.</p>
 *
 * @see User
 */
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    /**
     * Identifiant unique du jeton.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Valeur textuelle du jeton (unique et obligatoire).
     */
    @Column(nullable = false, unique = true)
    private String token;

    /**
     * Utilisateur associé à ce jeton (relation OneToOne).
     */
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * Date d'expiration du jeton.
     */
    @Column(nullable = false)
    private Instant expiryDate;

    /**
     * Agent utilisateur (navigateur/appareil) à l'origine du jeton.
     */
    @Column(nullable = false)
    private String userAgent;

    /**
     * Adresse IP utilisée lors de la création du jeton.
     */
    @Column(nullable = false)
    private String ipAddress;

    /**
     * Indique si le jeton a été révoqué (par défaut : false).
     */
    @Column(nullable = false)
    private Boolean revoked = false;

    /**
     * Date de création du jeton (automatiquement remplie).
     */
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Date de la dernière mise à jour du jeton.
     */
    @Column(nullable = false)
    private Instant updatedAt;

    /**
     * Initialisation automatique des champs {@code createdAt}, {@code updatedAt}, et {@code revoked}.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        if (this.revoked == null) {
            this.revoked = false;
        }
    }

    /**
     * Mise à jour automatique de {@code updatedAt} lors des modifications.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    // ----- Constructeurs -----

    /**
     * Constructeur sans argument requis par JPA.
     */
    public RefreshToken() {}

    /**
     * Constructeur complet.
     *
     * @param id identifiant
     * @param token valeur du jeton
     * @param user utilisateur associé
     * @param expiryDate date d’expiration
     * @param userAgent agent utilisateur
     * @param ipAddress adresse IP
     * @param revoked statut de révocation
     * @param createdAt date de création
     * @param updatedAt date de mise à jour
     */
    public RefreshToken(Long id, String token, User user, Instant expiryDate, String userAgent,
                        String ipAddress, Boolean revoked, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
        this.userAgent = userAgent;
        this.ipAddress = ipAddress;
        this.revoked = revoked;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ----- Getters & Setters -----

    /** @return identifiant du jeton */
    public Long getId() { return id; }

    /** @param id identifiant à définir */
    public void setId(Long id) { this.id = id; }

    /** @return valeur du token */
    public String getToken() { return token; }

    /** @param token valeur à définir */
    public void setToken(String token) { this.token = token; }

    /** @return utilisateur lié au jeton */
    public User getUser() { return user; }

    /** @param user utilisateur à associer */
    public void setUser(User user) { this.user = user; }

    /** @return date d'expiration */
    public Instant getExpiryDate() { return expiryDate; }

    /** @param expiryDate date à définir */
    public void setExpiryDate(Instant expiryDate) { this.expiryDate = expiryDate; }

    /** @return agent utilisateur */
    public String getUserAgent() { return userAgent; }

    /** @param userAgent agent utilisateur à définir */
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    /** @return adresse IP d'origine */
    public String getIpAddress() { return ipAddress; }

    /** @param ipAddress adresse IP à définir */
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    /** @return statut de révocation */
    public Boolean getRevoked() { return revoked; }

    /** @param revoked true si le token est révoqué */
    public void setRevoked(Boolean revoked) { this.revoked = revoked; }

    /** @return date de création */
    public Instant getCreatedAt() { return createdAt; }

    /** @param createdAt date de création à définir */
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    /** @return date de dernière mise à jour */
    public Instant getUpdatedAt() { return updatedAt; }

    /** @param updatedAt date de mise à jour à définir */
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    // ----- Méthodes utilitaires -----

    /**
     * Vérifie si le jeton est expiré.
     * @return {@code true} si la date d’expiration est passée, sinon {@code false}
     */
    public boolean isExpired() {
        return Instant.now().isAfter(expiryDate);
    }

    /**
     * Vérifie si le jeton a été révoqué.
     * @return {@code true} si le jeton est marqué comme révoqué
     */
    public boolean isRevoked() {
        return revoked != null && revoked;
    }
}
