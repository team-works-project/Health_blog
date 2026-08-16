package com.website.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostNotificationMessage implements Serializable {
    private String postId;
    private String postTitle;
    private String authorId;
    private String authorDisplayName;
}
