// Le package où se trouve cette entité (on l’a rangé dans le dossier "entities")
package com.gdu.wacdo.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Cette annotation @Entity indique que cette classe va correspondre à une table dans la base de données.
@Entity
public class Collaborateurs {

    // Clé primaire générée automatiquement (auto-incrémentée)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nom du collaborateur (ex : Dupont)
    private String nom;

    // Prénom du collaborateur (ex : Jean)
    private String prenom;

    // Adresse mail (sert aussi d’identifiant pour se connecter à l’appli)
    private String email;

    // Mot de passe (hashé grâce à Spring Security dans le service)
    private String motDePasse;

    // Date d'embauche du collaborateur (on pourrait l'utiliser pour calculer l’ancienneté par exemple)
    private Date datePremierEmbauche;

    // Booléen qui dit si l'utilisateur est un administrateur ou pas
    private Boolean isAdministrateur;

    // Liste des affectations du collaborateur (relation 1-N)
    // mappedBy = "collaborateurs" => correspond au nom de la propriété dans la classe Affectation
    @OneToMany(mappedBy = "collaborateurs")
    private List<Affectation> affectations = new ArrayList<>();

    // Constructeur personnalisé, utilisé pour créer un objet avec ses infos de base
    public Collaborateurs(String nom, String prenom, String email, String motDePasse, Date datePremierEmbauche, Boolean isAdministrateur) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.datePremierEmbauche = datePremierEmbauche;
        this.isAdministrateur = isAdministrateur;
    }

    // Constructeur vide obligatoire pour Hibernate (framework qui crée les objets à notre place)
    public Collaborateurs() {}

    // ---------------------------
    // Getters et Setters : ils permettent de lire ou modifier les propriétés
    // (très utiles pour les formulaires Thymeleaf ou pour les services)
    // ---------------------------

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
        this.isAdministrateur = administrateur;
    }

    // La liste des affectations n’est pas exposée ici,
    // mais elle est gérée par Hibernate via la relation OneToMany
}
