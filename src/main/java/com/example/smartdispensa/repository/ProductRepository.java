package com.example.smartdispensa.repository;

import com.example.smartdispensa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByQuantityLessThanEqual(Integer minimumQuantity);
    List<Product> findByExpirationDateLessThanEqual(LocalDate date);
}
