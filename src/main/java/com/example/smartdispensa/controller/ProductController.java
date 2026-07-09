package com.example.smartdispensa.controller;

import com.example.smartdispensa.model.Product;
import com.example.smartdispensa.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @GetMapping
    public List<Product> listAll(){
        return productRepository.findAll();
    }
    @PostMapping
    public Product salvar(@RequestBody @Valid Product product){
        return productRepository.save(product);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(product -> ResponseEntity.ok(product))
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody @Valid Product newDetails) {
        return productRepository.findById(id).map(currentProduct -> {
            currentProduct.setName(newDetails.getName());
            currentProduct.setBrand(newDetails.getBrand());
            currentProduct.setExpirationDate(newDetails.getExpirationDate());
            currentProduct.setCategory(newDetails.getCategory());
            currentProduct.setQuantity(newDetails.getQuantity());

            Product updatedProduct = productRepository.save(currentProduct);
            return ResponseEntity.ok(updatedProduct);
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return productRepository.findById(id).map(product -> {
            productRepository.delete(product);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}




