package com.example.restaurant.repo;


import com.example.restaurant.model.Product;
import com.example.restaurant.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query(value = "select p from Product p where p.category.id=:categoryId order by p.id")
    Page<Product> findByCategoryIdByOrderAsc(Long categoryId , Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description , Pageable pageable);

   Page<Product> findAllByOrderByIdAsc(Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE p.category.id = :categoryId " +
            "AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "ORDER BY p.id ASC")
   Page<Product> searchByCategoryAndKeyword( String keyword , Long categoryId , Pageable pageable);
}
