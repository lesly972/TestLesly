// Ce fichier représente le "repository" ou dépôt pour gérer les objets de type Fonctions
// En gros c'est ici qu'on va gérer toutes les opérations en lien avec la base de données pour les fonctions (comme "Cuisinier", "Manager", etc)

package com.gdu.wacdo.repositories;

// On importe l'entité "Fonctions" pour pouvoir la manipuler ici
import com.gdu.wacdo.entities.Fonctions;

// JpaRepository est une interface fournie par Spring Data JPA qui permet d’interagir facilement avec la BDD
// Elle permet d'avoir les méthodes CRUD de base (create, read, update, delete) déjà toutes prêtes !
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Cette annotation indique que cette interface est un bean Spring et qu'elle sera automatiquement gérée par le conteneur Spring
@Repository
public interface FonctionsRepository extends JpaRepository<Fonctions, Long> {

    // Pour l’instant on utilise juste les méthodes de base de JpaRepository,
    // comme findAll(), findById(), save(), deleteById(), etc.

}
