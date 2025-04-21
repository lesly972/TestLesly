// Package dans lequel est rangée la classe (on garde tout bien organisé)
package com.gdu.wacdo.entities;

// Importation des annotations et classes nécessaires pour gérer les entités
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// @Entity indique à Spring/Hibernate que cette classe doit être liée à une table dans la base de données
@Entity
public class Restaurants {

    // L'identifiant unique du restaurant (clé primaire auto-générée)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nom du restaurant (ex : Wacdo Paris 1)
    private String nom;

    // Adresse postale (numéro et rue)
    private String adresse;

    // Code postal du restaurant
    private String codePostal;

    // Ville où se situe le restaurant
    private String ville;

    // Relation OneToMany : un restaurant peut avoir plusieurs affectations (plusieurs employés qui y bossent à différents moments)
    @OneToMany(mappedBy = "restaurants") // "restaurants" correspond à la propriété dans la classe Affectation
    private List<Affectation> affectations = new ArrayList<>();

    // Constructeur avec paramètres (pratique pour créer rapidement un restaurant)
    public Restaurants(String ville, String codePostal, String adresse, String nom) {
        this.ville = ville;
        this.codePostal = codePostal;
        this.adresse = adresse;
        this.nom = nom;
    }

    // Constructeur vide requis par JPA/Hibernate
    public Restaurants() {}

    // --------------------------
    // Getters & Setters
    // --------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
