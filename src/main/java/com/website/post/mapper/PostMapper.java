package com.website.post.mapper;


import com.website.post.dto.Response.PostDetailResponse;
import com.website.post.dto.Response.PostResponse;
import com.website.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface PostMapper {
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "authorName", source = "author.displayName")
    PostResponse from(Post post);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "authorName", source = "author.displayName")
    PostDetailResponse fromDetail(Post post);
}