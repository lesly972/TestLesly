package com.gdu.wacdo.service;

import com.gdu.wacdo.dtos.RestaurantsDto;
import com.gdu.wacdo.entities.Restaurants;
import com.gdu.wacdo.repositories.RestaurantsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j

public class RestaurantService {

    private final RestaurantsRepository restaurantsRepository;

    public RestaurantService(RestaurantsRepository restaurantsRepository) {
        this.restaurantsRepository = restaurantsRepository;
    }

    public Restaurants saveRestaurants(RestaurantsDto dto){
        //crétion d'une antité vide
        //log.info(">> DONNÉES REÇUES dans service : {}" , dto.getVille());
        Restaurants restaurants = new Restaurants(dto.getVille(),dto.getCodePostal(),dto.getAdresse(),dto.getNom());
        try {
            //log.info(">> DONNÉES REÇUES dans service : {}" , restaurants.getVille());
            //Restaurants restaurantsReponse = restaurantsRepository.save(restaurants);
            return restaurantsRepository.save(restaurants);

            //Si la sauvegarde c'est bien passé on retourne
           // return restaurantsReponse;

        } catch (Exception e){
            //Affiche l'exeption
            System.out.println(e);
        }

        return new Restaurants();
    }

    // methode afin de revoyer la liste des restaurants présent en BdD
    public List<Restaurants> getAllRestaurants(){

        return restaurantsRepository.findAll();
    }

    // methode qui permet de récupérer un restaurant en fonction de son ID dans la BdD
    public Restaurants getRestaurantById(Long id) {
        return restaurantsRepository.findById(id).orElse(null);
    }

    public Restaurants updateRestaurant(Long id, RestaurantsDto dto) {
        Restaurants restaurant = restaurantsRepository.findById(id).orElse(null);
        if (restaurant == null) return null;

        restaurant.setNom(dto.getNom());
        restaurant.setAdresse(dto.getAdresse());
        restaurant.setCodePostal(dto.getCodePostal());
        restaurant.setVille(dto.getVille());

        return restaurantsRepository.save(restaurant);
    }

    public RestaurantsDto getRestaurantDto(Long id) {
        Restaurants restaurant = restaurantsRepository.findById(id).orElse(null);
        if (restaurant == null) return null;

        RestaurantsDto dto = new RestaurantsDto();
        dto.setNom(restaurant.getNom());
        dto.setAdresse(restaurant.getAdresse());
        dto.setCodePostal(restaurant.getCodePostal());
        dto.setVille(restaurant.getVille());

        return dto;
    }



    // public Restaurants getDetails (Long id) {
      //  List<Restaurants> restaurantsList = List.of(
              //  new Restaurants("Defense","92000","1 rue blabla","Wacdo Defense",3L) , // id 3 et non 1 pour les test et affichage de page non trouvée ( voir dans la class RestaurantController)
              //  new Restaurants("etrepagny","27150","31 rue saint","Wacdo Normandie",2L)
    //    );

      //  for (Restaurants restaurant : restaurantsList){
          //  log.info("Dans la boucle {}",restaurant);
          //  log.info("is true {}",restaurant.getId().equals(id));
          //  if (restaurant.getId().equals(id)){
            //    return restaurant;
            //}
       // }

       // return null;
   // }
}
