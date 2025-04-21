package com.gdu.wacdo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // on dit à Spring que c'est une classe de configuration (logique, vu le nom lol)
public class WebSecurityConfig {

    // Ici on configure tout ce qui concerne la sécurité HTTP de l'appli (les routes protégées, le login, etc.)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Protection CSRF activée pour éviter les failles de type "cross-site request forgery"
        http.csrf(csrf -> csrf.csrfTokenRepository(new HttpSessionCsrfTokenRepository()))

                // Ici on définit qui peut accéder à quoi dans l'appli
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll() // tout le monde peut accéder à la page d'accueil
                        .requestMatchers("/images/**").permitAll() // les images sont accessibles sans être connecté
                        .requestMatchers("/restaurants/**").hasAuthority("ROLE_ADMIN") // seules les admins peuvent gérer les restos
                        .requestMatchers("/collaborateurs/**").hasAuthority("ROLE_ADMIN") // pareil pour les collaborateurs
                        .requestMatchers("/fonctions/**").hasAuthority("ROLE_ADMIN") // pareil pour les fonctions
                        .requestMatchers("/affectation/**").hasAuthority("ROLE_ADMIN") // affectations aussi réservées aux admins
                        .requestMatchers(HttpMethod.POST,"/affectation").hasAuthority("ROLE_ADMIN") // protection des envois POST
                        .anyRequest().authenticated() // tout le reste nécessite une connexion
                )

                // Configuration de base pour la page de login (Spring en fournit une toute simple par défaut)
                .formLogin(Customizer.withDefaults())

                // Gestion de la déconnexion
                .logout(logout -> logout
                        .logoutUrl("/logout") // l’URL pour se déconnecter
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // matcher qui permet que le logout fonctionne même en GET
                        .logoutSuccessUrl("/?logout=true") // redirection après la déconnexion
                        .invalidateHttpSession(true) // on détruit la session pour être clean
                        .deleteCookies("JSESSIONID") // on supprime le cookie de session aussi
                );

        return http.build(); // on renvoie notre config
    }

    // Ici c’est pour encoder les mots de passe avec BCrypt (très utilisé, sécurisé)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Ce bean permet à Spring de récupérer les infos des utilisateurs pour la connexion
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // c’est ici qu’on indique comment retrouver les utilisateurs
        authProvider.setPasswordEncoder(passwordEncoder); // ici on dit d’utiliser BCrypt pour vérifier les mots de passe
        return authProvider;
    }

    // Ce bean permet d’avoir un gestionnaire d’authentification complet
    // Il s’occupe de vérifier que les identifiants sont bons au moment du login
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        return new ProviderManager(authenticationProvider(userDetailsService, passwordEncoder));
    }
}
