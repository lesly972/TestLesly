package com.gdu.wacdo.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
//@Table(
       // name = "affectation",
      //  uniqueConstraints = @UniqueConstraint(columnNames = {"collaborateurs_id", "restaurants_id"})
//)
public class Affectation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateDebut;
    private Date dateFin;

    @ManyToOne
    @JoinColumn(name = "fonctions_id", nullable = false)
    private Fonctions fonctions;

    @ManyToOne
    @JoinColumn(name = "collaborateurs_id", nullable = false)
    private Collaborateurs collaborateurs;

    @ManyToOne
    @JoinColumn(name = "restaurants_id", nullable = false)
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
