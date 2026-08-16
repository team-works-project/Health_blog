package com.website.category.service;

import com.website.category.entity.Category;
import com.website.category.mapper.CategoryMapper;
import com.website.category.dto.request.CategoryRequest;
import com.website.category.dto.response.CategoryResponse;
import com.website.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryResponse> list(Integer page, Integer size, String keyword) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, size == null ? 10 : size);
        Page<Category> categories;
        if (keyword == null || keyword.isBlank()) {
            categories = categoryRepository.findAll(pageable);
        } else {
            categories = categoryRepository.findByNameContainingIgnoreCase(keyword, pageable);
        }
        return categories.map(categoryMapper::from);
    }

    @Override
    public CategoryResponse view(String id) {
        return categoryMapper.from(findById(id));
    }

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Category name already exists");
        }
        Category category = categoryMapper.from(request);
        return categoryMapper.from(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponse update(String id, CategoryRequest request) {
        Category category = findById(id);
        categoryMapper.updateFrom(request, category);
        return categoryMapper.from(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void delete(String id) {
        categoryRepository.delete(findById(id));
    }

    @Override
    @Transactional
    public CategoryResponse enable(String id) {
        Category category = findById(id);
        category.enable();
        return categoryMapper.from(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponse disable(String id) {
        Category category = findById(id);
        category.disable();
        return categoryMapper.from(categoryRepository.save(category));
    }

    private Category findById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }
}