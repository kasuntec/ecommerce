package com.kn.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/")
    public ResponseEntity<CustomerResponse> save(@RequestBody @Valid CustomerRequest customerRequest) {
        return ResponseEntity.ok(customerService.save(customerRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable  String id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @PutMapping("/")
    public ResponseEntity<CustomerResponse> update(@PathVariable String id,
                                                   @RequestBody @Valid CustomerRequest customerRequest) {
        return ResponseEntity.ok(customerService.update(id,customerRequest));
    }

    @GetMapping("/")
    public ResponseEntity<Collection<CustomerResponse>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        customerService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

}
