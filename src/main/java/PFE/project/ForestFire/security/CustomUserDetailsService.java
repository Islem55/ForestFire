package PFE.project.ForestFire.security;
/**
 * Cette classe permet à Spring Security de charger les informations
 * d’un utilisateur depuis la base de données lors de l’authentification.
 * Elle recherche l’utilisateur par son nom via UserRepo, puis retourne
 * ses informations (nom, mot de passe et rôle) sous forme d’un
 * objet UserDetails afin que Spring puisse vérifier les identifiants
 * et attribuer les autorisations nécessaires.
 */
import PFE.project.ForestFire.entities.RoleEntity;
import PFE.project.ForestFire.entities.UserEntity;
import PFE.project.ForestFire.repository.UserRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Utilisateur non trouvé"));

        return new User(
                user.getEmail(),
                user.getMotDePasse(),
                mapRolesToAuthorities(user.getRole())
        );
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(RoleEntity userRole) {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(
                new SimpleGrantedAuthority(userRole.getRoleName().name())
        );

        return authorities;
    }
}