package com.gdu.wacdo.configuration;

import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

@Service

public class CustomUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){

        //Retrouver utilisateur qui veu se connecter
        Optional<Collaborateurs> userOpt = userRepository.findByPseudo(username);

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
