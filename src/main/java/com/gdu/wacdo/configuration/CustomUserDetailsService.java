package com.gdu.wacdo.configuration;

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
        Optional<com.gdu.wacdo.entities.User> userOpt = userRepository.findByPseudo(username);

        //Verifier
        if(userOpt.isPresent()){
            com.gdu.wacdo.entities.User user = userOpt.get();

            //Attribuer des rôles
            String role = user.getAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
            System.out.println("Rôle donné :" + role);

            // Construire
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getPseudo())
                    .password(user.getPassword())
                    .authorities(role)
                    .build();
        }

        throw new UsernameNotFoundException("User not found");

    }
}
