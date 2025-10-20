package com.example.restaurant.repo;

import com.example.restaurant.model.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDetailsRepo extends JpaRepository<ProductDetails, String> {
    @Query(value = "select p from ProductDetails p where p.product.id =:productId ")
    Optional<ProductDetails> findByProductId(Long productId);


}

