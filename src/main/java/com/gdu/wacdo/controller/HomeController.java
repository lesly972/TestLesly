package com.gdu.wacdo.controller;

import com.gdu.wacdo.dtos.RestaurantsDto;
import com.gdu.wacdo.entities.Restaurants;
import com.gdu.wacdo.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

   private final RestaurantService restaurantService;

    public HomeController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    // Défini le lien à utiliser pour afficher la page web
    @GetMapping("/restaurants")

    public String getHomePage(Model model){
        model.addAttribute("restaurantsdto",new RestaurantsDto());
        model.addAttribute("restaurantList",restaurantService.getAllRestaurants());
        return "restaurants";
    }

    @PostMapping("/myFormRestaurants")
    public String getDataRestaurants(RestaurantsDto dto, Model model){

        Restaurants restaurantsResponse = restaurantService.saveRestaurants(dto);

        //Besoin d'afficher directement la liste une fois retourné ou ajout d'un retaurant
        model.addAttribute("restaurantList",restaurantService.getAllRestaurants());
        return "restaurants";
    }
}
