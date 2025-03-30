package com.gdu.wacdo.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Affectation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateDebut;
    private Date dateFin;

    @OneToOne
    @JoinColumn(name = "fonction_id", unique = true)
    private Fonctions fonctions;


    @ManyToOne
    @JoinColumn(name = "collaborateur_id")
    private Collaborateurs collaborateurs;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurants restaurants;

    public Affectation(Long id, Date dateDebut, Date dateFin, Fonctions fonctions, Collaborateurs collaborateurs, Restaurants restaurants) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.fonctions = fonctions;
        this.collaborateurs = collaborateurs;
        this.restaurants = restaurants;
    }

    public Affectation() {

    }

    public Long getId() {
        return id;
    }

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

    public Fonctions getFonctions() {
        return fonctions;
    }

    public void setFonctions(Fonctions fonctions) {
        this.fonctions = fonctions;
    }

    public Collaborateurs getCollaborateurs() {
        return collaborateurs;
    }

    public void setCollaborateurs(Collaborateurs collaborateurs) {
        this.collaborateurs = collaborateurs;
    }

    public Restaurants getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Restaurants restaurants) {
        this.restaurants = restaurants;
    }
}
