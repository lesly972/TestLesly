package com.gdu.wacdo.controller;

import com.gdu.wacdo.dtos.RestaurantsDto;
import com.gdu.wacdo.entities.Affectation;
import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.entities.Restaurants;
import com.gdu.wacdo.service.AffectationService;
import com.gdu.wacdo.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller

//Affiche les logs
@Slf4j
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final AffectationService affectationService;

    public RestaurantController(RestaurantService restaurantService, AffectationService affectationService) {
        this.restaurantService = restaurantService;
        this.affectationService = affectationService;
    }

    // Défini le lien à utiliser pour afficher la page web
    @GetMapping("/restaurants")
    public String getHomePage(Model model){
        model.addAttribute("restaurantsDto",new RestaurantsDto());
        model.addAttribute("restaurantList",restaurantService.getAllRestaurants());

       // System.out.println("Chargement de la page avec DTO vide");

        return "restaurants";
    }

    @PostMapping("/myFormRestaurants")
    public String getDataRestaurants(RestaurantsDto dto, Model model){

       // log.info(">> DONNÉES REÇUES : {}" , dto.getVille());

        Restaurants restaurantsResponse = restaurantService.saveRestaurants(dto);

        //affiche directement la liste une fois retourné ou ajout d'un retaurant
        model.addAttribute("restaurantList",restaurantService.getAllRestaurants());
        return "restaurants";
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++Gestion des détails du restaurant++++++++++++++++++++++++++++++++++++++++++++
    @GetMapping("/restaurants/{id}")
    public String getRestaurantDetails(@PathVariable("id") Long id, Model model) {
        Restaurants restaurantTrouve = restaurantService.getRestaurantById(id);
        List<Affectation> affectations = affectationService.getAffectationsByRestaurantId(id);

        if (restaurantTrouve != null) {
            model.addAttribute("restaurant", restaurantTrouve);
            model.addAttribute("affectations", affectations);

        } else {
            model.addAttribute("notif", "Restaurant non trouvé !");
        }

        return "restaurantDetails";
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++Gestion des modification des détails du restaurant++++++++++++++++++++++++++++++++++++++++++++
    @GetMapping("/restaurants/edit/{id}")
    public String afficherFormModification(@PathVariable Long id, Model model) {
        RestaurantsDto dto = restaurantService.getRestaurantDto(id);
        model.addAttribute("restaurantsDto", dto);
        model.addAttribute("id", id);
        return "modifierRestaurant";
    }

    @PostMapping("/restaurants/edit/{id}")
    public String enregistrerModifRestaurant(@PathVariable Long id, @ModelAttribute("restaurantsDto") RestaurantsDto dto) {
        restaurantService.updateRestaurant(id, dto);
        return "redirect:/restaurants";
    }


}
