package com.website.tag.controller;

import static com.website.shared.api.ControllerHandler.responseCreated;
import static com.website.shared.api.ControllerHandler.responseDeleted;
import static com.website.shared.api.ControllerHandler.responsePaging;
import static com.website.shared.api.ControllerHandler.responseSucceed;

import com.website.tag.service.TagService;
import com.website.tag.dto.request.TagRequest;
import com.website.tag.dto.response.TagResponse;
import com.website.shared.entity.HttpBodyPagingResponse;
import com.website.shared.entity.HttpBodyResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Admin-only endpoints for managing tags (create/update/delete). Access to this
 * whole path is restricted to ADMIN in SecurityConfiguration.
 */
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

    @GetMapping("/{id}")
    public ResponseEntity<HttpBodyResponse<TagResponse>> view(@PathVariable String id) {
        return responseSucceed(tagService.view(id));
    }

    @PostMapping
    public ResponseEntity<HttpBodyResponse<TagResponse>> create(
            @Valid @RequestBody TagRequest request) {
        return responseCreated(tagService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpBodyResponse<TagResponse>> update(
            @PathVariable String id, @Valid @RequestBody TagRequest request) {
        return responseSucceed(tagService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        tagService.delete(id);
        return responseDeleted();
    }
}