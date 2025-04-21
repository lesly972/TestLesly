package com.gdu.wacdo.service;

import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.repositories.CollaborateursRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CollaborateursServiceTest {

    private CollaborateursService collaborateursService;
    private CollaborateursRepository collaborateursRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        collaborateursRepository = Mockito.mock(CollaborateursRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        collaborateursService = new CollaborateursService(collaborateursRepository, passwordEncoder);
    }

    @Test
    void getCollabDetails_shouldReturnCollaborateur_whenIdExists() {
        Collaborateurs collab = new Collaborateurs();
        collab.setId(1L);
        Mockito.when(collaborateursRepository.findById(1L)).thenReturn(Optional.of(collab));

        Collaborateurs result = collaborateursService.getCollabDetails(1L);

        assertThat(result).isEqualTo(collab);
    }

    @Test
    void getCollabDetails_shouldReturnNull_whenIdDoesNotExist() {
        Mockito.when(collaborateursRepository.findById(42L)).thenReturn(Optional.empty());

        Collaborateurs result = collaborateursService.getCollabDetails(42L);

        assertThat(result).isNull();
    }
}
