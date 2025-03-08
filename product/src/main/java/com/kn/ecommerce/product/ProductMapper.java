package com.kn.ecommerce.product;

import com.kn.ecommerce.category.Category;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toProduct(ProductRequest productRequest) {
        return Product
                .builder()
                .id(productRequest.id())
                .name(productRequest.name())
                .description(productRequest.description())
                .availableQty(productRequest.availableQty())
                .price(productRequest.price())
                .category(Category.builder().id(productRequest.categoryId())
                        .build())
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQty(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName());
    }
}
