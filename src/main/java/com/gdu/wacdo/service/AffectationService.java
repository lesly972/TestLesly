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
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

import java.util.Date;

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
        Collaborateurs collab = collaborateursRepository.findById(dto.getCollaborateurId()).orElse(null);
        Fonctions fonction = fonctionsRepository.findById(dto.getFonctionId()).orElse(null);
        Restaurants restaurant = restaurantsRepository.findById(dto.getRestaurantId()).orElse(null);

        if (collab == null || fonction == null || restaurant == null) return null;

        Affectation affectation = new Affectation();
        affectation.setCollaborateurs(collab);
        affectation.setFonctions(fonction);
        affectation.setRestaurants(restaurant);
        affectation.setDateDebut(Date.from(dto.getDateDebut().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        affectation.setDateFin(Date.from(dto.getDateFin().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        return affectationRepository.save(affectation);
    }
}
