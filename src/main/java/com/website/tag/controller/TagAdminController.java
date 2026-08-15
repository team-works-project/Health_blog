package com.website.tag.controller;

import com.website.tag.dto.request.TagRequest;
import com.website.tag.dto.response.TagResponse;
import com.website.tag.service.TagService;
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
