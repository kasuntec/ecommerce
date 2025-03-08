package com.kn.ecommerce.kafka.order;

import com.kn.ecommerce.notification.kafka.common.Customer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public record OrderConfirmation(
        Long orderRef,
        BigDecimal totalAmount,
        Customer customer,
        Object createdDate,
        List<OrderLine> orderItems
) {
}
