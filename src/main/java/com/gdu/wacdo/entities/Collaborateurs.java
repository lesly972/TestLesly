package com.gdu.wacdo.entities;
import lombok.Data;
import java.util.Date;

@Data
public class Collaborateurs {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String datePremierEmbauche;
    private Boolean isAdministrateur;

    public Collaborateurs( Boolean isAdministrateur, String datePremierEmbauche, String prenom, String email, String nom, String motDePasse,Long id) {
        this.motDePasse = motDePasse;
        this.prenom = prenom;
        this.nom = nom;
        this.id = id;
        this.email = email;
        this.datePremierEmbauche = datePremierEmbauche;
        this.isAdministrateur = false;
    }
}
