package com.example.smartdispensa.dto;

import com.example.smartdispensa.model.Product;
import java.time.LocalDate;

public class ProductResponseDTO {
    private Long id;
    private String name;
    private String brand;
    private LocalDate expirationDate;
    private String category;
    private Integer quantity;
    private Integer minimumQuantity;
    private String status;


    public ProductResponseDTO() {
    }


    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.expirationDate = product.getExpirationDate();
        this.category = product.getCategory();
        this.quantity = product.getQuantity();
        this.minimumQuantity = product.getMinimumQuantity();
        this.status = product.getStatus();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public String getCategory() { return category; }
    public Integer getQuantity() { return quantity; }
    public Integer getMinimumQuantity() { return minimumQuantity; }
    public String getStatus() { return status; }
}
