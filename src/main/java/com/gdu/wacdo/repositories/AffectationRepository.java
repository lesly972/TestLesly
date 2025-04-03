package com.gdu.wacdo.repositories;

import com.gdu.wacdo.entities.Affectation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AffectationRepository extends JpaRepository<Affectation, Long> {
}
