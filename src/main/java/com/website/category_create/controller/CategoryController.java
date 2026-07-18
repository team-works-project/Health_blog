package com.website.category_create.controller;




import com.website.category_create.dto.Request.CategoryRequest;
import com.website.category_create.dto.Response.CategoryResponse;
import com.website.category_create.service.CategoryService;
import com.website.shared.entity.HttpBodyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.website.shared.api.ControllerHandler.responseCreated;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<HttpBodyResponse<CategoryResponse>> create(
            @Valid @RequestBody CategoryRequest request) {
        return responseCreated(categoryService.create(request));
    }
}
