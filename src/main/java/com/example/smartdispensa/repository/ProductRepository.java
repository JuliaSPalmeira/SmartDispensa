package com.example.smartdispensa.repository;

import com.example.smartdispensa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
