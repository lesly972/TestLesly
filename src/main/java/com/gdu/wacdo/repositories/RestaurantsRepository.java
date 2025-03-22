package com.gdu.wacdo.repositories;

import com.gdu.wacdo.entities.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantsRepository extends JpaRepository<Restaurants,Long> {


}
