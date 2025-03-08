package com.kn.ecommerce.kafka.order;

import com.kn.ecommerce.client.customer.CustomerResponse;
import com.kn.ecommerce.order.OrderLineResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public record OrderConfirmation(
        Long orderRef,
        BigDecimal totalAmount,
        CustomerResponse customer,
        LocalDateTime createdDate,
        List<OrderLineResponse> orderItems
) {
}
