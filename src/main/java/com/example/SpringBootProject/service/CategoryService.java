package com.example.SpringBootProject.service;

import com.example.SpringBootProject.Exceptions.CustomException;
import com.example.SpringBootProject.dto.Request.CategoryDTOReq;
import com.example.SpringBootProject.dto.Response.CategoryDTOResp;
import com.example.SpringBootProject.enums.ErrorCode;
import com.example.SpringBootProject.mapper.CategoryMapper;
import com.example.SpringBootProject.mapper.ProductMapper;
import com.example.SpringBootProject.model.Cart;
import com.example.SpringBootProject.model.Category;
import com.example.SpringBootProject.model.Product;
import com.example.SpringBootProject.repository.CategoryRepository;
import com.example.SpringBootProject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTOResp> getAllCategories() {
        return CategoryMapper.INSTANCE.categoryListToCategoryDTOList(categoryRepository.findAll());
    }


    @Transactional
    public CategoryDTOResp createCategory(CategoryDTOReq categoryDTO) {
        Category category = CategoryMapper.INSTANCE.categoryDTOToCategory(categoryDTO);
        categoryRepository.save(category);
        return CategoryMapper.INSTANCE.categoryToCategoryDTO(category);
    }

    @Transactional
    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }

    @Transactional
    public CategoryDTOResp updateCategory(CategoryDTOReq categoryDTO){
        Category existingCategory = categoryRepository.findById(categoryDTO.id())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND,"Category with id "+categoryDTO.id()+" not found"));

        existingCategory.builder()
                .name(categoryDTO.name())
                .products(ProductMapper.INSTANCE.productDTOListToProductList(categoryDTO.products()))
                .build();

        categoryRepository.save(existingCategory);
        return CategoryMapper.INSTANCE.categoryToCategoryDTO(existingCategory);
    }
}
