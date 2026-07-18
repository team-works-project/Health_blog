package com.website.tag_create.service;

import com.website.tag_create.dto.Request.TagRequest;
import com.website.tag_create.dto.Response.TagResponse;
import org.springframework.data.domain.Page;

public interface TagService {
    Page<TagResponse> list(Integer page, Integer size, String keyword);
    TagResponse create(TagRequest request);




}
