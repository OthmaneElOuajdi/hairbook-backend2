package com.hairbook.hairbook_backend.security.jwt;

import com.hairbook.hairbook_backend.config.AppProperties;
import com.hairbook.hairbook_backend.config.JwtProperties;
import com.hairbook.hairbook_backend.security.services.UserDetailsImpl;
import com.hairbook.hairbook_backend.util.LoggingService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utilitaire pour la gestion des tokens JWT.
 * 
 * Cette classe fournit des méthodes pour :
 * <ul>
 *   <li>Générer un token JWT pour un utilisateur authentifié</li>
 *   <li>Extraire le nom d'utilisateur depuis un token</li>
 *   <li>Valider un token JWT</li>
 * </ul>
 * 
 * Le token est signé avec une clé secrète définie dans les propriétés de configuration.
 * 
 * @see com.hairbook.hairbook_backend.config.JwtProperties
 * @see com.hairbook.hairbook_backend.security.services.UserDetailsImpl
 */
@Component
public class JwtUtils {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private AppProperties appProperties;

    /**
     * Génère un token JWT basé sur l'objet {@link Authentication} fourni.
     *
     * @param authentication l'objet Authentication de Spring Security
     * @return un token JWT signé, encodé sous forme de chaîne
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + appProperties.getJwtExpirationMs()))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Retourne la clé secrète utilisée pour signer et valider les tokens JWT.
     * La clé est générée à partir de la propriété `jwt.secret` encodée en Base64.
     *
     * @return une clé HMAC-SHA signée
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
    }

    /**
     * Extrait le nom d'utilisateur contenu dans un token JWT.
     *
     * @param token le token JWT
     * @return le nom d'utilisateur (claim "subject")
     * @throws JwtException si le token est invalide ou mal signé
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Génère un token JWT à partir d'un nom d'utilisateur.
     *
     * @param username le nom d'utilisateur
     * @return un token JWT signé
     */
    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + appProperties.getJwtExpirationMs()))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valide un token JWT.
     * Vérifie la signature, la date d'expiration, et le format général du token.
     *
     * @param authToken le token JWT à valider
     * @return {@code true} si le token est valide, {@code false} sinon
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            LoggingService.logError(this.getClass(), "Token JWT invalide: {0}", e);
        } catch (ExpiredJwtException e) {
            LoggingService.logError(this.getClass(), "Token JWT expiré: {0}", e);
        } catch (UnsupportedJwtException e) {
            LoggingService.logError(this.getClass(), "Token JWT non supporté: {0}", e);
        } catch (IllegalArgumentException e) {
            LoggingService.logError(this.getClass(), "La chaîne de revendications JWT est vide: {0}", e);
        }

        return false;
    }
}
