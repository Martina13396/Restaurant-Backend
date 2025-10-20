package com.example.restaurant.repo;

import com.example.restaurant.model.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    Page<Order> findByAccountIdOrderByCodeAsc(long accountId,Pageable pageable );
    Page<Order> findAllByOrderByCodeAsc(Pageable pageable);
}
