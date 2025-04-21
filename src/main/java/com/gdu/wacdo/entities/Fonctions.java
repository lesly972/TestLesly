// Le package où est rangée cette entité (on reste bien organisé dans le projet)
package com.gdu.wacdo.entities;

import jakarta.persistence.*;
import java.util.List;

// Cette annotation permet de dire à Spring/Hibernate que cette classe est une entité (donc elle sera liée à une table dans la base de données)
@Entity
public class Fonctions {

    // Clé primaire (chaque fonction aura son propre identifiant unique)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Le nom du poste (par exemple : Serveur, Manager, Cuisinier...)
    private String nomDuPoste;

    // Une fonction peut être occupée par plusieurs collaborateurs à travers les affectations
    // => Relation OneToMany avec la table Affectation (mappedBy doit matcher avec la propriété dans Affectation.java)
    @OneToMany(mappedBy = "fonctions")
    private List<Affectation> affectations;

    // Constructeur avec le nom du poste en paramètre (utile lors de la création d'un nouveau poste)
    public Fonctions(String nomDuPoste) {
        this.nomDuPoste = nomDuPoste;
    }

    // Constructeur vide obligatoire pour Hibernate (il l’utilise pour instancier les objets)
    public Fonctions() {}

    // --------------------------
    // Getters & Setters
    // --------------------------

    public String getNomDuPoste() {
        return nomDuPoste;
    }

    public void setNomDuPoste(String nomDuPoste) {
        this.nomDuPoste = nomDuPoste;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // La liste des affectations n’est pas forcément exposée ici,
    // mais on pourra y accéder si besoin dans les services
}
