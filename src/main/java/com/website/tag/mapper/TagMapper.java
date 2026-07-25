package com.website.tag.mapper;

import com.website.tag.dto.Request.TagRequest;
import com.website.tag.dto.Response.TagResponse;
import com.website.tag.entity.Tag;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagResponse from(Tag tag);

    @Mapping(target = "createdAt", ignore = true)
    Tag from(TagRequest request);

}