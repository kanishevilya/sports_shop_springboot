package com.example.SpringBootProject.mapper;


import com.example.SpringBootProject.dto.Request.CategoryDTOReq;
import com.example.SpringBootProject.dto.Response.CategoryDTOResp;
import com.example.SpringBootProject.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTOResp categoryToCategoryDTO(Category category);

    Category categoryDTOToCategory(CategoryDTOReq categoryDTO);

    List<CategoryDTOResp> categoryListToCategoryDTOList(List<Category> categories);
}