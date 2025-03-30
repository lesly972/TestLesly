package com.gdu.wacdo.dtos;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CollaborateursDto {

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;

    @DateTimeFormat(pattern = "yyyy-MM-dd") // Permet d'adapter le format de la date
    private Date datePremierEmbauche;
    private Boolean administrateur;

    public CollaborateursDto(String nom,String prenom, String email, String motDePasse, Date datePremierEmbauche, Boolean administrateur) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.datePremierEmbauche = datePremierEmbauche;
        this.administrateur = administrateur;

    }


    public CollaborateursDto() {

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
        return administrateur;
    }

    public void setAdministrateur(Boolean administrateur) {
        this.administrateur = administrateur;
    }


}
