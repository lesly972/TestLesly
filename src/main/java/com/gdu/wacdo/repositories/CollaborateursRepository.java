// Ce fichier est placé dans le package repository, c'est ici qu'on gère l'accès à la base de données
package com.gdu.wacdo.repositories;

// On importe notre entité Collaborateurs pour l'utiliser dans les méthodes
import com.gdu.wacdo.entities.Collaborateurs;

// On importe JpaRepository pour avoir toutes les méthodes classiques (save, findAll, delete, etc.)
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Cette annotation dit à Spring que cette interface est un composant qui gère les requêtes vers la base
@Repository
public interface CollaborateursRepository extends JpaRepository<Collaborateurs, Long> {

    // Cette méthode va permettre de chercher un collaborateur à partir de son email
    // Elle renvoie un Optional pour éviter les NullPointerException si y a pas de résultat
    Optional<Collaborateurs> findByEmail(String username);

    // Spring comprend "findByEmail" et fait la requête SQL derrière automatiquement
    // C'est très pratique pour l'authentification, genre quand l'utilisateur essaye de se connecter
}
