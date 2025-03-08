package com.kn.ecommerce.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/")
    public ResponseEntity<ProductResponse> save(@RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productService.save(productRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PutMapping("/")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id,
                                                   @RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productService.update(id,productRequest));
    }

    @GetMapping("/")
    public ResponseEntity<Collection<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.accepted().build();
    }
}
