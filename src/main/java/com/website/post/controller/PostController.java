package com.website.post.controller;

import com.website.post.dto.Request.PostRequest;
import com.website.post.dto.Response.PostDetailResponse;
import com.website.post.dto.Response.PostResponse;
import com.website.post.service.PostService;
import com.website.shared.entity.HttpBodyPagingResponse;
import com.website.shared.entity.HttpBodyResponse;
import com.website.shared.metadata.Metadata;
import jakarta.transaction.Transactional;
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
public class PostController {
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
    @GetMapping("/{id}")
    public ResponseEntity<HttpBodyResponse<PostResponse>> view(@PathVariable String id) {
        return responseSucceed(postService.view(id));
    }

    @PostMapping
    public ResponseEntity<HttpBodyResponse<PostDetailResponse>> create(
            @Valid @RequestBody PostRequest request) {
        return responseCreated(postService.create(new Metadata(), request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpBodyResponse<PostDetailResponse>> update(
            @PathVariable String id, @Valid @RequestBody PostRequest request) {
        return responseSucceed(postService.update(new Metadata(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        postService.delete(id);
        return responseDeleted();
    }


}
