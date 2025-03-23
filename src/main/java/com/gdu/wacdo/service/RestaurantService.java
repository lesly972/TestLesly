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
        Restaurants restaurants = new Restaurants(dto.getNom(),dto.getVille(),dto.getAdresse(),dto.getCodePostal());
        try {
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
