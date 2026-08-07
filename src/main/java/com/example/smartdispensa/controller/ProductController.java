package com.example.smartdispensa.controller;
import com.example.smartdispensa.dto.ProductResponseDTO;
import com.example.smartdispensa.exception.ResourceNotFoundException;
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
            com.example.smartdispensa.model.Product product = productService.findProductById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Produto com o ID " + id + " não foi encontrado na dispensa."));
            return ResponseEntity.ok(new ProductResponseDTO(product));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ProductRequestDTO requestDTO) {
        Product currentProduct = productService.findProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Impossível atualizar. Produto com o ID " + id + " não existe."));
        currentProduct.setName(requestDTO.name());
        currentProduct.setBrand(requestDTO.brand());
        currentProduct.setExpirationDate(requestDTO.expirationDate());
        currentProduct.setCategory(requestDTO.category());
        currentProduct.setQuantity(requestDTO.quantity());
        currentProduct.setMinimumQuantity(requestDTO.minimumQuantity());

        Product updatedProduct = productService.updateProduct(currentProduct);
        return ResponseEntity.ok(new ProductResponseDTO(updatedProduct));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Product product = productService.findProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Impossível deletar. Produto com o ID " + id + " não existe."));

        productService.deleteProduct(product);
        return ResponseEntity.noContent().build();
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




