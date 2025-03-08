package com.kn.ecommerce.product;

import ch.qos.logback.core.util.StringUtil;
import com.kn.ecommerce.exception.ProductNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse save(ProductRequest productRequest) {
        Product product = productMapper.toProduct(productRequest);
        return productMapper.toProductResponse(productRepository.save(product));
    }

    public ProductResponse findById(Long id) {
        Product productById = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found, id: "+id));
        return productMapper.toProductResponse(productById);
    }

    @Transactional
    public ProductResponse update(Long id, @Valid ProductRequest productRequest) {
        Product productById = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found, id: "+id));
        mergeProduct(productRequest,productById);
        return productMapper.toProductResponse(productRepository.save(productById));
    }

    private void mergeProduct(ProductRequest productRequest, Product productById) {
        if(StringUtil.notNullNorEmpty(productRequest.name())) {
            productById.setName(productById.getName());
        }
        if(StringUtil.notNullNorEmpty(productRequest.description())) {
            productById.setDescription(productById.getDescription());
        }
        if(productRequest.availableQty()!=null) {
            productById.setAvailableQty(productById.getAvailableQty());
        }

    }

    public Collection<ProductResponse> findAll() {
        return productRepository
                .findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
