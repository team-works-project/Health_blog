package com.website.category.service;

import com.website.category.dto.CategoryRequest;
import com.website.category.dto.CategoryResponse;
import org.springframework.data.domain.Page;

public interface CategoryService {
    CategoryResponse create(CategoryRequest request);
}
