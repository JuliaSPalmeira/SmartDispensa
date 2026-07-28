package com.example.smartdispensa.controller;
import com.example.smartdispensa.dto.ProductResponseDTO;
import com.example.smartdispensa.model.Product;
import com.example.smartdispensa.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.smartdispensa.dto.ProductRequestDTO;
import java.time.LocalDate;
import java.util.List;



@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> listAll(
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        Page<Product> productsPage = productService.findAllProducts(pageable);
        Page<ProductResponseDTO> dtoPage = productsPage.map(ProductResponseDTO::new);
        return ResponseEntity.ok(dtoPage);
    }
    @PostMapping
    public ResponseEntity<ProductResponseDTO> save(@RequestBody @Valid ProductRequestDTO requestDTO) {
        Product product = requestDTO.toEntity();
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(new ProductResponseDTO(savedProduct));
    }
        @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return productService.findProductById(id)
                .map(product -> ResponseEntity.ok(new ProductResponseDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ProductRequestDTO requestDTO) {
        return productService.findProductById(id).map(currentProduct -> {
            currentProduct.setName(requestDTO.name());
            currentProduct.setBrand(requestDTO.brand());
            currentProduct.setExpirationDate(requestDTO.expirationDate());
            currentProduct.setCategory(requestDTO.category());
            currentProduct.setQuantity(requestDTO.quantity());
            currentProduct.setMinimumQuantity(requestDTO.minimumQuantity());

            Product updatedProduct = productService.updateProduct(currentProduct);
            return ResponseEntity.ok(new ProductResponseDTO(updatedProduct));
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
    public ResponseEntity<List<ProductResponseDTO>> listLowStock(@RequestParam Integer limit) {
        List<Product> products = productService.listLowStock(limit);
        List<ProductResponseDTO> dtos = products.stream().map(ProductResponseDTO::new).toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/alerts/expiration")
    public ResponseEntity<List<ProductResponseDTO>> listExpirationAlerts(
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Product> products = productService.listExpirationAlerts(date);
        List<ProductResponseDTO> dtos = products.stream().map(ProductResponseDTO::new).toList();
        return ResponseEntity.ok(dtos);
    }
}




