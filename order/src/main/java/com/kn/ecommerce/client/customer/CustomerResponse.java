package com.kn.ecommerce.client.customer;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        String mobile,
        CustomerAddress address
){

}
