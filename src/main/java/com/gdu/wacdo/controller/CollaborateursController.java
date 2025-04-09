package com.gdu.wacdo.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Controller

//Affiche les logs
@Slf4j

public class CollaborateursController {

    public final CollaborateursService collaborateursService;
    private final AffectationService affectationService;
    private final RestaurantService restaurantService;
    private final FonctionsService fonctionsService;

    public CollaborateursController(CollaborateursService collaborateursService, AffectationService affectationService, RestaurantService restaurantService, FonctionsService fonctionsService) {
        this.collaborateursService = collaborateursService;
        this.affectationService = affectationService;
        this.restaurantService = restaurantService;
        this.fonctionsService = fonctionsService;
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++Gestion de la page collaborateur avec le formulaire et la liste des collaborateurs++++++++++++++++++++++++++++++++++++++++++++
    @GetMapping("/collaborateurs")

    public String getHomePageCollaborateurs(Model model) {
        model.addAttribute("collaborateursDto", new CollaborateursDto());
        model.addAttribute("collaborateursList",collaborateursService.getAllCollaborateurs());

        return "collaborateurs"; //Retour sur le fichier HTML collaborateur
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++Gestion du formulaire colaborateurs++++++++++++++++++++++++++++++++++++++++++++
    @PostMapping("/myFormCollaborateurs")
    public String getDataCollaborateurs(CollaborateursDto dto, Model model){
        Collaborateurs collaborateursReponse = collaborateursService.saveCollaborateurs(dto);

        //affiche directement la liste une fois retourné ou ajout d'un collaborateur
        model.addAttribute("collaborateursList", collaborateursService.getAllCollaborateurs());
        return "collaborateurs";
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++Gestion des détails des collaborateurs++++++++++++++++++++++++++++++++++++++++++++
    @GetMapping("/collaborateurs/{id}")
    // Pathvariable récup un param de l'url
    public String getCollaborateurDetails(@PathVariable("id") Long id, Model model) {

        Collaborateurs collaborateurTrouve = collaborateursService.getCollabDetails(id);
        if (collaborateurTrouve != null) {
            model.addAttribute("collaborateur", collaborateurTrouve);
        } else {
            model.addAttribute("notif", "Collaborateur non trouvé !");
        }

        //Afffiche les affectation du collaborateur ( restaurant et poste)
        Affectation affectation = affectationService.getAffectationById(id);
        model.addAttribute("affectation", affectation);

        return "collaborateurDetails"; // page de détails
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++Modification des détails des collaborateurs++++++++++++++++++++++++++++++++++++++++++++
    @GetMapping("/collaborateurs/edit/{id}")
    public String afficherFormModification(@PathVariable Long id, Model model) {
        CollaborateursDto dto = collaborateursService.getCollaborateurDto(id);
        model.addAttribute("collaborateursDto", dto);
        model.addAttribute("id", id);
        //model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        //model.addAttribute("fonctions", fonctionsService.getAllFonctions());
        return "modifierCollaborateur";
    }

    @PostMapping("/collaborateurs/edit/{id}")
    public String enregistrerModifications(@PathVariable Long id, @ModelAttribute("collaborateursDto") CollaborateursDto dto) {
        collaborateursService.updateCollaborateur(id, dto);
        return "redirect:/collaborateurs";
    }


}
