package com.gdu.wacdo.service;

// Importation des dépendances nécéssaires
import com.gdu.wacdo.dtos.AffectationDto;
import com.gdu.wacdo.entities.*;
import com.gdu.wacdo.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.List;

// Service Spring (bean injecté automatiquement)
@Service
public class AffectationService {

    private final AffectationRepository affectationRepository;
    private final CollaborateursRepository collaborateursRepository;
    private final FonctionsRepository fonctionsRepository;
    private final RestaurantsRepository restaurantsRepository;

    // Constructeur avec injection des repository nécessaires au service
    public AffectationService(AffectationRepository affectationRepository,
                              CollaborateursRepository collaborateursRepository,
                              FonctionsRepository fonctionsRepository,
                              RestaurantsRepository restaurantsRepository) {
        this.affectationRepository = affectationRepository;
        this.collaborateursRepository = collaborateursRepository;
        this.fonctionsRepository = fonctionsRepository;
        this.restaurantsRepository = restaurantsRepository;
    }

    // Crée une nouvelle affectation à partir du DTO
    public Affectation creerAffectation(AffectationDto dto) {

        // Vérifie si le collaborateur a déjà une affectation en cours dans ce resto
        boolean dejaAffecteDansResto = affectationRepository
                .existsByCollaborateurs_IdAndRestaurants_IdAndDateFinIsNull(
                        dto.getCollaborateurId(),
                        dto.getRestaurantId()
                );

        if (dejaAffecteDansResto) {
            // Si c'est le cas, on lève une erreur pour bloquer
            throw new IllegalStateException("Ce collaborateur a déjà un poste actif dans ce restaurant.");
        }

        // On récupère les entités complètes à partir des ID envoyés dans le formulaire (dto)
        Collaborateurs collab = collaborateursRepository.findById(dto.getCollaborateurId()).orElse(null);
        Fonctions fonction = fonctionsRepository.findById(dto.getFonctionId()).orElse(null);
        Restaurants restaurant = restaurantsRepository.findById(dto.getRestaurantId()).orElse(null);

        // Vérification de sécurité, on retourne null si une des entités est absente
        if (collab == null || fonction == null || restaurant == null) return null;

        // Si une affectation sans date de fin existe déjà pour CE resto, on la clôture
        List<Affectation> affectationsExistantes = affectationRepository.findByCollaborateurs_IdOrderByDateDebutDesc(dto.getCollaborateurId());
        Affectation affectationDansResto = affectationsExistantes.stream()
                .filter(a -> a.getRestaurants().getId().equals(dto.getRestaurantId()) && a.getDateFin() == null)
                .findFirst()
                .orElse(null);

        if (affectationDansResto != null) {
            affectationDansResto.setDateFin(new Date());
            affectationRepository.save(affectationDansResto);
        }

        // Création de la nouvelle affectation
        Affectation nouvelle = new Affectation();
        nouvelle.setCollaborateurs(collab);
        nouvelle.setFonctions(fonction);
        nouvelle.setRestaurants(restaurant);
        nouvelle.setDateDebut(dto.getDateDebut());
        nouvelle.setDateFin(null); // null = active

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

    // Pour modifier une affectation (remplace l'ancienne par une nouvelle)
    @Transactional
    public void modifierAffectation(Long id, AffectationDto dto) {
        Affectation ancienneAffectation = affectationRepository.findById(id).orElse(null);
        if (ancienneAffectation == null) {
            throw new IllegalArgumentException("Affectation non trouvée");
        }

        // On cloture l'ancienne
        ancienneAffectation.setDateFin(new Date());
        affectationRepository.save(ancienneAffectation);

        // On vérifie qu'il n'y a pas déjà une affectation identique active
        boolean existeDeja = affectationRepository.existsByCollaborateurs_IdAndRestaurants_IdAndDateFinIsNull(
                dto.getCollaborateurId(), dto.getRestaurantId()
        );

        if (existeDeja) {
            throw new IllegalStateException("Une affectation active existe déjà pour ce collaborateur dans ce restaurant.");
        }

        // On recrée une affectation avec les nouvelles infos
        Collaborateurs collab = collaborateursRepository.findById(dto.getCollaborateurId()).orElse(null);
        Fonctions fonction = fonctionsRepository.findById(dto.getFonctionId()).orElse(null);
        Restaurants restaurant = restaurantsRepository.findById(dto.getRestaurantId()).orElse(null);

        Affectation nouvelle = new Affectation();
        nouvelle.setCollaborateurs(collab);
        nouvelle.setFonctions(fonction);
        nouvelle.setRestaurants(restaurant);
        nouvelle.setDateDebut(dto.getDateDebut());
        nouvelle.setDateFin(dto.getDateFin());

        affectationRepository.save(nouvelle);
    }

    public List<Affectation> getHistoriqueAffectationsParCollaborateur(Long collabId) {
        return affectationRepository.findByCollaborateurs_IdOrderByDateDebutDesc(collabId);
    }

    public Affectation getDerniereAffectation(Long collaborateurId) {
        List<Affectation> affectations = affectationRepository.findByCollaborateurs_IdOrderByDateDebutDesc(collaborateurId);
        if (affectations.isEmpty()) return null;

        return affectations.get(0); // On récupère la plus récente
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

    // Gestionnaire d'exception global pour donner des retours plus propre
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

    // Renvoie la liste des collaborateurs qui ne sont pas affectés (aucune affectation active)
    public List<Collaborateurs> getCollaborateursSansAffectation() {
        List<Collaborateurs> tous = collaborateursRepository.findAll();
        List<Long> idsAffectes = affectationRepository.findAll().stream()
                .filter(a -> a.getDateFin() == null)
                .map(a -> a.getCollaborateurs().getId())
                .distinct()
                .toList();

        return tous.stream()
                .filter(c -> !idsAffectes.contains(c.getId()))
                .toList();
    }
}
