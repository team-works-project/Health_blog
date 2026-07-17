package com.website.post.mapper;

import com.website.post.dto.Request.TagRequest;
import com.website.post.dto.Response.TagResponse;
import com.website.post.entity.Tag;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagResponse from(Tag tag);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Tag from(TagRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateFrom(TagRequest request, @MappingTarget Tag tag);
}
