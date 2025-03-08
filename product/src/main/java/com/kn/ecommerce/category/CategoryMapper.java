package com.kn.ecommerce.category;

import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toProduct(CategoryRequest categoryRequest) {
        return Category
                .builder()
                .id(categoryRequest.id())
                .name(categoryRequest.name())
                .build();
    }

    public CategoryResponse toProductResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );

    }
}
