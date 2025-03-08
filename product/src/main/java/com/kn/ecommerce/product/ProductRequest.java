package com.kn.ecommerce.product;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
        Long id,
        @NotNull(message = "Product name is required")
        String name,
        @NotNull(message = "Product description is required")
        String description,
        Double availableQty,
        @NotNull(message = "Product price is required")
        BigDecimal price,
        @NotNull(message = "Product categoryId is required")
        Integer categoryId
) {

}
