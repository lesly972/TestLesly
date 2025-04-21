// Ce fichier représente le repository pour les objets de type "Restaurants"
// C’est lui qui permet de faire le lien entre notre application Java et la base de données, pour tout ce qui concerne les restaurants.

package com.gdu.wacdo.repositories;

// On importe l'entité Restaurants, qu'on va manipuler ici
import com.gdu.wacdo.entities.Restaurants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Cette annotation dit à Spring que cette interface est un bean qu’il doit gérer automatiquement
@Repository
public interface RestaurantsRepository extends JpaRepository<Restaurants,Long> {

}
