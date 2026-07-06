package com.example.smartdispensa.controller;

import com.example.smartdispensa.model.Product;
import com.example.smartdispensa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @GetMapping
    public List<Product> ListarTodos(){
        return productRepository.findAll();
    }
    @PostMapping
    public Product salvar(@RequestBody Product product){
        return productRepository.save(product);
    }
}
