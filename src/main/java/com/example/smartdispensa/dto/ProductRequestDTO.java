package com.example.smartdispensa.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class ProductRequestDTO {

    @NotBlank(message = "O nome do produto é obrigatório")
    private String name;

    @NotBlank(message = "A marca do produto é obrigatória")
    private String brand;

    @NotNull(message = "A data de validade é obrigatória")
    @FutureOrPresent(message = "A data de validade não pode ser no passado")
    private LocalDate expirationDate;

    @NotBlank(message = "A categoria é obrigatória")
    private String category;

    @NotNull(message = "A quantidade é obrigatória")
    @PositiveOrZero(message = "A quantidade deve ser zero ou maior")
    private Integer quantity;

    @NotNull(message = "A quantidade mínima é obrigatória")
    @PositiveOrZero(message = "A quantidade mínima deve ser zero ou maior")
    private Integer minimumQuantity;

    public ProductRequestDTO() {
    }

    public com.example.smartdispensa.model.Product toEntity() {
        return new com.example.smartdispensa.model.Product(
                this.name,
                this.brand,
                this.expirationDate,
                this.category,
                this.quantity,
                this.minimumQuantity
        );
    }

    public String name() { return name; }
    public String brand() { return brand; }
    public LocalDate expirationDate() { return expirationDate; }
    public String category() { return category; }
    public Integer quantity() { return quantity; }
    public Integer minimumQuantity() { return minimumQuantity; }

    public void setName(String name) { this.name = name; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
    public void setCategory(String category) { this.category = category; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setMinimumQuantity(Integer minimumQuantity) { this.minimumQuantity = minimumQuantity; }
}
