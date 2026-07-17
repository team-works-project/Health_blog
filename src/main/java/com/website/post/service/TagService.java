package com.website.post.service;

import com.website.post.dto.Request.TagRequest;
import com.website.post.dto.Response.TagResponse;
import org.springframework.data.domain.Page;

public interface TagService {
    Page<TagResponse> list(Integer page, Integer size, String keyword);
    TagResponse create(TagRequest request);




}
