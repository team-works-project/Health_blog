package com.website.tag.service;

import com.website.tag.dto.request.TagRequest;
import com.website.tag.dto.response.TagResponse;
import org.springframework.data.domain.Page;

public interface TagService {
    Page<TagResponse> list(Integer page, Integer size, String keyword);

    TagResponse view(String id);

    TagResponse create(TagRequest request);

    TagResponse update(String id, TagRequest request);

    void delete(String id);
}