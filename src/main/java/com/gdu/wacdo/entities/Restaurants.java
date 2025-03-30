package com.gdu.wacdo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "restaurants")
    private List<Affectation> affectations = new ArrayList<>();

    public Restaurants(String ville, String codePostal, String adresse, String nom) {
        this.ville = ville;
        this.codePostal = codePostal;
        this.adresse = adresse;
        this.nom = nom;
    }

    public Restaurants() {

    }

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
