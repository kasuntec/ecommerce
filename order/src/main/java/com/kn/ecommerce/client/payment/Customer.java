package com.kn.ecommerce.client.payment;

public record Customer(
        String id,
        String firstName,
        String lastName,
        String email
) {
}
