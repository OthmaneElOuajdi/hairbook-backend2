package com.hairbook.hairbook_backend.security.jwt;

import com.hairbook.hairbook_backend.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtre d'authentification JWT exécuté une fois par requête.
 * 
 * Ce filtre intercepte les requêtes HTTP, extrait le token JWT de l'en-tête Authorization,
 * le valide, puis initialise le contexte de sécurité Spring avec les informations utilisateur si le token est valide.
 * 
 * Il permet de sécuriser les endpoints REST en s'assurant que les requêtes proviennent
 * d'utilisateurs authentifiés via un token JWT valide.
 * 
 * Ce filtre fonctionne avec {@link JwtUtils} pour la gestion des tokens
 * et {@link UserDetailsServiceImpl} pour charger les détails utilisateur.
 * 
 * Exemple d'en-tête attendu : Authorization: Bearer &lt;token_jwt&gt;
 * 
 * @see JwtUtils
 * @see UserDetailsServiceImpl
 */
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    /**
     * Méthode principale exécutée à chaque requête HTTP.
     * Elle extrait le JWT, le valide, récupère les informations utilisateur,
     * puis configure l'objet d'authentification Spring Security.
     *
     * @param request     la requête HTTP
     * @param response    la réponse HTTP
     * @param filterChain la chaîne de filtres à exécuter
     * @throws ServletException en cas d'erreur liée au filtre
     * @throws IOException      en cas d'erreur d'E/S
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Impossible de définir l'authentification de l'utilisateur: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extrait le token JWT de l'en-tête Authorization de la requête HTTP.
     *
     * @param request la requête HTTP
     * @return le token JWT s'il est présent et bien formé, sinon {@code null}
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
