// Le package où se trouve ce fichier DTO (objet de transfert de données)
package com.gdu.wacdo.dtos;

// Pour le bon format des dates, notamment avec les champs <input type="date"> dans le formulaire
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

// Cette classe va servir à représenter un collaborateur de façon simplifiée.
// Elle est utilisée pour le formulaire HTML, donc on ne met pas ici d’objets liés (genre Restaurant ou Affectation)
// C’est plus "léger" que l’entité réelle et plus sécurisé aussi (on évite d’exposer des données sensibles ou inutiles).

public class CollaborateursDto {

    // Nom du collaborateur
    private String nom;

    // Prénom
    private String prenom;

    // Email utilisé pour l’identification / connexion
    private String email;

    // Le mot de passe (normalement il sera crypté après dans le service avant d'être stocké)
    private String motDePasse;

    // Date de la première embauche du collaborateur
    // L’annotation est là pour que Spring puisse parser correctement la date venant du formulaire HTML
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datePremierEmbauche;

    // Boolean qui dit si la personne a le rôle "admin"
    private Boolean administrateur;

    // Constructeur avec tous les champs — utile quand on veut créer le DTO avec toutes les données d’un coup
    public CollaborateursDto(String nom, String prenom, String email, String motDePasse, Date datePremierEmbauche, Boolean administrateur) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.datePremierEmbauche = datePremierEmbauche;
        this.administrateur = administrateur;
    }

    // Constructeur vide obligatoire — sinon Spring ne pourra pas créer l’objet automatiquement dans le binding
    public CollaborateursDto() {}

    // ------------------- GETTERS & SETTERS -----------------------
    // Ces méthodes permettent à Spring (et nous aussi) de lire/modifier les valeurs des champs

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
