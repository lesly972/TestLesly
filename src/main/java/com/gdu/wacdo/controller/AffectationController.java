package com.gdu.wacdo.controller;

import com.gdu.wacdo.dtos.AffectationDto;
import com.gdu.wacdo.service.AffectationService;
import com.gdu.wacdo.service.CollaborateursService;
import com.gdu.wacdo.service.FonctionsService;
import com.gdu.wacdo.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/affectation")
    public String afficherFormulaireAffectation(Model model) {
        model.addAttribute("affectationDto", new AffectationDto());
        model.addAttribute("collaborateurs", collaborateursService.getAllCollaborateurs());
        model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        model.addAttribute("fonctions", fonctionsService.getAllFonctions());
        return "affectation"; // ou affectation.html
    }

    @PostMapping("/affectation")
    public String creerAffectation(@ModelAttribute AffectationDto dto) {
        affectationService.creerAffectation(dto);
        return "redirect:/collaborateurs";
    }
}