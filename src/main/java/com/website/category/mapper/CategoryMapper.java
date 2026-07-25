package com.website.category.mapper;


import com.website.category.dto.Request.CategoryRequest;
import com.website.category.dto.Response.CategoryResponse;
import com.website.category.entity.Category;
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

