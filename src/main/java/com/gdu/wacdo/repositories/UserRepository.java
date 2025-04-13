package com.gdu.wacdo.repositories;

import com.gdu.wacdo.entities.Collaborateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gdu.wacdo.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //Optional<User> findByPseudo(String username);
    Optional<Collaborateurs> findByPseudo(String username);
}
