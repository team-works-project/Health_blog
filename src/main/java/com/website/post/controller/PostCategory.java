package com.website.post.controller;

import com.website.post.dto.Request.PostRequest;
import com.website.post.dto.Response.PostDetailResponse;
import com.website.post.dto.Response.PostResponse;
import com.website.post.service.PostService;
import com.website.shared.entity.HttpBodyPagingResponse;
import com.website.shared.entity.HttpBodyResponse;
import com.website.shared.metadata.Metadata;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.website.shared.api.ControllerHandler.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostCategory {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<HttpBodyResponse<List<PostResponse>>> list(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String keyword) {
        Page<PostResponse> posts = postService.list(page, size, keyword);
        return responsePaging(
                posts.getContent(),
                HttpBodyPagingResponse.of(
                        posts.getNumber(), posts.getSize(), posts.getTotalElements(), posts.getTotalPages()));
    }


}
