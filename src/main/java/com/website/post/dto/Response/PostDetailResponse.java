package com.website.post.dto.Response;


import com.website.post.entity.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * What admins see when managing posts - same content as PostResponse, plus
 * moderation/audit info (enabled status, who created/last updated it) that
 * regular readers don't need to see.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResponse {
    private String id;
    private String title;
    private PostType type;
    private String content;
    private String thumbnail;
    private String categoryId;
    private String categoryName;
    private String authorName;
    private List<TagResponse> tags;
    private boolean enabled;
    private String createdBy;
    private String updatedBy;
    private Instant createdAt;
    private Instant updatedAt;
}
