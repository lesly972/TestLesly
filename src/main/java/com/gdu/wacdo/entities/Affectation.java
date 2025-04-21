// Package où est stockée la classe (ici dans les entités du projet Wacdo)
package com.gdu.wacdo.entities;

// Importation des annotations JPA pour le mapping en base de données
import jakarta.persistence.*;

import java.util.Date;

// Annotation JPA pour dire que cette classe est une entité (donc reliée à une table en BDD)
@Entity

public class Affectation {

    // Id auto-incrémenté généré automatiquement (clé primaire)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Date à laquelle commence l’affectation
    private Date dateDebut;

    // Date à laquelle elle se termine (null si encore en poste)
    private Date dateFin;

    // Chaque affectation est liée à une seule fonction (poste)
    // Exemple : "Serveur", "Cuisinier", etc.
    @ManyToOne
    @JoinColumn(name = "fonctions_id", nullable = false)
    private Fonctions fonctions;

    // Chaque affectation concerne un seul collaborateur
    // (relation ManyToOne = un collab peut avoir plusieurs affectations dans le temps)
    @ManyToOne
    @JoinColumn(name = "collaborateurs_id", nullable = false)
    private Collaborateurs collaborateurs;

    // Chaque affectation est rattachée à un seul restaurant
    @ManyToOne
    @JoinColumn(name = "restaurants_id", nullable = false)
    private Restaurants restaurants;

    // Constructeur complet (peut servir pour créer une affectation à la main)
    public Affectation(Long id, Date dateDebut, Date dateFin, Fonctions fonctions, Collaborateurs collaborateurs, Restaurants restaurants) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.fonctions = fonctions;
        this.collaborateurs = collaborateurs;
        this.restaurants = restaurants;
    }

    // Constructeur vide obligatoire pour que Spring / Hibernate puisse créer des objets tout seul
    public Affectation() {}

    // Accesseur pour l'id (get = lire la valeur)
    public Long getId() {
        return id;
    }

    // Modificateur pour l'id (set = changer la valeur)
    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    // Accès à la fonction liée à cette affectation
    public Fonctions getFonctions() {
        return fonctions;
    }

    public void setFonctions(Fonctions fonctions) {
        this.fonctions = fonctions;
    }

    // Accès au collaborateur concerné
    public Collaborateurs getCollaborateurs() {
        return collaborateurs;
    }

    public void setCollaborateurs(Collaborateurs collaborateurs) {
        this.collaborateurs = collaborateurs;
    }

    // Accès au restaurant concerné
    public Restaurants getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Restaurants restaurants) {
        this.restaurants = restaurants;
    }
}
