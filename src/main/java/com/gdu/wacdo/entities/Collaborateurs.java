package com.gdu.wacdo.entities;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Data

@Entity
public class Collaborateurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;


    private Date datePremierEmbauche;
    private Boolean isAdministrateur;


    @OneToMany(mappedBy = "collaborateurs")
    private List<Affectation> affectations = new ArrayList<>();

    public Collaborateurs( String nom,String prenom, String email, String motDePasse,Date datePremierEmbauche,Boolean isAdministrateur ) {
        this.motDePasse = motDePasse;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.datePremierEmbauche = datePremierEmbauche;
        this.isAdministrateur = isAdministrateur;
    }

    public Collaborateurs() {

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Date getDatePremierEmbauche() {
        return datePremierEmbauche;
    }

    public void setDatePremierEmbauche(Date datePremierEmbauche) {
        this.datePremierEmbauche = datePremierEmbauche;
    }

    public Boolean getAdministrateur() {
        return isAdministrateur;
    }

    public void setAdministrateur(Boolean administrateur) {
        isAdministrateur = administrateur;
    }

}
