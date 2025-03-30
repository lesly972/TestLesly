package com.gdu.wacdo.dtos;

import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.entities.Fonctions;
import com.gdu.wacdo.entities.Restaurants;

import java.util.Date;

public class AffectationDto {

    private Date dateDebut;
    private Date dateFin;

    private Long collaborateurId;
    private Long restaurantsId;
    private Long fonctionsId;
    private Date dateAffectation;

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Long getCollaborateurId() {
        return collaborateurId;
    }

    public void setCollaborateurId(Long collaborateurId) {
        this.collaborateurId = collaborateurId;
    }

    public Long getRestaurantsId() {
        return restaurantsId;
    }

    public void setRestaurantsId(Long restaurantsId) {
        this.restaurantsId = restaurantsId;
    }

    public Long getFonctionsId() {
        return fonctionsId;
    }

    public void setFonctionsId(Long fonctionsId) {
        this.fonctionsId = fonctionsId;
    }

    public Date getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(Date dateAffectation) {
        this.dateAffectation = dateAffectation;
    }
}
