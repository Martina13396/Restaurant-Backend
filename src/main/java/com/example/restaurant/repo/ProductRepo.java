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
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query(value = "select p from Product p where p.category.id=:categoryId AND p.active = true order by p.id ")
    Page<Product> findByCategoryIdByOrderAsc(Long categoryId , Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.active = true AND " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) )")
    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(@Param("keyword") String keyword , Pageable pageable);

   Page<Product> findAllByActiveTrueOrderByIdAsc(Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE p.category.id = :categoryId AND p.active = true AND " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) ) " +
            "ORDER BY p.id ASC")
   Page<Product> searchByCategoryAndKeyword( @Param("keyword") String keyword , Long categoryId , Pageable pageable);


    @Query("SELECT p FROM Product p " +
            "WHERE p.category.id = :categoryId " +
            "AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "     OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "ORDER BY p.id ASC")
    Page<Product> searchByCategoryAndKeywordIncludingInactive(
            @Param("keyword") String keyword,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );

    @Query("SELECT p FROM Product p order by p.id asc ")
    Page<Product> findAllIncludingInactive(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    Page<Product> findByCategoryIdIncludingInactive(@Param("categoryId") Long categoryId ,Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseIncludeInactive(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.id IN :ids AND p.active = true")
    List<Product> findAllActiveById(@Param("ids") List<Long> ids);

    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.active = true")
    Optional<Product> findActiveById(@Param("id") Long id);


    Long id(Long id);
}
