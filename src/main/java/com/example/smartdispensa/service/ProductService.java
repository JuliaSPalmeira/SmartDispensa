package com.example.smartdispensa.service;

import com.example.smartdispensa.model.Product;
import com.example.smartdispensa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Product> findAllProducts(){
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            if (product.getExpirationDate() == null) {
                product.setStatus("Seguro");
            } else {
                LocalDate today = LocalDate.now();
                LocalDate expiration = product.getExpirationDate();
                Long daysToExpire = ChronoUnit.DAYS.between(today, expiration);
                if (daysToExpire < 0) {
                    product.setStatus("VENCIDO");
                } else if (daysToExpire <= 7) {
                    product.setStatus("CRÍTICO");
                } else {
                    product.setStatus("SEGURO");
                }
            }
        }
        return products;
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
        List<Product> products = productRepository.findByQuantityLessThanEqual(limit);
        for (Product product : products) {
            calculateStatus(product);
        }
        return products;
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
