package com.gdu.wacdo.service;

import com.gdu.wacdo.dtos.FonctionsDto;
import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.entities.Fonctions;
import com.gdu.wacdo.entities.Restaurants;
import com.gdu.wacdo.repositories.FonctionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j

public class FonctionsService {

    private final FonctionsRepository fonctionsRepository;

    public FonctionsService(FonctionsRepository fonctionsRepository) {
        this.fonctionsRepository = fonctionsRepository;
    }

    public Fonctions saveFonctions(FonctionsDto dto){

        Fonctions fonctions = new Fonctions(dto.getNomDuPoste());
        try {
            return fonctionsRepository.save(fonctions);
        } catch (Exception e) {
            System.out.println(e);
        }

        return new Fonctions();
    }

    public List<Fonctions> getAllFonctions(){

        return fonctionsRepository.findAll();
    }

    // methode qui permet de récupérer un restaurant en fonction de son ID dans la BdD
    public Fonctions getFonctionsById(Long id) {
        return fonctionsRepository.findById(id).orElse(null);
    }

    public Fonctions updateFonction(Long id, FonctionsDto dto) {
        Fonctions fonction = fonctionsRepository.findById(id).orElse(null);
        if (fonction == null) return null;

        fonction.setNomDuPoste(dto.getNomDuPoste());
        return fonctionsRepository.save(fonction);
    }

    public FonctionsDto getFonctionDto(Long id) {
        Fonctions fonction = fonctionsRepository.findById(id).orElse(null);
        if (fonction == null) return null;

        FonctionsDto dto = new FonctionsDto();
        dto.setNomDuPoste(fonction.getNomDuPoste());
        return dto;
    }

}
