package com.example.restaurant.repo;

import com.example.restaurant.model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChefRepo extends JpaRepository<Chef, Long> {

    List<Chef> findAllByOrderByIdAsc();
}
