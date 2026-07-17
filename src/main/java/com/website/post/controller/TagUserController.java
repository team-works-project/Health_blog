package com.website.post.controller;

import com.website.post.dto.Response.TagResponse;
import com.website.post.service.TagService;
import com.website.shared.entity.HttpBodyPagingResponse;
import com.website.shared.entity.HttpBodyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.website.shared.api.ControllerHandler.responsePaging;
import static com.website.shared.api.ControllerHandler.responseSucceed;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagUserController {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
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
    }

