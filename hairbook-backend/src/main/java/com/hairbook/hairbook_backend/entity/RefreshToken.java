package com.hairbook.hairbook_backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.Instant;

@Schema(description = "Représente un jeton de rafraîchissement utilisé pour renouveler un accès utilisateur sans devoir se reconnecter")
@Entity
@Table(name = "refresh_tokens", uniqueConstraints = {
    // Suppression de la contrainte d'unicité sur user_id
})
public class RefreshToken {

    @Schema(description = "Identifiant unique du jeton", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Valeur textuelle du jeton unique", example = "eyJhbGciOiJIUzI1NiJ9...", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(nullable = false, unique = true)
    private String token;

    @Schema(description = "Utilisateur associé à ce jeton")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Schema(description = "Date d'expiration du jeton", example = "2024-01-15T10:00:00Z", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(nullable = false)
    private Instant expiryDate;

    @Schema(description = "Agent utilisateur à l'origine du jeton", example = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(nullable = false)
    private String userAgent;

    @Schema(description = "Adresse IP utilisée lors de la création du jeton", example = "192.168.1.1", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(nullable = false)
    private String ipAddress;

    @Schema(description = "Indique si le jeton a été révoqué", example = "false")
    @Column(nullable = false)
    private Boolean revoked = false;

    @Schema(description = "Date de création du jeton", example = "2024-01-15T10:00:00Z", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Schema(description = "Date de la dernière mise à jour du jeton", example = "2024-01-15T10:05:00Z", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        if (this.revoked == null) {
            this.revoked = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    // ----- Constructeurs -----

    public RefreshToken() {}

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
    
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Instant getExpiryDate() { return expiryDate; }

    public void setExpiryDate(Instant expiryDate) { this.expiryDate = expiryDate; }

    public String getUserAgent() { return userAgent; }

    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public String getIpAddress() { return ipAddress; }

    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public Boolean getRevoked() { return revoked; }

    public void setRevoked(Boolean revoked) { this.revoked = revoked; }

    public Instant getCreatedAt() { return createdAt; }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public boolean isExpired() {
        return Instant.now().isAfter(expiryDate);
    }

    public boolean isRevoked() {
        return revoked != null && revoked;
    }
}
