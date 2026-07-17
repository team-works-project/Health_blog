package com.website.post.controller;

import com.website.post.dto.Request.TagRequest;
import com.website.post.dto.Response.TagResponse;
import com.website.post.service.TagService;
import com.website.shared.entity.HttpBodyPagingResponse;
import com.website.shared.entity.HttpBodyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.website.shared.api.ControllerHandler.responseCreated;
import static com.website.shared.api.ControllerHandler.responsePaging;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/tags")
public class TagAdminController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<HttpBodyResponse<List<TagResponse>>> list(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String keyword) {
        Page<TagResponse> tags = tagService.list(page, size, keyword);
        return responsePaging(
                tags.getContent(),
                HttpBodyPagingResponse.of(
                        tags.getNumber(), tags.getSize(), tags.getTotalElements(), tags.getTotalPages()));
    }
    @PostMapping
    public ResponseEntity<HttpBodyResponse<TagResponse>> create(
            @Valid @RequestBody TagRequest request) {
        return responseCreated(tagService.create(request));
    }

}
