package PFE.project.ForestFire.security;
/**
 * Ce filtre JWT est exécuté une seule fois pour chaque requête HTTP.
 * Il vérifie si la requête contient un token JWT valide dans l’en-tête
 * "Authorization" (format : Bearer token).
 *
 * Si le token est valide :
 * - Il extrait le nom d'utilisateur depuis le JWT.
 * - Il charge les informations de l'utilisateur depuis la base de données.
 * - Il crée un objet d'authentification avec ses rôles.
 * - Il enregistre l'utilisateur authentifié dans le SecurityContext.
 *
 * Cela permet à Spring Security de reconnaître l'utilisateur comme
 * authentifié et d'autoriser l'accès aux routes protégées.
 */
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTGenerator jwtGenerator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response , FilterChain filterChain) throws ServletException , IOException {
        //Extraire le JWT de la requete HTTP en appelant la methode getJWTFromRequest()
        String token  = getJWTFromRequest(request);
        //Vérifie si le jeton existe et si celui-ci est valide en utilisant la methode validateToken() du tokenGenerator
        if (StringUtils.hasText(token) && jwtGenerator.validateToken(token)) {
            String email = jwtGenerator.getUsernameFromJWT(token);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        filterChain.doFilter(request,response);

    }
    //Méthode pour extraire le JWT depuis l'en tete "Authorization" de la requete HTTP

    private String getJWTFromRequest (HttpServletRequest request) {
        //recuperer l'en tete authorization de la requete
        String bearerToken = request.getHeader("Authorization");
        // Si l'en tete contient un jeton e qu'il commence par bearer on extait le jeton sans le préfie bearer
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7,bearerToken.length());

        }
        return null;
    }


}
