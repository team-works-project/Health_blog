package com.website.category_create.service;

import com.website.category_create.dto.Request.CategoryRequest;
import com.website.category_create.dto.Response.CategoryResponse;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<CategoryResponse> list(Integer page, Integer size, String keyword);

    CategoryResponse create(CategoryRequest request);
}
