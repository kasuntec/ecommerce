package com.kn.ecommerce.category;

import ch.qos.logback.core.util.StringUtil;
import com.kn.ecommerce.exception.CategoryNotFoundException;
import com.kn.ecommerce.exception.ProductNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse save(@Valid CategoryRequest productRequest) {
        Category category = categoryMapper.toProduct(productRequest);
        return categoryMapper.toProductResponse(categoryRepository.save(category));
    }

    public CategoryResponse findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found, id: "+id));
        return categoryMapper.toProductResponse(category);
    }

    @Transactional
    public CategoryResponse update(Long id, @Valid CategoryRequest categoryRequest) {
        Category productById = categoryRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Category not found, id: "+id));
        mergeProduct(categoryRequest,productById);
        return categoryMapper.toProductResponse(categoryRepository.save(productById));
    }

    private void mergeProduct(CategoryRequest categoryRequest, Category category) {
        if(StringUtil.notNullNorEmpty(categoryRequest.name())) {
            category.setName(category.getName());
        }
    }

    public Collection<CategoryResponse> findAll() {
        return categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
