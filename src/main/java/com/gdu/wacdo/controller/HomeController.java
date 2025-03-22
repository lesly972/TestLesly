package com.gdu.wacdo.controller;

import com.gdu.wacdo.dtos.RestaurantsDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    // Défini le lien à utiliser pour afficher la page web
    @GetMapping("/restaurants")

    public String getHomePage(Model model){
        model.addAttribute("restaurantsdto",new RestaurantsDto());
        model.addAttribute("restaurantList",null);
        return "restaurants";
    }

    @PostMapping("/myFormRestaurants")
    public String getDataRestaurants(RestaurantsDto dto){

        return "restaurants";
    }
}
