package com.gdu.wacdo.configuration;

import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.repositories.CollaborateursRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

@Service // On indique que cette classe est un service géré par Spring (elle est injecté automatiquement)
public class CustomUserDetailsService implements UserDetailsService {

    private final CollaborateursRepository collaborateursRepository;

    // On injecte le repository pour aller chercher les infos des utilisateurs dans la base
    public CustomUserDetailsService(CollaborateursRepository collaborateursRepository) {
        this.collaborateursRepository = collaborateursRepository;
    }

    // Méthode obligée quand on implémente UserDetailsService. Spring l'appel quand quelqu'un essaye de se connecter.
    @Override
    public UserDetails loadUserByUsername(String username) {

        // On cherche un collaborateur dans la base avec son adresse mail (username = email ici)
        Optional<Collaborateurs> userOpt = collaborateursRepository.findByEmail(username);

        // Si on trouve quelqu'un
        if (userOpt.isPresent()) {
            Collaborateurs user = userOpt.get(); // On récupère l'utilisateur

            // On regarde si il est admin ou pas, pour donner le bon rôle
            String role = user.getAdministrateur() ? "ROLE_ADMIN" : "ROLE_USER";

            // Juste pour debug, ça s'affiche dans la console (ça sert à vérifier que les rôles sont bien donnés)
            System.out.println("Rôle donné :" + role);

            // On construit l'objet User (spécifique à Spring Security)
            // avec son email, mot de passe, et les rôles (admin ou pas)
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail()) // ici c'est le login
                    .password(user.getMotDePasse()) // le mot de passe est déjà crypté normalement
                    .authorities(role) // les rôles, pour les autorisations dans l'app
                    .build();
        }

        // Si aucun utilisateur trouvé avec cet email, on balance une exception
        throw new UsernameNotFoundException("User not found");
    }
}
