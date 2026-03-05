package PFE.project.ForestFire.security;
/**
 * Gère les erreurs d'authentification JWT.
 * Retourne une erreur 401 lorsque l'utilisateur
 * tente d'accéder à une ressource protégée sans être authentifié.
 */
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JWTAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {

        response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized: " + authException.getMessage()
        );
    }
}