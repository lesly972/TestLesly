package com.gdu.wacdo.entities;

import java.util.Date;

public class Affectation {

    private Long id;
    private Date dateDebut;
    private Date dateFin;
    private Fonctions fonctions;
    private Collaborateurs collaborateurs;
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
}
