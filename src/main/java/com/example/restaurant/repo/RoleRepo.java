package com.example.restaurant.repo;

import com.example.restaurant.model.RoleCode;
import com.example.restaurant.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
  Role findByCode(RoleCode code);
}
