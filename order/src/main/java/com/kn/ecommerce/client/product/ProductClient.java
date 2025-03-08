package com.kn.ecommerce.client.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "product-service",
        url = "${application.config.product-service-url}"
)
public interface ProductClient {
    @GetMapping("/{product-id}")
    Optional<ProductResponse> getProductById(@PathVariable("product-id") Long id);
}
