package com.gdu.wacdo.repositories;

import com.gdu.wacdo.entities.Fonctions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface FonctionsRepository extends JpaRepository<Fonctions, Long> {

}
