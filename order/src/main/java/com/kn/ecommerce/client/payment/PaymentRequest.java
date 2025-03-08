package com.kn.ecommerce.client.payment;

import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
public record PaymentRequest(
        Long orderId,
        String customerId,
        String paymentMethod,
        BigDecimal amount
) {
}
