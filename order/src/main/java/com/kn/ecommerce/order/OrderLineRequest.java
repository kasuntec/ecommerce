package com.kn.ecommerce.order;


import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderLineRequest(
        Long orderId,
        @NotNull(message = "Order line productId is required")
        Long productId,
        @NotNull(message = "Order line price is required")
        BigDecimal price,
        @NotNull(message = "Order line qty is required")
        Integer qty
) {
}
