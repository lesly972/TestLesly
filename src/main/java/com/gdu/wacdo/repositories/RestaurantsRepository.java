package com.gdu.wacdo.repositories;

import com.gdu.wacdo.entities.Affectation;
import com.gdu.wacdo.entities.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantsRepository extends JpaRepository<Restaurants,Long> {

}
