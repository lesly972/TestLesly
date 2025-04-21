// Le package qui contient notre service pour gérer les restaurants
package com.gdu.wacdo.service;

// Imports des DTOs et entités liées aux restaurants, ainsi que le repository qui accède à la BD
import com.gdu.wacdo.dtos.RestaurantsDto;
import com.gdu.wacdo.entities.Restaurants;
import com.gdu.wacdo.repositories.RestaurantsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

// On indique à Spring que cette classe est un service métier avec @Service
@Service
@Slf4j
public class RestaurantService {

    // Le repository qui nous permet de faire les requêtes en base pour les restaurants
    private final RestaurantsRepository restaurantsRepository;

    // Constructeur pour injecter le repository automatiquement (Spring s'en charge)
    public RestaurantService(RestaurantsRepository restaurantsRepository) {
        this.restaurantsRepository = restaurantsRepository;
    }

    // Enregistre un nouveau restaurant à partir des données du formulaire (DTO)
    public Restaurants saveRestaurants(RestaurantsDto dto){
        // Création d'une nouvelle entité à partir du DTO (ville, code postal, adresse et nom)
        Restaurants restaurants = new Restaurants(dto.getVille(),dto.getCodePostal(),dto.getAdresse(),dto.getNom());
        try {
            // Sauvegarde en base de données
            return restaurantsRepository.save(restaurants);
        } catch (Exception e){
            // Affiche une erreur dans la console si la sauvegarde échoue (à améliorer plus tard)
            System.out.println(e);
        }

        // En cas d'erreur, retourne un restaurant vide (pas l'idéal mais suffisant pour débuter)
        return new Restaurants();
    }

    // Renvoie la liste complète de tous les restaurants (utile pour l'affichage dans les vues)
    public List<Restaurants> getAllRestaurants(){
        return restaurantsRepository.findAll();
    }

    // Permet de récupérer un restaurant à partir de son ID (ex : quand on clique sur un lien "Détail")
    public Restaurants getRestaurantById(Long id) {
        return restaurantsRepository.findById(id).orElse(null); // retourne null si pas trouvé
    }

    // Permet de modifier les informations d'un restaurant existant à partir d'un formulaire
    public Restaurants updateRestaurant(Long id, RestaurantsDto dto) {
        Restaurants restaurant = restaurantsRepository.findById(id).orElse(null);
        if (restaurant == null) return null; // si le restaurant existe pas on sort

        // Mise à jour des infos depuis le formulaire
        restaurant.setNom(dto.getNom());
        restaurant.setAdresse(dto.getAdresse());
        restaurant.setCodePostal(dto.getCodePostal());
        restaurant.setVille(dto.getVille());

        return restaurantsRepository.save(restaurant); // on sauvegarde les modifs
    }

    // Permet de transformer une entité restaurant en DTO (pratique pour pré-remplir les formulaires)
    public RestaurantsDto getRestaurantDto(Long id) {
        Restaurants restaurant = restaurantsRepository.findById(id).orElse(null);
        if (restaurant == null) return null;

        // Création du DTO et remplissage à partir de l'entité
        RestaurantsDto dto = new RestaurantsDto();
        dto.setNom(restaurant.getNom());
        dto.setAdresse(restaurant.getAdresse());
        dto.setCodePostal(restaurant.getCodePostal());
        dto.setVille(restaurant.getVille());

        return dto;
    }

}
