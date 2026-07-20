package com.website.category.service;

import com.website.category.dto.CategoryRequest;
import com.website.category.dto.CategoryResponse;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<CategoryResponse> list(Integer page, Integer size, String keyword);
    CategoryResponse view(String id);
    CategoryResponse create(CategoryRequest request);
    CategoryResponse update(String id, CategoryRequest request);
    void delete(String id);
}
