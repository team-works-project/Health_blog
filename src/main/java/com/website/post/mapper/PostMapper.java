package com.website.post.mapper;


import com.website.post.dto.Response.PostDetailResponse;
import com.website.post.dto.Response.PostResponse;
import com.website.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface PostMapper {
    PostResponse from(Post post);

    PostDetailResponse fromDetail(Post post);
}