package com.gdu.wacdo.repositories;

import com.gdu.wacdo.entities.Collaborateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CollaborateursRepository extends JpaRepository<Collaborateurs, Long> {


}
