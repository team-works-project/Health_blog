package com.website.category.service;

import com.website.category.dto.CategoryRequest;
import com.website.category.dto.CategoryResponse;
import com.website.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Page<CategoryResponse> list(Integer page, Integer size, String keyword) {
        return null;
    }

    @Override
    public CategoryResponse view(String id) {
        return null;
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        return null;
    }
}
