// Déclaration du package où se trouve cette classe DTO (Data Transfer Object)
package com.gdu.wacdo.dtos;

// Importation des entités (même si elles ne sont pas utilisées ici)
import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.entities.Fonctions;
import com.gdu.wacdo.entities.Restaurants;

// Import pour le format de la date côté formulaire HTML
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

// Cette classe va servir à transporter les infos d'une affectation entre le formulaire et le backend.
// On n'utilise pas directement l'entité "Affectation" pour ne pas exposer tout, surtout les relations complexes.
// Donc ce DTO ne contient que les infos de base : les ID et les dates.
public class AffectationDto {

    // L’ID du collaborateur sélectionné dans le formulaire
    private Long collaborateurId;

    // L’ID du restaurant où on veut l’affecter
    private Long restaurantId;

    // L’ID du poste (fonction) attribué
    private Long fonctionId;

    // La date de début de l’affectation (annotée pour que Spring comprenne le format du champ HTML)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDebut;

    // Optionnel : la date de fin (si le contrat est terminé par exemple)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFin;

    // Constructeur vide obligatoire pour que Spring puisse instancier l’objet automatiquement
    public AffectationDto() {}

    // Tous les getters & setters ci-dessous permettent d'accéder et modifier les champs du DTO
    // Ils sont utilisés par Spring lors du binding du formulaire <form>

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Long getFonctionId() {
        return fonctionId;
    }

    public void setFonctionId(Long fonctionId) {
        this.fonctionId = fonctionId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getCollaborateurId() {
        return collaborateurId;
    }

    public void setCollaborateurId(Long collaborateurId) {
        this.collaborateurId = collaborateurId;
    }
}
