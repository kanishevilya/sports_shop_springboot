package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.dto.ProductCritetiaDTO;
import com.example.SpringBootProject.dto.Response.ProductDTOResp;
import com.example.SpringBootProject.repository.ProductRepository;
import com.example.SpringBootProject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/getAllProducts")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTOResp> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getProductById")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTOResp getProductById(@RequestParam Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/getByFiler")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTOResp> getByFilters(@RequestBody ProductCritetiaDTO filter) {
        return productService.getByCriteria(filter);
    }
}
