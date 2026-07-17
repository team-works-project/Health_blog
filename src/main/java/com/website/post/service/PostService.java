package com.website.post.service;

import com.website.post.dto.Request.PostRequest;
import com.website.post.dto.Response.PostDetailResponse;
import com.website.post.dto.Response.PostResponse;
import com.website.shared.metadata.Metadata;
import com.website.shared.metadata.MetadataHandler;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface PostService {
    Page<PostResponse> list(Integer page, Integer size, String keyword);

    PostResponse view(String id);

    PostDetailResponse create(Metadata metadata, PostRequest request);


    @MetadataHandler
    @Transactional
    PostDetailResponse update(Metadata metadata, String id, PostRequest request);

    void delete(String id);

    PostDetailResponse enable(Metadata metadata, String id);

    PostDetailResponse disable(Metadata metadata, String id);
}