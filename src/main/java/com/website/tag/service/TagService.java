package com.website.tag.service;

import com.website.tag.dto.Request.TagRequest;
import com.website.tag.dto.Response.TagResponse;
import org.springframework.data.domain.Page;

public interface TagService {
    Page<TagResponse> list(Integer page, Integer size, String keyword);
    TagResponse create(TagRequest request);




}
