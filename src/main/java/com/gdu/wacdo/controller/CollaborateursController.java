package com.gdu.wacdo.controller;

import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.entities.Restaurants;
import com.gdu.wacdo.service.CollaborateursService;
import com.gdu.wacdo.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

@Controller

//Affiche les logs
@Slf4j

public class CollaborateursController {

    public final CollaborateursService collaborateursService;

    public CollaborateursController(CollaborateursService collaborateursService) {this.collaborateursService = collaborateursService;
    }


    @GetMapping("/collaborateurs/")

    public String getCollaborateursList(Model model) {

        List<Collaborateurs> collaborateursList = List.of(
                new Collaborateurs(false,new Date(),"fafa","tt@ff.com","ffff","fdfff",1L),
                new Collaborateurs(false,new Date(),"faddddfa","tadadat@ff.com","ffdadadff","fddaadfff",2L)
        );

        log.info("-------------- collaborateursList for view : {}", collaborateursList);

        model.addAttribute("collaborateursList", collaborateursList); // Pousse les infos à afficher dans le fichier html

        return "collaborateurs"; //Retour sur le fichier HTML collaborateur
    }

    @GetMapping("/collaborateurs/{id}")
// Pathvariable récup un param de l'url
  public String getCollaborateurDetails(@PathVariable("id") Long id, Model model) {

        Collaborateurs collaborateurTrouve = collaborateursService.getCollabDetails(id);
        if (collaborateurTrouve != null) {
            model.addAttribute("collaborateur", collaborateurTrouve);
        } else {
            model.addAttribute("notif", "Collaborateur non trouvé !");
        }

        return "collaborateurDetails"; // page de détails
    }
}
