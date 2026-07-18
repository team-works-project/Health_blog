package com.website.category_create.mapper;


import com.website.category_create.dto.Request.CategoryRequest;
import com.website.category_create.dto.Response.CategoryResponse;
import com.website.category_create.entity.Category;
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

