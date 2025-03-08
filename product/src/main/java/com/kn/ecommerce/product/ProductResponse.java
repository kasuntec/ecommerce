package com.kn.ecommerce.product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        Double availableQty,
        BigDecimal price,
        Integer categoryId,
        String categoryName
) {
}
