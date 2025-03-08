package com.kn.ecommerce.payment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;


public record PaymentRequest(
        Long orderId,
        String customerId,
        String paymentMethod,
        BigDecimal amount
) {
}
