package com.gdu.wacdo.dtos;

public class RestaurantsDto {

    private String nom;
    private String adresse;
    private String codePostal;
    private String ville;

    public RestaurantsDto(String ville, String codePostal, String adresse, String nom, Long id) {
        this.ville = ville;
        this.codePostal = codePostal;
        this.adresse = adresse;
        this.nom = nom;

    }

    public RestaurantsDto() {
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