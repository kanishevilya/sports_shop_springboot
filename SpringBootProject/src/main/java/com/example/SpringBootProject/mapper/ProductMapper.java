package com.example.SpringBootProject.mapper;

import com.example.SpringBootProject.dto.Request.ProductDTOReq;
import com.example.SpringBootProject.dto.Response.ProductDTOResp;
import com.example.SpringBootProject.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTOResp productToProductDTO(Product product);

    List<ProductDTOResp> productListToProductDTOList(List<Product> products);

    @Mapping(target = "category", ignore = true)
    Product productDTOToProduct(ProductDTOReq productDTO);

    List<Product> productDTOListToProductList(List<ProductDTOReq> productDTO);
}