package com.kn.ecommerce.order;


import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderLineResponse(
        Long id,
        Long orderId,
        Long productId,
        String productName,
        BigDecimal price,
        Integer qty
) {
}
