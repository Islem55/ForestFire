package PFE.project.ForestFire.security;
/**
 * Classe responsable de la gestion des JWT.
 * Génère le token après authentification,
 * extrait le username et valide le token.
 */
import PFE.project.ForestFire.entities.UserEntity;
import PFE.project.ForestFire.repository.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JWTGenerator {
    private final UserRepo userRepo;

    private static final Key key= Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public JWTGenerator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    public String generateToken(Authentication authentication){
        String email =authentication.getName();
        Date currentDate =new Date();
        Date expirationDate = new Date(currentDate.getTime()+ SecurityConstants.JWT_EXPIRATION);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        UserEntity user =userRepo.findByEmail(email).get();
        String token = Jwts.builder()
                .setSubject(email)
                .claim("user", Map.of(
                        "id",user.getId(),
                        "nom",user.getNom(),
                        "email",user.getEmail()

                ))
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();
        System.out.println("token "+token);
        return token;

    }

    public  String getUsernameFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();

    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }
        catch(Exception e){
            throw new AuthenticationCredentialsNotFoundException("JWT Was expired  or incorrect" , e.fillInStackTrace());
        }
    }
}
