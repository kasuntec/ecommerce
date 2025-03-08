package com.kn.ecommerce.notification.kafka.common;

public record Customer(
        String id,
        String firstName,
        String lastName,
        String email
) {
}
