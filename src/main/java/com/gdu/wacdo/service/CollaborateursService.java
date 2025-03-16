package com.gdu.wacdo.service;

import com.gdu.wacdo.entities.Collaborateurs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j

public class CollaborateursService {

    public Collaborateurs getCollabDetails (Long id) {

        //  Récupération de la même liste que CollaborateursController
        List<Collaborateurs> collaborateursList = List.of(
                new Collaborateurs(false, new Date(), "fafa", "tt@ff.com", "ffff", "fdfff", 1L),
                new Collaborateurs(false, new Date(2020 - 12 - 11), "toto", "toto@wacdo.com", "toto", "motdepasse", 2L)
        );

        //  Cherche le collaborateur correspondant
        for (Collaborateurs collaborateursRecup : collaborateursList) {
            if (collaborateursRecup.getId().equals(id)) {
                return collaborateursRecup;
            }
        }

        return null;
    }
}
