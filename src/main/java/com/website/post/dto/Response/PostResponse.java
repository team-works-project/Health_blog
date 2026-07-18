package com.website.post.dto.Response;

import com.website.post.entity.PostType;
import com.website.tag_create.dto.Response.TagResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private String id;
    private String title;
    private PostType type;
    private String content;
    private String thumbnail;
    private String categoryId;
    private String categoryName;
    private String authorName;
    private List<TagResponse> tags;
    private Instant createdAt;
    private Instant updatedAt;

}
