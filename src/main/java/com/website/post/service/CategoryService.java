package com.website.post.service;

import com.website.post.dto.Request.CategoryRequest;
import com.website.post.dto.Response.CategoryResponse;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<CategoryResponse> list(Integer page, Integer size, String keyword);

    CategoryResponse create(CategoryRequest request);
}
