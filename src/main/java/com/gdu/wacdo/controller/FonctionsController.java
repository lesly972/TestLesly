package com.gdu.wacdo.controller;

import com.gdu.wacdo.dtos.CollaborateursDto;
import com.gdu.wacdo.dtos.FonctionsDto;
import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.entities.Fonctions;
import com.gdu.wacdo.entities.Restaurants;
import com.gdu.wacdo.service.FonctionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j

public class FonctionsController {

    public final FonctionsService fonctionsService;

    public FonctionsController(FonctionsService fonctionsService) {
        this.fonctionsService = fonctionsService;
    }

    @GetMapping("/fonctions")

    public String getHomePageFonctions(Model model){
        model.addAttribute("fonctionsDto", new FonctionsDto());
        model.addAttribute("fonctionsList",fonctionsService.getAllFonctions());

        return "fonctions";
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++Gestion du formulaire fonction++++++++++++++++++++++++++++++++++++++++++++
    @PostMapping("/myFormFonctions")
    public String getDataFonctions(FonctionsDto dto, Model model){
        Fonctions fonctionsReponse = fonctionsService.saveFonctions(dto);

        //affiche directement la liste une fois retourné ou ajout d'un poste
        model.addAttribute("fonctionsList", fonctionsService.getAllFonctions());
        return "fonctions";
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++Gestion des détails du nom du post++++++++++++++++++++++++++++++++++++++++++++
    @GetMapping("/fonctions/{id}")
    public String getFonctionsDetails(@PathVariable("id") Long id, Model model) {
        Fonctions fonctionsTrouve = fonctionsService.getFonctionsById(id);

        if (fonctionsTrouve != null) {
            model.addAttribute("fonctions", fonctionsTrouve);
        } else {
            model.addAttribute("notif", "fonctions non trouvé !");
        }

        return "fonctionsDetails";
    }

    @GetMapping("/fonctions/edit/{id}")
    public String afficherFormModification(@PathVariable Long id, Model model) {
        FonctionsDto dto = fonctionsService.getFonctionDto(id);
        model.addAttribute("fonctionsDto", dto);
        model.addAttribute("id", id);
        return "modifierFonction";
    }

    @PostMapping("/fonctions/edit/{id}")
    public String enregistrerModifFonction(@PathVariable Long id, @ModelAttribute("fonctionsDto") FonctionsDto dto) {
        fonctionsService.updateFonction(id, dto);
        return "redirect:/fonctions";
    }

}
