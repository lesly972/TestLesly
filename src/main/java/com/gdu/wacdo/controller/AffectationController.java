package com.gdu.wacdo.controller;

import com.gdu.wacdo.dtos.AffectationDto;
import com.gdu.wacdo.entities.Affectation;
import com.gdu.wacdo.service.AffectationService;
import com.gdu.wacdo.service.CollaborateursService;
import com.gdu.wacdo.service.FonctionsService;
import com.gdu.wacdo.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AffectationController {

    private final AffectationService affectationService;
    private final CollaborateursService collaborateursService;
    private final FonctionsService fonctionsService;
    private final RestaurantService restaurantService;

    public AffectationController(AffectationService affectationService,CollaborateursService collaborateursService,FonctionsService fonctionsService,RestaurantService restaurantService) {
        this.affectationService = affectationService;
        this.collaborateursService = collaborateursService;
        this.fonctionsService = fonctionsService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/affectation") //Afficher le formulaire
    public String afficherFormulaireAffectation(Model model) {
        model.addAttribute("affectationDto", new AffectationDto());
        model.addAttribute("collaborateurs", collaborateursService.getAllCollaborateurs());
        model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        model.addAttribute("fonctions", fonctionsService.getAllFonctions());
        model.addAttribute("affectationList",affectationService.getAllAffectations());

        return "affectation"; // ou affectation.html
    }

    @PostMapping("/affectation")
    public String creerAffectation(@ModelAttribute AffectationDto dto) {
        affectationService.creerAffectation(dto);
        return "redirect:/collaborateurs";
    }


    @GetMapping("/affectation/{id}")
    public String afficherDetailAffectation(@PathVariable Long id, Model model) {
        Affectation affectation = affectationService.getAffectationById(id);
        if (affectation == null) {
            model.addAttribute("notif", "Affectation non trouvée !");
            return "redirect:/affectation";
        }
        model.addAttribute("affectation", affectation);
        return "affectationDetails";
    }

    @GetMapping("/affectation/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        Affectation affectation = affectationService.getAffectationById(id);
        if (affectation == null) {
            model.addAttribute("notif", "Affectation non trouvée !");
            return "redirect:/collaborateurs";
        }

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

        return "modifierAffectation"; // nouveau fichier HTML
    }

    @PostMapping("/affectation/modifier/{id}")
    public String modifierAffectation(@PathVariable Long id, @ModelAttribute AffectationDto dto) {
        affectationService.modifierAffectation(id, dto);
        return "redirect:/collaborateurs/" + dto.getCollaborateurId();
    }



}