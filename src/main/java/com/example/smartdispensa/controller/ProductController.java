package com.example.smartdispensa.controller;

import com.example.smartdispensa.model.Product;
import com.example.smartdispensa.repository.ProductRepository;
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
    public List<Product> ListarTodos(){
        return productRepository.findAll();
    }
    @PostMapping
    public Product salvar(@RequestBody Product product){
        return productRepository.save(product);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> buscarPorId(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(product -> ResponseEntity.ok(product))
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> atualizar(@PathVariable Long id, @RequestBody Product novosDados) {
        return productRepository.findById(id).map(produtoExistente -> {
            produtoExistente.setName(novosDados.getName());
            produtoExistente.setBrand(novosDados.getBrand());
            produtoExistente.setExpirationDate(novosDados.getExpirationDate());
            produtoExistente.setCategory(novosDados.getCategory());
            produtoExistente.setQuantity(novosDados.getQuantity());

            Product produtoAtualizado = productRepository.save(produtoExistente);
            return ResponseEntity.ok(produtoAtualizado);
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        return productRepository.findById(id).map(produto -> {
            productRepository.delete(produto);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}




