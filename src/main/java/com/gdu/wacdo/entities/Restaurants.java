package com.gdu.wacdo.entities;

import lombok.Data;
@Data
public class Restaurants {

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
        this.id = id;
    }
}
