package com.website.category.service;

import com.website.category.dto.request.CategoryRequest;
import com.website.category.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<CategoryResponse> list(Integer page, Integer size, String keyword);

    CategoryResponse view(String id);

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(String id, CategoryRequest request);

    void delete(String id);

    CategoryResponse enable(String id);

    CategoryResponse disable(String id);
}
