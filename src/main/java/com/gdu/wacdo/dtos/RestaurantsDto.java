// Le package du projet où se trouve le DTO des restaurants
package com.gdu.wacdo.dtos;

// Ce DTO (Data Transfer Object) permet de transférer les données
// entre le formulaire HTML (ou autre vue) et le backend concernant un restaurant.
// C'est une sorte de "boîte de données" temporaire avant de les transformer en entité réelle.

public class RestaurantsDto {

    // Le nom du restaurant, genre "La Taverne", "Chez Paul", etc.
    private String nom;

    // Adresse complète du resto (n° et rue)
    private String adresse;

    // Code postal : 75001, 93200, 27120...
    private String codePostal;

    // Ville où se situe le resto : "Paris", "Évreux", "Lille"...
    private String ville;

    // Constructeur avec tous les champs (sauf l'id qui sert à rien ici car ce n’est pas une entité)
    public RestaurantsDto(String ville, String codePostal, String adresse, String nom, Long id) {
        this.ville = ville;
        this.codePostal = codePostal;
        this.adresse = adresse;
        this.nom = nom;
        // L’id est passé mais pas stocké (probablement une erreur ou restes d’un test)
    }

    // Constructeur vide, ultra important ! Il est utilisé par Spring
    // quand on remplit un formulaire pour créer un resto.
    public RestaurantsDto() {
    }

    // Getter pour récupérer le nom du resto
    public String getNom() {
        return nom;
    }

    // Setter pour changer le nom du resto
    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter pour l’adresse complète
    public String getAdresse() {
        return adresse;
    }

    // Setter pour l’adresse
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    // Getter du code postal
    public String getCodePostal() {
        return codePostal;
    }

    // Setter du code postal
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    // Getter de la ville
    public String getVille() {
        return ville;
    }

    // Setter de la ville
    public void setVille(String ville) {
        this.ville = ville;
    }
}
