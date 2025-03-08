package com.kn.ecommerce.client.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "payment-service",
        url = "${application.config.payment-service-url}"
)
public interface PaymentClient {
    @PostMapping("/")
    PaymentResponse makePayment(@RequestBody PaymentRequest paymentRequest);
}
