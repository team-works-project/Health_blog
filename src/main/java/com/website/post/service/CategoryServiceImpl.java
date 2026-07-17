package com.website.post.service;

import com.website.post.dto.Request.CategoryRequest;
import com.website.post.dto.Response.CategoryResponse;
import com.website.post.entity.Category;
import com.website.post.mapper.CategoryMapper;
import com.website.post.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
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
    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Category name already exists");
        }
        Category category = categoryMapper.from(request);
        return categoryMapper.from(categoryRepository.save(category));
    }

}
