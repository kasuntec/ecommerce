package com.kn.ecommerce.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(

        Long id,
        @NotNull(message = "Order totalAmount is required")
        BigDecimal totalAmount,
        @NotNull(message = "Order paymentMethod is required")
        String paymentMethod,
        @NotNull(message = "Order customerId is required")
        String customerId,
        @NotEmpty(message = "Order items are required")
        List<OrderLineRequest> orderLines
) {
}
