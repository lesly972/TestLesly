package com.gdu.wacdo.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity

public class Fonctions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomDuPoste;

    @OneToMany(mappedBy = "fonctions")
    private List<Affectation> affectations;

    public Fonctions(String nomDuPoste){

        this.nomDuPoste = nomDuPoste;
    }

    public Fonctions() {

    }

    public String getNomDuPoste() {
        return nomDuPoste;
    }

    public void setNomDuPoste(String nomDuPoste) {
        this.nomDuPoste = nomDuPoste;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
