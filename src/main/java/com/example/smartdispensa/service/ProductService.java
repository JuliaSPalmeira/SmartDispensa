package com.example.smartdispensa.service;

import com.example.smartdispensa.model.Product;
import com.example.smartdispensa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
}
