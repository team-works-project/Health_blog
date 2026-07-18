package com.website.post.service;


import com.website.post.dto.Response.PostResponse;
import org.springframework.data.domain.Page;


public interface PostService {
    Page<PostResponse> list(Integer page, Integer size, String keyword);

    PostResponse view(String id);
}