package com.website.tag_create.mapper;

import com.website.tag_create.dto.Request.TagRequest;
import com.website.tag_create.dto.Response.TagResponse;
import com.website.tag_create.entity.Tag;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagResponse from(Tag tag);

    @Mapping(target = "createdAt", ignore = true)
    Tag from(TagRequest request);

}