package com.website.category.service;

import com.website.category.dto.Request.CategoryRequest;
import com.website.category.dto.Response.CategoryResponse;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<CategoryResponse> list(Integer page, Integer size, String keyword);

    CategoryResponse create(CategoryRequest request);
}
