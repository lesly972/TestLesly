package com.gdu.wacdo.dtos;

import lombok.Getter;

@Getter
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
}