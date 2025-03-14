package com.kn.ecommerce.payment;

import java.math.BigDecimal;

public record PaymentResponse (
        Long id,
        Long orderId,
        String paymentMethod,
        BigDecimal amount
){
}
