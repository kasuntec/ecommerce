package com.kn.ecommerce.kafka.order;

import java.math.BigDecimal;

public  record OrderLine(
        Long orderId,
        Long productId,
        String productName,
        BigDecimal price,
        Integer qty
) {
}