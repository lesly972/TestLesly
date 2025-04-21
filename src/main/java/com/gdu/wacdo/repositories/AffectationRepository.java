// Package dans lequel on classe tous nos repositories (accès à la base de données)
package com.gdu.wacdo.repositories;

// Importation de notre entité Affectation
import com.gdu.wacdo.entities.Affectation;

// Importation de JpaRepository pour bénéficier des méthodes toutes faites (CRUD, etc.)
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Cette annotation @Repository dit à Spring que cette interface est un "composant" pour gérer l'accès aux données
@Repository
public interface AffectationRepository extends JpaRepository<Affectation, Long> {

    //  Méthode pour récupérer toutes les affectations liées à un restaurant en particulier (grâce à son ID)
    List<Affectation> findByRestaurants_Id(Long id);

    //  Méthode qui permet de récupérer les affectations d’un collaborateur en les triant par date de début (du plus récent au plus ancien)
    List<Affectation> findByCollaborateurs_IdOrderByDateDebutDesc(Long collaborateurId);

    //  Méthode pour savoir si un collaborateur est déjà affecté à un restaurant actuellement (dateFin encore null = affectation en cours)
    boolean existsByCollaborateurs_IdAndRestaurants_IdAndDateFinIsNull(Long collaborateurId, Long restaurantId);

    // Cette méthode ci-dessous est commentée, mais elle aurait permis de vérifier si une même personne est déjà affectée à un poste précis dans un restaurant
    // boolean existsByCollaborateurs_IdAndRestaurants_IdAndFonctions_IdAndDateFinIsNull(Long collaborateurId, Long restaurantId, Long fonctionId);
}
