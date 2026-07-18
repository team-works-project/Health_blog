package com.website.post.controller;

import com.website.post.dto.Request.TagRequest;
import com.website.post.dto.Response.TagResponse;
import com.website.post.service.TagService;
import com.website.shared.entity.HttpBodyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.website.shared.api.ControllerHandler.responseCreated;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/tags")
public class TagAdminController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<HttpBodyResponse<TagResponse>> create(
            @Valid @RequestBody TagRequest request) {
        return responseCreated(tagService.create(request));
    }

}
