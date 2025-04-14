package com.gdu.wacdo.repositories;

import com.gdu.wacdo.entities.Affectation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AffectationRepository extends JpaRepository<Affectation, Long> {
    List<Affectation> findByRestaurants_Id(Long id);
    List<Affectation> findByCollaborateurs_IdOrderByDateDebutDesc(Long collaborateurId);
}
