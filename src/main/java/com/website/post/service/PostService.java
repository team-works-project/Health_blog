package com.website.post.service;


import com.website.post.dto.Request.PostRequest;
import com.website.post.dto.Response.PostDetailResponse;
import com.website.post.dto.Response.PostResponse;
import com.website.shared.metadata.Metadata;
import org.springframework.data.domain.Page;


public interface PostService {
    Page<PostResponse> list(Integer page, Integer size, String keyword);

    PostResponse view(String id);

    PostDetailResponse create(Metadata metadata, PostRequest request);
}