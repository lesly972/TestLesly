package com.gdu.wacdo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

//@Data

//Entity va dire à la base d'utiliser le nom de la class ici Restaurants pour la transformer en table
@Entity

public class Restaurants {

    @Id //définis que l'attribu id ci-dessous deviens la clef primaire.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String adresse;
    private String codePostal;
    private String ville;

    public Restaurants(String ville, String codePostal, String adresse, String nom, Long id) {
        this.ville = ville;
        this.codePostal = codePostal;
        this.adresse = adresse;
        this.nom = nom;
        // this.id = id;
    }

    public Restaurants() {

    }

    public Restaurants(Object nom, Object ville, Object adresse, Object codePostal) {
    }
}
