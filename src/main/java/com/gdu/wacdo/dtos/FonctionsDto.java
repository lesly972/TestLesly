// Le package dans lequel se trouve ce fichier DTO
package com.gdu.wacdo.dtos;

// Ce DTO est utilisé pour gérer les formulaires liés aux fonctions (postes) dans l’appli.
// Il est volontairement très simple : juste le nom du poste.

public class FonctionsDto {

    // C’est le seul champ : le nom du poste qu’on veut créer ou modifier.
    // Exemple : "Serveur", "Cuisinier", "Manager", etc.
    private String nomDuPoste;

    // Constructeur qui permet de créer un objet FonctionsDto avec un nom de poste déjà rempli.
    // Ça peut être utile dans le contrôleur ou dans les tests.
    public FonctionsDto(String nomDuPoste){
        this.nomDuPoste = nomDuPoste;
    }

    // Constructeur vide obligatoire ! Sinon Spring peut pas instancier l'objet dans les formulaires
    public FonctionsDto(){
    }

    // Getter = méthode pour récupérer la valeur du nomDuPoste
    public String getNomDuPoste() {
        return nomDuPoste;
    }

    // Setter = méthode pour modifier la valeur du nomDuPoste
    public void setNomDuPoste(String nomDuPoste) {
        this.nomDuPoste = nomDuPoste;
    }
}
