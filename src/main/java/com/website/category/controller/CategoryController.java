package com.website.category.controller;

import static com.website.shared.api.ControllerHandler.responseCreated;
import static com.website.shared.api.ControllerHandler.responseDeleted;
import static com.website.shared.api.ControllerHandler.responsePaging;
import static com.website.shared.api.ControllerHandler.responseSucceed;

import com.website.category.dto.CategoryRequest;
import com.website.category.dto.CategoryResponse;
import com.website.category.service.CategoryService;
import com.website.shared.entity.HttpBodyPagingResponse;
import com.website.shared.entity.HttpBodyResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PutMapping("/{id}")
    public ResponseEntity<HttpBodyResponse<CategoryResponse>> update(
            @PathVariable String id, @Valid @RequestBody CategoryRequest request) {
        return responseSucceed(categoryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        categoryService.delete(id);
        return responseDeleted();
    }

    @PatchMapping("/{id}/enabled")
    public ResponseEntity<HttpBodyResponse<CategoryResponse>> enable(@PathVariable String id) {
        return responseSucceed(categoryService.enable(id));
    }

    @PatchMapping("/{id}/disabled")
    public ResponseEntity<HttpBodyResponse<CategoryResponse>> disable(@PathVariable String id) {
        return responseSucceed(categoryService.disable(id));
    }

}