package com.kn.ecommerce.order;

import com.kn.ecommerce.client.customer.CustomerResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        BigDecimal totalAmount,
        String paymentMethod,
        CustomerResponse customer,
        LocalDateTime createdAt,
        LocalDateTime lastUpdated,
        List<OrderLineResponse> orderLines
) {

}
