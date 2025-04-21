// Le package qui contient notre service lié aux "fonctions" (c'est-à-dire les postes dans les restaurants)
package com.gdu.wacdo.service;

// Imports des classes nécessaires pour manipuler les fonctions et leurs données
import com.gdu.wacdo.dtos.FonctionsDto;
import com.gdu.wacdo.entities.Fonctions;
import com.gdu.wacdo.repositories.FonctionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

// On utilise @Service pour dire à Spring que cette classe gère une logique métier
@Service
@Slf4j
public class FonctionsService {

    // Le repository qui permet d'accéder aux données des fonctions (postes)
    private final FonctionsRepository fonctionsRepository;

    // Constructeur pour injecter automatiquement le repository au moment de l'initialisation
    public FonctionsService(FonctionsRepository fonctionsRepository) {
        this.fonctionsRepository = fonctionsRepository;
    }

    // Enregistre une nouvelle fonction dans la base de données à partir du DTO (données du formulaire)
    public Fonctions saveFonctions(FonctionsDto dto){
        // On crée un nouvel objet Fonction avec le nom du poste récupéré depuis le DTO
        Fonctions fonctions = new Fonctions(dto.getNomDuPoste());
        try {
            return fonctionsRepository.save(fonctions); // On sauvegarde en base
        } catch (Exception e) {
            System.out.println(e); // en cas d'erreur on affiche dans la console
        }

        return new Fonctions(); // retourne une instance vide en cas d'échec (à améliorer avec de vraies exceptions plus tard)
    }

    // Récupère toutes les fonctions (postes) de la base pour affichage ou sélection dans un menu
    public List<Fonctions> getAllFonctions(){
        return fonctionsRepository.findAll();
    }

    // Permet de récupérer une fonction précise à partir de son ID (utile pour afficher les détails ou modifier)
    public Fonctions getFonctionsById(Long id) {
        return fonctionsRepository.findById(id).orElse(null); // si pas trouvée, on retourne null
    }

    // Met à jour une fonction existante à partir d'un formulaire (via DTO)
    public Fonctions updateFonction(Long id, FonctionsDto dto) {
        // On va d'abord chercher la fonction existante
        Fonctions fonction = fonctionsRepository.findById(id).orElse(null);
        if (fonction == null) return null; // Si elle n'existe pas, on retourne null

        fonction.setNomDuPoste(dto.getNomDuPoste()); // on met à jour le nom du poste
        return fonctionsRepository.save(fonction); // et on sauvegarde les modifs
    }

    // Permet de générer un DTO à partir d'une entité, pratique pour préremplir un formulaire lors d'une modif
    public FonctionsDto getFonctionDto(Long id) {
        Fonctions fonction = fonctionsRepository.findById(id).orElse(null);
        if (fonction == null) return null;

        // On crée un DTO et on lui affecte les valeurs depuis l'entité
        FonctionsDto dto = new FonctionsDto();
        dto.setNomDuPoste(fonction.getNomDuPoste());
        return dto;
    }

}
