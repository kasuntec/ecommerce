package com.kn.ecommerce.kafka.payment;

import com.kn.ecommerce.notification.kafka.common.Customer;

import java.math.BigDecimal;

public record PaymentConfirmation(
        Long id,
        Long orderId,
        String paymentMethod,
        Customer customer,
        BigDecimal amount
) {
}
