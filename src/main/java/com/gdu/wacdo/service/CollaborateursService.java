package com.gdu.wacdo.service;

import com.gdu.wacdo.dtos.CollaborateursDto;
import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.entities.Restaurants;
import com.gdu.wacdo.repositories.CollaborateursRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j

public class CollaborateursService {

    private final CollaborateursRepository collaborateursRepository;

    private final PasswordEncoder passwordEncoder;

    public CollaborateursService(CollaborateursRepository collaborateursRepository, PasswordEncoder passwordEncoder) {
        this.collaborateursRepository = collaborateursRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Collaborateurs saveCollaborateurs(CollaborateursDto dto){
        //création d'une antité vide
        Collaborateurs collaborateurs = new Collaborateurs(dto.getNom(),dto.getPrenom(),dto.getEmail(),passwordEncoder.encode(dto.getMotDePasse()),dto.getDatePremierEmbauche(),dto.getAdministrateur());
        try {
            return collaborateursRepository.save(collaborateurs);
        } catch (Exception e) {
            System.out.println(e);
        }

        return new Collaborateurs();
    }

    public List<Collaborateurs> getAllCollaborateurs(){

        return collaborateursRepository.findAll();
    }


    public Collaborateurs getCollabDetails(Long id) {

        return collaborateursRepository.findById(id).orElse(null);
        //  Récupération de la même liste que CollaborateursController
       // List<Collaborateurs> collaborateursList = List.of(
              //  new Collaborateurs(false, new Date(), "fafa", "tt@ff.com", "ffff", "fdfff", 1L),
              //  new Collaborateurs(false, new Date(2020 - 12 - 11), "toto", "toto@wacdo.com", "toto", "motdepasse", 2L)
       // );

        //  Cherche le collaborateur correspondant
      //  for (Collaborateurs collaborateursRecup : collaborateursList) {
          //  if (collaborateursRecup.getId().equals(id)) {
             //   return collaborateursRecup;
          //  }
      //  }

       // return null;
   }

    public Collaborateurs updateCollaborateur(Long id, CollaborateursDto dto) {
        Collaborateurs collab = collaborateursRepository.findById(id).orElse(null);
        if (collab == null) {
            return null;
        }

        // Mise à jour des champs
        collab.setNom(dto.getNom());
        collab.setPrenom(dto.getPrenom());
        collab.setEmail(dto.getEmail());
        collab.setMotDePasse(dto.getMotDePasse());
        collab.setDatePremierEmbauche(dto.getDatePremierEmbauche());
        collab.setAdministrateur(dto.getAdministrateur());

        return collaborateursRepository.save(collab);
    }

    public CollaborateursDto getCollaborateurDto(Long id) {
        Collaborateurs collab = collaborateursRepository.findById(id).orElse(null);
        if (collab == null) return null;

        CollaborateursDto dto = new CollaborateursDto();
        dto.setNom(collab.getNom());
        dto.setPrenom(collab.getPrenom());
        dto.setEmail(collab.getEmail());
        dto.setMotDePasse(collab.getMotDePasse());
        dto.setDatePremierEmbauche(collab.getDatePremierEmbauche());
        dto.setAdministrateur(collab.getAdministrateur());

        return dto;
    }

}
