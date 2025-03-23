package com.gdu.wacdo.controller;

import com.gdu.wacdo.dtos.RestaurantsDto;
import com.gdu.wacdo.entities.Restaurants;
import com.gdu.wacdo.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller

//Affiche les logs
@Slf4j
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    // Défini le lien à utiliser pour afficher la page web
    @GetMapping("/restaurants")
    public String getHomePage(Model model){
        model.addAttribute("restaurantsDto",new RestaurantsDto());
        model.addAttribute("restaurantList",restaurantService.getAllRestaurants());

        System.out.println("Chargement de la page avec DTO vide");

        return "restaurants";
    }

    @PostMapping("/myFormRestaurants")
    public String getDataRestaurants(RestaurantsDto dto, Model model){

        log.info(">> DONNÉES REÇUES : {}" , dto.getVille());

        Restaurants restaurantsResponse = restaurantService.saveRestaurants(dto);

        //affiche directement la liste une fois retourné ou ajout d'un retaurant
        model.addAttribute("restaurantList",restaurantService.getAllRestaurants());
        return "restaurants";
    }


}
