package com.example.smartdispensa.controller;

import com.example.smartdispensa.model.Product;
import com.example.smartdispensa.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping
    public List<Product> listAll(){
        return productService.findAllProducts();
    }
    @PostMapping
    public Product save(@RequestBody @Valid Product product){
        return productService.saveProduct(product);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        return productService.findProductById(id)
                .map(product -> ResponseEntity.ok(product))
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody @Valid Product newDetails) {
        return productService.findProductById(id).map(currentProduct -> {
            currentProduct.setName(newDetails.getName());
            currentProduct.setBrand(newDetails.getBrand());
            currentProduct.setExpirationDate(newDetails.getExpirationDate());
            currentProduct.setCategory(newDetails.getCategory());
            currentProduct.setQuantity(newDetails.getQuantity());
            currentProduct.setMinimumQuantity(newDetails.getMinimumQuantity());

            Product updatedProduct = productService.updateProduct(currentProduct);
            return ResponseEntity.ok(updatedProduct);
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return productService.findProductById(id).map(product -> {
            productService.deleteProduct(product);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/alerts/low-stock")
    public List<Product> listLowStock(@RequestParam Integer limit) {
        return productService.listLowStock(limit);
    }
    @GetMapping("/alerts/expiration")
    public List<Product> listExpirationAlerts(@RequestParam String date) {
        LocalDate cutoffDate = LocalDate.parse(date);
        return productService.listExpirationAlerts(cutoffDate);
    }
}




