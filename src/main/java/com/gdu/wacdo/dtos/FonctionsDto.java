package com.gdu.wacdo.dtos;

public class FonctionsDto {

    private String nomDuPoste;

    public FonctionsDto(String nomDuPoste){
        this.nomDuPoste = nomDuPoste;
    }

    public FonctionsDto(){
    }

    public String getNomDuPoste() {
        return nomDuPoste;
    }

    public void setNomDuPoste(String nomDuPoste) {
        this.nomDuPoste = nomDuPoste;
    }
}
