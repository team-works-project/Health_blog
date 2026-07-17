package com.website.post.mapper;


import com.website.post.dto.Request.CategoryRequest;
import com.website.post.dto.Response.CategoryResponse;
import com.website.post.entity.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse from(Category category);
    @Mapping(target = "createdAt", ignore = true)
    Category from(CategoryRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createdAt", ignore = true)
    void createdAt(CategoryRequest request, @MappingTarget Category category);


}

