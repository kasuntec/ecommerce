package com.kn.ecommerce.customer;

import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "Customer firstName is required")
        String firstName,
        @NotNull(message = "Customer lastName is required")
        String lastName,
        @NotNull(message = "Customer email is required")
        String email,
        @NotNull(message = "Customer mobile is required")
        String mobile,
        @NotNull(message = "Customer address is required")
        Address address
) {

}
