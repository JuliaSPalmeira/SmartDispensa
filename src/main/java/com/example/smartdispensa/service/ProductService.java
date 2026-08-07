package com.example.smartdispensa.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.smartdispensa.model.Product;
import com.example.smartdispensa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public Page<Product> findAllProducts(Pageable pageable) {
        Page<Product> productsPage = productRepository.findAll(pageable);
        productsPage.forEach(this::calculateStatus);
        return productsPage;
    }
    public Product saveProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        calculateStatus(savedProduct);
        return savedProduct;
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }
    public Product updateProduct(Product product) {
        Product updatedProduct = productRepository.save(product);
        calculateStatus(updatedProduct);
        return updatedProduct;
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }
    public List<Product> listLowStock(Integer limit) {
        List<Product> products = productRepository.findLowStockProducts();
        for (Product product : products) {
            calculateStatus(product);
        }
        return products.stream().limit(limit).toList();
    }

    public List<Product> listExpirationAlerts(LocalDate cutoffDate) {
        List<Product> products = productRepository.findByExpirationDateLessThanEqual(cutoffDate);
        for (Product product : products) {
            calculateStatus(product);
        }

        return products;
    }

    private void calculateStatus(Product product) {
        if (product.getExpirationDate() == null) {
            product.setStatus("SEGURO");
        } else {
            LocalDate today = LocalDate.now();
            LocalDate expiration = product.getExpirationDate();
            Long daysToExpire = java.time.temporal.ChronoUnit.DAYS.between(today, expiration);
            if (daysToExpire < 0) {
                product.setStatus("VENCIDO");
            } else if (daysToExpire <= 7) {
                product.setStatus("CRÍTICO");
            } else {
                product.setStatus("SEGURO");
            }
        }
    }

}
