package com.gdu.wacdo.entities;

import java.util.Date;

public class Affectation {

    private Date dateDebut;
    private Date dateFin;
    private Fonctions fonctions;
    private Collaborateurs collaborateurs;
    private Restaurants restaurants;



    public Affectation( Date dateDebut, Date dateFin, Fonctions fonctions, Collaborateurs collaborateurs, Restaurants restaurants,Long id) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.fonctions = fonctions;
        this.collaborateurs = collaborateurs;
        this.restaurants = restaurants;
    }
}
