package com.website.post.dto.Request;

import com.website.post.entity.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    @NotBlank
    @Size(max = 255)
    private String title;

    @NotNull
    private PostType type;

    @NotBlank
    private String content;

    private String thumbnail;

    private String categoryId;

    private List<String> tagIds;
}
