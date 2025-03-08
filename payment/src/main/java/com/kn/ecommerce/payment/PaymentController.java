package com.kn.ecommerce.payment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/")
    public ResponseEntity<PaymentResponse> save(@RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(paymentService.save(paymentRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> findById(@PathVariable  Long id) {
        return ResponseEntity.ok(paymentService.findById(id));
    }
}
