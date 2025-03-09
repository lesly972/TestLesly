package com.gdu.wacdo.controller;

import com.gdu.wacdo.entities.Restaurants;
import com.gdu.wacdo.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.ArrayList;
import java.util.List;


@Controller

//Affiche les logs
@Slf4j
public class RestaurantController {
    public final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants/")
    public String getRestaurantsList(Model model) {

        List<Restaurants> restaurantsList = List.of(
                new Restaurants("Defense","92000","1 rue blabla","Wacdo Defense",1L) ,
                new Restaurants("etrepagny","27150","31 rue saint","Wacdo Normandie",2L)
        );

        model.addAttribute("notif", "cet attribut est ok"); // Pousse les infos à afficher dans le fichier html

        log.info("-------------- restaurantList for view : {}", restaurantsList);

        model.addAttribute("restaurantList", restaurantsList); // Pousse les infos à afficher dans le fichier html

        return "restaurants"; //Retour sur le fichier HTML restaurants
    }

    @GetMapping("/restaurant/{id}")
    public String getRestaurantDetails(@PathVariable("id") Long id, Model model){

       Restaurants restaurants = restaurantService.getDetails(id);

       if(restaurants != null){

           model.addAttribute("restaurant", restaurants);
       }
       else {
           model.addAttribute("restaurant", new Restaurants(null,null,null,null,null));
           model.addAttribute("notif", "Aucun restaurant trouvé");
       }

       return "restaurantDetails";
    }
}
