// On précise dans quel package se trouve cette classe
package com.gdu.wacdo.controller;

// Import des classes nécessaires (DTO, entités, services, etc.)
import com.gdu.wacdo.dtos.CollaborateursDto;
import com.gdu.wacdo.dtos.FonctionsDto;
import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.entities.Fonctions;
import com.gdu.wacdo.entities.Restaurants;
import com.gdu.wacdo.service.FonctionsService;

// Pour les logs
import lombok.extern.slf4j.Slf4j;

// Annotations Spring MVC
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Cette classe est un controller qui va gérer tout ce qui concerne les "fonctions" (les postes des collaborateurs)
@Controller

// Pour pouvoir afficher des messages dans la console
@Slf4j
public class FonctionsController {

    // Service permettant de manipuler les fonctions (ajouter, modifier, récupérer...)
    public final FonctionsService fonctionsService;

    // Constructeur pour injecter le service dans le contrôleur
    public FonctionsController(FonctionsService fonctionsService) {
        this.fonctionsService = fonctionsService;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++
    // Méthode appelée quand on va sur /fonctions
    // Elle permet d’afficher la page avec le formulaire + la liste des postes
    @GetMapping("/fonctions")
    public String getHomePageFonctions(Model model){
        // On met un DTO vide pour que le formulaire fonctionne
        model.addAttribute("fonctionsDto", new FonctionsDto());

        // Et on récupère la liste de tous les postes
        model.addAttribute("fonctionsList", fonctionsService.getAllFonctions());

        return "fonctions"; // on retourne la vue "fonctions.html"
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++
    // Méthode appelée quand on soumet le formulaire d'ajout de poste
    @PostMapping("/myFormFonctions")
    public String getDataFonctions(FonctionsDto dto, Model model){
        // On sauvegarde la fonction (poste)
        Fonctions fonctionsReponse = fonctionsService.saveFonctions(dto);

        // Et on recharge la liste pour qu'elle soit à jour
        model.addAttribute("fonctionsList", fonctionsService.getAllFonctions());

        return "fonctions"; // on reste sur la même vue
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++
    // Affichage des détails d’un poste
    @GetMapping("/fonctions/{id}")
    public String getFonctionsDetails(@PathVariable("id") Long id, Model model) {
        // On récupère la fonction via son ID
        Fonctions fonctionsTrouve = fonctionsService.getFonctionsById(id);

        if (fonctionsTrouve != null) {
            // Si trouvé, on l’ajoute au modèle
            model.addAttribute("fonctions", fonctionsTrouve);
        } else {
            // Sinon on affiche une notification
            model.addAttribute("notif", "fonctions non trouvé !");
        }

        return "fonctionsDetails"; // vue pour les détails
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++
    // Formulaire de modification d’une fonction
    @GetMapping("/fonctions/edit/{id}")
    public String afficherFormModification(@PathVariable Long id, Model model) {
        // On récupère le DTO de la fonction à modifier
        FonctionsDto dto = fonctionsService.getFonctionDto(id);

        model.addAttribute("fonctionsDto", dto);
        model.addAttribute("id", id);

        return "modifierFonction"; // page de modification
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++
    // Enregistrement de la modification d'une fonction
    @PostMapping("/fonctions/edit/{id}")
    public String enregistrerModifFonction(@PathVariable Long id, @ModelAttribute("fonctionsDto") FonctionsDto dto) {
        // On applique les modifications
        fonctionsService.updateFonction(id, dto);

        // Et on redirige vers la liste des postes
        return "redirect:/fonctions";
    }

}
