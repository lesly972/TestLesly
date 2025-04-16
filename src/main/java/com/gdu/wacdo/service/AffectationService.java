package com.gdu.wacdo.service;

import com.gdu.wacdo.dtos.AffectationDto;
import com.gdu.wacdo.entities.Affectation;
import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.entities.Fonctions;
import com.gdu.wacdo.entities.Restaurants;
import com.gdu.wacdo.repositories.AffectationRepository;
import com.gdu.wacdo.repositories.CollaborateursRepository;
import com.gdu.wacdo.repositories.FonctionsRepository;
import com.gdu.wacdo.repositories.RestaurantsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.time.ZoneId;
import java.util.Date;

import java.util.Date;
import java.util.List;

@Service

public class AffectationService {

    private final AffectationRepository affectationRepository;
    private final CollaborateursRepository collaborateursRepository;
    private final FonctionsRepository fonctionsRepository;
    private final RestaurantsRepository restaurantsRepository;

    public AffectationService(AffectationRepository affectationRepository,CollaborateursRepository collaborateursRepository,FonctionsRepository fonctionsRepository,RestaurantsRepository restaurantsRepository) {
        this.affectationRepository = affectationRepository;
        this.collaborateursRepository = collaborateursRepository;
        this.fonctionsRepository = fonctionsRepository;
        this.restaurantsRepository = restaurantsRepository;
    }


    public Affectation creerAffectation(AffectationDto dto) {

        //  Vérifie si déjà affecté à ce restaurant
        boolean dejaAffecteDansResto = affectationRepository
                .existsByCollaborateurs_IdAndRestaurants_IdAndDateFinIsNull(
                        dto.getCollaborateurId(),
                        dto.getRestaurantId()
                );

        if (dejaAffecteDansResto) {
            throw new IllegalStateException("Ce collaborateur a déjà un poste actif dans ce restaurant.");
        }

        Collaborateurs collab = collaborateursRepository.findById(dto.getCollaborateurId()).orElse(null);
        Fonctions fonction = fonctionsRepository.findById(dto.getFonctionId()).orElse(null);
        Restaurants restaurant = restaurantsRepository.findById(dto.getRestaurantId()).orElse(null);

        if (collab == null || fonction == null || restaurant == null) return null;

        //  Clôturer uniquement l'affectation active dans CE restaurant (si elle existe)
        List<Affectation> affectationsExistantes = affectationRepository.findByCollaborateurs_IdOrderByDateDebutDesc(dto.getCollaborateurId());
        Affectation affectationDansResto = affectationsExistantes.stream()
                .filter(a -> a.getRestaurants().getId().equals(dto.getRestaurantId()) && a.getDateFin() == null)
                .findFirst()
                .orElse(null);

        if (affectationDansResto != null) {
            affectationDansResto.setDateFin(new Date());
            affectationRepository.save(affectationDansResto);
        }

        Affectation nouvelle = new Affectation();
        nouvelle.setCollaborateurs(collab);
        nouvelle.setFonctions(fonction);
        nouvelle.setRestaurants(restaurant);
        nouvelle.setDateDebut(dto.getDateDebut());
        nouvelle.setDateFin(null); // active

        return affectationRepository.save(nouvelle);
    }

    public List<Affectation> getAllAffectations() {
        return affectationRepository.findAll();
    }

    public Affectation getAffectationById(Long id) {
        return affectationRepository.findById(id).orElse(null);
    }

    public List<Affectation> getAffectationsByRestaurantId(Long restaurantId) {
        return affectationRepository.findByRestaurants_Id(restaurantId);
    }


    @Transactional
    public void modifierAffectation(Long id, AffectationDto dto) {
        Affectation ancienneAffectation = affectationRepository.findById(id).orElse(null);
        if (ancienneAffectation == null) {
            throw new IllegalArgumentException("Affectation non trouvée");
        }

        // Clôture de l'ancienne affectation
        ancienneAffectation.setDateFin(new Date());
        affectationRepository.save(ancienneAffectation);

        // Vérifie s’il existe déjà une affectation active identique
        boolean existeDeja = affectationRepository.existsByCollaborateurs_IdAndRestaurants_IdAndDateFinIsNull(
                dto.getCollaborateurId(), dto.getRestaurantId()
        );

        if (existeDeja) {
            throw new IllegalStateException("Une affectation active existe déjà pour ce collaborateur dans ce restaurant.");
        }

        // Création d'une nouvelle affectation
        Collaborateurs collab = collaborateursRepository.findById(dto.getCollaborateurId()).orElse(null);
        Fonctions fonction = fonctionsRepository.findById(dto.getFonctionId()).orElse(null);
        Restaurants restaurant = restaurantsRepository.findById(dto.getRestaurantId()).orElse(null);

        Affectation nouvelle = new Affectation();
        nouvelle.setCollaborateurs(collab);
        nouvelle.setFonctions(fonction);
        nouvelle.setRestaurants(restaurant);
        nouvelle.setDateDebut(dto.getDateDebut());
        nouvelle.setDateFin(dto.getDateFin()); // ou null si active

        affectationRepository.save(nouvelle);
    }


    public List<Affectation> getHistoriqueAffectationsParCollaborateur(Long collabId) {
        return affectationRepository.findByCollaborateurs_IdOrderByDateDebutDesc(collabId);
    }

    public Affectation getDerniereAffectation(Long collaborateurId) {
        List<Affectation> affectations = affectationRepository.findByCollaborateurs_IdOrderByDateDebutDesc(collaborateurId);
        if (affectations.isEmpty()) return null;

        return affectations.get(0); // la plus récente en premier
    }

    public List<Affectation> getAffectationsActives() {
        return affectationRepository.findAll().stream()
                .filter(a -> a.getDateFin() == null)
                .toList();
    }

    public List<Restaurants> getRestaurantsAffectesParCollaborateur(Long collaborateurId) {
        return affectationRepository.findByCollaborateurs_IdOrderByDateDebutDesc(collaborateurId)
                .stream()
                .map(Affectation::getRestaurants)
                .distinct()
                .toList();
    }

    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleAllExceptions(Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur interne : " + ex.getMessage());
        }

        @ExceptionHandler(IllegalStateException.class)
        public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
            return ResponseEntity
                    .badRequest()
                    .body("Erreur métier : " + ex.getMessage());
        }
    }


}
