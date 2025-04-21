// Le package dans lequel se trouve ce service
package com.gdu.wacdo.service;

// Importation des classes nécessaires (DTO, entités, repo, etc)
import com.gdu.wacdo.dtos.CollaborateursDto;
import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.repositories.CollaborateursRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

// Déclare que cette classe est un service Spring (elle contient de la logique métier)
@Service
// Permet d'utiliser des logs avec @Slf4j (pratique pour débug ou info serveur)
@Slf4j
public class CollaborateursService {

    // On injecte le repo pour accéder à la base de données
    private final CollaborateursRepository collaborateursRepository;

    // Injecté pour hasher les mots de passe avant de les stocker
    private final PasswordEncoder passwordEncoder;

    // Constructeur qui fait l’injection automatique des dépendances
    public CollaborateursService(CollaborateursRepository collaborateursRepository, PasswordEncoder passwordEncoder) {
        this.collaborateursRepository = collaborateursRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Enregistre un nouveau collaborateur dans la BDD
    public Collaborateurs saveCollaborateurs(CollaborateursDto dto){
        // Création d’un objet Collaborateurs à partir du DTO reçu (les données du formulaire)
        Collaborateurs collaborateurs = new Collaborateurs(
                dto.getNom(),
                dto.getPrenom(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getMotDePasse()), // ⚠️ on hash le mot de passe pour la sécurité
                dto.getDatePremierEmbauche(),
                dto.getAdministrateur()
        );

        try {
            return collaborateursRepository.save(collaborateurs); // Et hop, sauvegarde en BDD
        } catch (Exception e) {
            System.out.println(e); // Pas ouf mais ça affiche si ya une erreur
        }

        return new Collaborateurs(); // Retourne un objet vide si ça plante (on pourrait gérer mieux...)
    }

    // Récupère tous les collaborateurs (utile pour l’affichage de la liste)
    public List<Collaborateurs> getAllCollaborateurs(){
        return collaborateursRepository.findAll();
    }

    // Récupère un collaborateur via son ID (pour affichage de détails)
    public Collaborateurs getCollabDetails(Long id) {
        return collaborateursRepository.findById(id).orElse(null); // null si non trouvé (à gérer dans le contrôleur)
    }

    // Mise à jour d’un collaborateur existant (avec les nouvelles valeurs du formulaire)
    public Collaborateurs updateCollaborateur(Long id, CollaborateursDto dto) {
        Collaborateurs collab = collaborateursRepository.findById(id).orElse(null);
        if (collab == null) {
            return null; // si le collaborateur n’existe pas, on abandonne
        }

        // Mise à jour des champs un par un
        collab.setNom(dto.getNom());
        collab.setPrenom(dto.getPrenom());
        collab.setEmail(dto.getEmail());
        collab.setMotDePasse(dto.getMotDePasse()); // ⚠️ ici on n’encode pas à nouveau le mot de passe
        collab.setDatePremierEmbauche(dto.getDatePremierEmbauche());
        collab.setAdministrateur(dto.getAdministrateur());

        return collaborateursRepository.save(collab); // sauvegarde de la modif en BDD
    }

    // Transforme un collaborateur existant en DTO (pratique pour pré-remplir le formulaire de modif)
    public CollaborateursDto getCollaborateurDto(Long id) {
        Collaborateurs collab = collaborateursRepository.findById(id).orElse(null);
        if (collab == null) return null;

        CollaborateursDto dto = new CollaborateursDto();
        dto.setNom(collab.getNom());
        dto.setPrenom(collab.getPrenom());
        dto.setEmail(collab.getEmail());
        dto.setMotDePasse(collab.getMotDePasse()); // à voir si on l'affiche en clair ou pas...
        dto.setDatePremierEmbauche(collab.getDatePremierEmbauche());
        dto.setAdministrateur(collab.getAdministrateur());

        return dto;
    }
}
