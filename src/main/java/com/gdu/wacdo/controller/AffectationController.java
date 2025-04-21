// Déclaration du package où se trouve ce controller
package com.gdu.wacdo.controller;

// Importation des objets nécessaires pour faire fonctionner ce controller
import com.gdu.wacdo.dtos.AffectationDto;
import com.gdu.wacdo.entities.Affectation;
import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.entities.Restaurants;
import com.gdu.wacdo.service.AffectationService;
import com.gdu.wacdo.service.CollaborateursService;
import com.gdu.wacdo.service.FonctionsService;
import com.gdu.wacdo.service.RestaurantService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Cette classe permet de gérer tout ce qui concerne les affectations dans l’application
@Controller
public class AffectationController {

    // On injecte les services dont on aura besoin ici
    private final AffectationService affectationService;
    private final CollaborateursService collaborateursService;
    private final FonctionsService fonctionsService;
    private final RestaurantService restaurantService;

    // Constructeur pour injecter les dépendances (Spring va faire ça automatiquement)
    public AffectationController(AffectationService affectationService,
                                 CollaborateursService collaborateursService,
                                 FonctionsService fonctionsService,
                                 RestaurantService restaurantService) {
        this.affectationService = affectationService;
        this.collaborateursService = collaborateursService;
        this.fonctionsService = fonctionsService;
        this.restaurantService = restaurantService;
    }

    // Route GET pour afficher le formulaire d'affectation
    @GetMapping("/affectation")
    public String afficherFormulaireAffectation(Model model) {

        // On crée un nouvel objet vide pour le formulaire
        AffectationDto dto = new AffectationDto();

        // On ajoute les infos nécessaires pour que le formulaire ait toutes les options
        model.addAttribute("affectationDto", dto);
        model.addAttribute("collaborateurs", collaborateursService.getAllCollaborateurs());
        model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        model.addAttribute("fonctions", fonctionsService.getAllFonctions());
        model.addAttribute("affectationList", affectationService.getAllAffectations());

        // On récupère toutes les affectations actives (sans date de fin)
        List<Affectation> affectationsActives = affectationService.getAffectationsActives();

        // Test temporaire, pour voir ce qui est retourné côté console
        System.out.println(">> Affectations actives :");
        for (Affectation a : affectationsActives) {
            System.out.println(a.getCollaborateurs().getId() + "-" +
                    a.getRestaurants().getId() + "-" +
                    a.getFonctions().getId());
        }

        // On crée une liste des fonctions déjà affectées pour un combo collab + resto
        List<String> fonctionsDejaAffectees = affectationsActives.stream()
                .map(a -> a.getCollaborateurs().getId() + "-" + a.getRestaurants().getId() + "-" + a.getFonctions().getId())
                .toList();
        model.addAttribute("fonctionsDejaAffectees", fonctionsDejaAffectees);

        // Même chose mais juste le couple collaborateur + resto (sans fonction)
        List<String> combinaisonsActives = affectationsActives.stream()
                .map(a -> a.getCollaborateurs().getId() + "-" + a.getRestaurants().getId())
                .toList();
        model.addAttribute("combinaisonsActives", combinaisonsActives);

        // Pour afficher ceux qui n'ont aucune affectation active
        List<Collaborateurs> sansAffectation = affectationService.getCollaborateursSansAffectation();
        model.addAttribute("collaborateursSansAffectation", sansAffectation);

        return "affectation"; // vue thymeleaf
    }

    // Route POST pour créer une nouvelle affectation
    @PostMapping("/affectation")
    public String creerAffectation(@ModelAttribute AffectationDto dto, Model model) {
        try {
            // On tente de créer une nouvelle affectation
            affectationService.creerAffectation(dto);
            return "redirect:/collaborateurs"; // une fois créée, on redirige vers les collabs
        } catch (IllegalStateException e) {
            // En cas d’erreur métier (ex: déjà affecté), on affiche un message
            model.addAttribute("messageErreur", e.getMessage());
            model.addAttribute("affectationDto", dto);
            model.addAttribute("collaborateurs", collaborateursService.getAllCollaborateurs());
            model.addAttribute("restaurants", restaurantService.getAllRestaurants());
            model.addAttribute("fonctions", fonctionsService.getAllFonctions());
            model.addAttribute("affectationList", affectationService.getAllAffectations());

            // Et on remet à jour les combinaisons pour éviter d’avoir des doublons
            List<Affectation> affectationsActives = affectationService.getAffectationsActives();
            List<String> combinaisonsActives = affectationsActives.stream()
                    .map(a -> a.getCollaborateurs().getId() + "-" + a.getRestaurants().getId())
                    .toList();
            model.addAttribute("combinaisonsActives", combinaisonsActives);

            return "affectation";
        }
    }

    // Route GET pour afficher les détails d’une affectation spécifique
    @GetMapping("/affectation/{id}")
    public String afficherDetailAffectation(@PathVariable Long id, Model model) {
        Affectation affectation = affectationService.getAffectationById(id);

        // Si rien trouvé, on retourne un message
        if (affectation == null) {
            model.addAttribute("notif", "Affectation non trouvée !");
            return "redirect:/affectation";
        }

        model.addAttribute("affectation", affectation);
        return "affectationDetails";
    }

    // Route GET pour afficher un formulaire de modification d'affectation
    @GetMapping("/affectation/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        Affectation affectation = affectationService.getAffectationById(id);

        if (affectation == null) {
            model.addAttribute("notif", "Affectation non trouvée !");
            return "redirect:/collaborateurs";
        }

        // Pré-remplissage du formulaire avec les valeurs actuelles
        AffectationDto dto = new AffectationDto();
        dto.setCollaborateurId(affectation.getCollaborateurs().getId());
        dto.setRestaurantId(affectation.getRestaurants().getId());
        dto.setFonctionId(affectation.getFonctions().getId());
        dto.setDateDebut(affectation.getDateDebut());
        dto.setDateFin(affectation.getDateFin());

        model.addAttribute("affectationId", affectation.getId());
        model.addAttribute("affectationDto", dto);
        model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        model.addAttribute("fonctions", fonctionsService.getAllFonctions());

        List<Restaurants> restaurantsAffectes = affectationService
                .getRestaurantsAffectesParCollaborateur(dto.getCollaborateurId());
        model.addAttribute("restaurantsAffectes", restaurantsAffectes);

        return "modifierAffectation";
    }

    // Route POST pour enregistrer une modification d’affectation
    @PostMapping("/affectation/modifier/{id}")
    public String modifierAffectation(@PathVariable Long id,
                                      @ModelAttribute AffectationDto dto,
                                      Model model) {
        try {
            affectationService.modifierAffectation(id, dto);
            return "redirect:/collaborateurs/" + dto.getCollaborateurId(); // vers la fiche du collab
        } catch (IllegalStateException e) {
            model.addAttribute("messageErreur", e.getMessage());
            model.addAttribute("affectationId", id);
            model.addAttribute("affectationDto", dto);
            model.addAttribute("fonctions", fonctionsService.getAllFonctions());

            Restaurants restaurant = restaurantService.getRestaurantById(dto.getRestaurantId());
            if (restaurant != null) {
                model.addAttribute("restaurantNom", restaurant.getNom());
            }

            return "modifierAffectation";
        }
    }

}
