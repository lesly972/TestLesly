// Le package dans lequel se trouve le controller
package com.gdu.wacdo.controller;

// Importation des objets dont on aura besoin pour gérer les collaborateurs
import com.gdu.wacdo.dtos.CollaborateursDto;
import com.gdu.wacdo.entities.Affectation;
import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.entities.Restaurants;
import com.gdu.wacdo.service.AffectationService;
import com.gdu.wacdo.service.CollaborateursService;
import com.gdu.wacdo.service.FonctionsService;
import com.gdu.wacdo.service.RestaurantService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

// On déclare cette classe comme un controller Spring MVC
@Controller

// Permet d’afficher des logs dans la console
@Slf4j
public class CollaborateursController {

    // On déclare tous les services nécessaires
    public final CollaborateursService collaborateursService;
    private final AffectationService affectationService;
    private final RestaurantService restaurantService;
    private final FonctionsService fonctionsService;

    // Constructeur, il sert à injecter tous les services dans ce controller
    public CollaborateursController(CollaborateursService collaborateursService, AffectationService affectationService, RestaurantService restaurantService, FonctionsService fonctionsService) {
        this.collaborateursService = collaborateursService;
        this.affectationService = affectationService;
        this.restaurantService = restaurantService;
        this.fonctionsService = fonctionsService;
    }

    // ++++++++++++++++++++ Affichage de la page principale collaborateurs ++++++++++++++++++++
    @GetMapping("/collaborateurs")
    public String getHomePageCollaborateurs(Model model) {
        // On envoie un DTO vide pour le formulaire d'ajout
        model.addAttribute("collaborateursDto", new CollaborateursDto());

        // Et on envoie la liste de tous les collaborateurs pour les afficher
        model.addAttribute("collaborateursList", collaborateursService.getAllCollaborateurs());

        // Renvoi vers la vue `collaborateurs.html`
        return "collaborateurs";
    }

    // ++++++++++++++++++++ Traitement du formulaire d'ajout de collaborateur ++++++++++++++++++++
    @PostMapping("/myFormCollaborateurs")
    public String getDataCollaborateurs(CollaborateursDto dto, Model model){
        // On sauvegarde le collaborateur en base
        Collaborateurs collaborateursReponse = collaborateursService.saveCollaborateurs(dto);

        // On recharge la liste pour afficher à jour
        model.addAttribute("collaborateursList", collaborateursService.getAllCollaborateurs());

        return "collaborateurs"; // on reste sur la même page
    }

    // ++++++++++++++++++++ Affichage des détails d’un collaborateur ++++++++++++++++++++
    @GetMapping("/collaborateurs/{id}")
    public String getCollaborateurDetails(@PathVariable("id") Long id, Model model) {

        // On récupère le collaborateur via son id
        Collaborateurs collaborateurTrouve = collaborateursService.getCollabDetails(id);

        // Si trouvé, on ajoute ses infos au modèle
        if (collaborateurTrouve != null) {
            model.addAttribute("collaborateur", collaborateurTrouve);

            // On récupère tout son historique d'affectation
            List<Affectation> historique = affectationService.getHistoriqueAffectationsParCollaborateur(id);
            model.addAttribute("historique", historique);
        } else {
            // Sinon on affiche une notif d’erreur
            model.addAttribute("notif", "Collaborateur non trouvé !");
        }

        // On récupère sa dernière affectation (la plus récente)
        Affectation affectation = affectationService.getDerniereAffectation(id);
        model.addAttribute("affectation", affectation);

        return "collaborateurDetails"; // Vue associée
    }

    // ++++++++++++++++++++ Formulaire de modification d’un collaborateur ++++++++++++++++++++
    @GetMapping("/collaborateurs/edit/{id}")
    public String afficherFormModification(@PathVariable Long id, Model model) {
        // On récupère le collaborateur et on le convertit en DTO pour pré-remplir le formulaire
        CollaborateursDto dto = collaborateursService.getCollaborateurDto(id);

        model.addAttribute("collaborateursDto", dto);
        model.addAttribute("id", id);

        // Si jamais on veut ajouter d'autres données dans le formulaire, comme les fonctions ou restos, c’est là
        // model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        // model.addAttribute("fonctions", fonctionsService.getAllFonctions());

        return "modifierCollaborateur";
    }

    // ++++++++++++++++++++ Enregistrement de la modif du collaborateur ++++++++++++++++++++
    @PostMapping("/collaborateurs/edit/{id}")
    public String enregistrerModifications(@PathVariable Long id, @ModelAttribute("collaborateursDto") CollaborateursDto dto) {
        // On appelle le service pour sauvegarder les modifs
        collaborateursService.updateCollaborateur(id, dto);

        // Redirection vers la page liste des collaborateurs
        return "redirect:/collaborateurs";
    }

}
