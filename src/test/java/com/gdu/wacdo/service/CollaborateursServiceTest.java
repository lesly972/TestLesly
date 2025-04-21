// Package dans lequel on test notre service
package com.gdu.wacdo.service;

// Imports nécessaires pour les entités, mocks, test et assertions
import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.repositories.CollaborateursRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

// Début de la classe de test pour CollaborateursService
class CollaborateursServiceTest {

    // Déclaration des éléments à tester et leurs mocks
    private CollaborateursService collaborateursService;
    private CollaborateursRepository collaborateursRepository;
    private PasswordEncoder passwordEncoder;

    // Avant chaque test on initialise notre service avec des mocks
    @BeforeEach
    void setUp() {
        collaborateursRepository = Mockito.mock(CollaborateursRepository.class); // on mock le repository
        passwordEncoder = Mockito.mock(PasswordEncoder.class); // on mock aussi l'encodeur
        collaborateursService = new CollaborateursService(collaborateursRepository, passwordEncoder); // on instancie le service à tester
    }

    // Test pour le cas ou le collaborateur est trouvé par son ID
    @Test
    void getCollabDetails_shouldReturnCollaborateur_whenIdExists() {
        // On prépare un collaborateur fictif
        Collaborateurs collab = new Collaborateurs();
        collab.setId(1L);

        // On dit au mock de retourner ce collaborateur si on cherche l'ID 1
        Mockito.when(collaborateursRepository.findById(1L)).thenReturn(Optional.of(collab));

        // On appelle la méthode à tester
        Collaborateurs result = collaborateursService.getCollabDetails(1L);

        // On vérifie que le résultat est bien celui attendu
        assertThat(result).isEqualTo(collab);
    }

    // Test pour le cas ou aucun collaborateur n'est trouvé
    @Test
    void getCollabDetails_shouldReturnNull_whenIdDoesNotExist() {
        // On simule que le repo retourne rien pour l'id 42
        Mockito.when(collaborateursRepository.findById(42L)).thenReturn(Optional.empty());

        // On teste la méthode
        Collaborateurs result = collaborateursService.getCollabDetails(42L);

        // Et on vérifie qu'on a bien un null
        assertThat(result).isNull();
    }
}
