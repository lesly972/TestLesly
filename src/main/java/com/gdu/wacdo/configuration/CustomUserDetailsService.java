package com.gdu.wacdo.configuration;

import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.repositories.CollaborateursRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private final CollaborateursRepository collaborateursRepository;

    public CustomUserDetailsService(CollaborateursRepository collaborateursRepository) {
        this.collaborateursRepository = collaborateursRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){

        //Retrouver utilisateur qui veu se connecter
        Optional<Collaborateurs> userOpt = collaborateursRepository.findByEmail(username);

        //Verifier
        if(userOpt.isPresent()){
            Collaborateurs user = userOpt.get();

            //Attribuer des rôles
            String role = user.getAdministrateur() ? "ROLE_ADMIN" : "ROLE_USER";
            System.out.println("Rôle donné :" + role);

            // Construire
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getMotDePasse())
                    .authorities(role)
                    .build();
        }

        throw new UsernameNotFoundException("User not found");

    }
}
