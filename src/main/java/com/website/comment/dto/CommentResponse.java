package com.website.comment.dto.response;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private String id;
    private String content;
    private String postId;
    private String authorId;
    private String authorName;
    private Instant createdAt;
    private Instant deletedAt;
}