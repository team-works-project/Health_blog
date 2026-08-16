package com.website.tag.controller;

import com.website.tag.dto.request.TagRequest;
import com.website.tag.service.TagService;
import com.website.tag.dto.response.TagResponse;
import com.website.shared.entity.HttpBodyPagingResponse;
import com.website.shared.entity.HttpBodyResponse;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.website.shared.api.ControllerHandler.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagUserController {
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