// Déclaration du package
package com.gdu.wacdo.controller;

// Importation des DTO, entités et services nécessaires
import com.gdu.wacdo.dtos.RestaurantsDto;
import com.gdu.wacdo.entities.Affectation;
import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.entities.Restaurants;
import com.gdu.wacdo.service.AffectationService;
import com.gdu.wacdo.service.RestaurantService;

// Annotation pour pouvoir écrire des logs dans la console (log.info etc.)
import lombok.extern.slf4j.Slf4j;

// Annotations Spring pour déclarer un controller
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// On indique que cette classe est un Controller Spring
@Controller

// On active les logs (même si pas trop utilisé ici)
@Slf4j
public class RestaurantController {

    // On injecte les services nécessaires
    private final RestaurantService restaurantService;
    private final AffectationService affectationService;

    // Constructeur pour injection automatique
    public RestaurantController(RestaurantService restaurantService, AffectationService affectationService) {
        this.restaurantService = restaurantService;
        this.affectationService = affectationService;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++
    // Affichage de la page principale des restaurants
    @GetMapping("/restaurants")
    public String getHomePage(Model model){
        // On ajoute un DTO vide pour le formulaire
        model.addAttribute("restaurantsDto", new RestaurantsDto());

        // On récupère et affiche la liste de tous les restaurants
        model.addAttribute("restaurantList", restaurantService.getAllRestaurants());

        // On retourne la vue HTML "restaurants.html"
        return "restaurants";
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++
    // Traitement du formulaire pour ajouter un nouveau restaurant
    @PostMapping("/myFormRestaurants")
    public String getDataRestaurants(RestaurantsDto dto, Model model){

        // Enregistre le restaurant via le service
        Restaurants restaurantsResponse = restaurantService.saveRestaurants(dto);

        // Recharge la liste des restaurants mise à jour
        model.addAttribute("restaurantList", restaurantService.getAllRestaurants());

        // Retourne sur la même page (pour réafficher le formulaire + la liste)
        return "restaurants";
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++
    // Affichage des détails d’un restaurant (via son ID)
    @GetMapping("/restaurants/{id}")
    public String getRestaurantDetails(@PathVariable("id") Long id, Model model) {

        // On récupère le restaurant concerné
        Restaurants restaurantTrouve = restaurantService.getRestaurantById(id);

        // Et toutes les affectations (les collaborateurs qui bossent dedans)
        List<Affectation> affectations = affectationService.getAffectationsByRestaurantId(id);

        // Si trouvé, on envoie les données à la vue
        if (restaurantTrouve != null) {
            model.addAttribute("restaurant", restaurantTrouve);
            model.addAttribute("affectations", affectations);
        } else {
            // Sinon on envoie une notif
            model.addAttribute("notif", "Restaurant non trouvé !");
        }

        return "restaurantDetails"; // vue HTML
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++
    // Affichage du formulaire de modification d’un resto
    @GetMapping("/restaurants/edit/{id}")
    public String afficherFormModification(@PathVariable Long id, Model model) {

        // On récupère les infos existantes du restaurant
        RestaurantsDto dto = restaurantService.getRestaurantDto(id);

        // On ajoute dans le modèle
        model.addAttribute("restaurantsDto", dto);
        model.addAttribute("id", id);

        return "modifierRestaurant"; // on retourne vers la page de modif
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++
    // Traitement du formulaire de modification d’un resto
    @PostMapping("/restaurants/edit/{id}")
    public String enregistrerModifRestaurant(@PathVariable Long id, @ModelAttribute("restaurantsDto") RestaurantsDto dto) {

        // Mise à jour via le service
        restaurantService.updateRestaurant(id, dto);

        // Et redirection vers la liste
        return "redirect:/restaurants";
    }
}
