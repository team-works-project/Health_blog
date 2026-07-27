package com.website.tag.controller;

import static com.website.shared.api.ControllerHandler.responsePaging;
import static com.website.shared.api.ControllerHandler.responseSucceed;

import com.website.tag.service.TagService;
import com.website.tag.dto.response.TagResponse;
import com.website.shared.entity.HttpBodyPagingResponse;
import com.website.shared.entity.HttpBodyResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Public/read-only endpoints for browsing tags (e.g. filtering posts by tag,
 * showing a tag cloud). No login required - see SecurityConfiguration.
 */
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
}