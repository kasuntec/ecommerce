package com.kn.ecommerce.payment;

public record Customer(
        String id,
        String firstName,
        String lastName,
        String email
) {
}
