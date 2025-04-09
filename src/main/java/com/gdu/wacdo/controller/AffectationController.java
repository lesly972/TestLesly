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




}