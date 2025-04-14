package com.gdu.wacdo.repositories;

import com.gdu.wacdo.entities.Collaborateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface CollaborateursRepository extends JpaRepository<Collaborateurs, Long> {
    Optional<Collaborateurs> findByEmail(String username);
}
