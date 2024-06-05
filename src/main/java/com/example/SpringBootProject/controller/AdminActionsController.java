package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.dto.Request.CategoryDTOReq;
import com.example.SpringBootProject.dto.Request.ProductDTOReq;
import com.example.SpringBootProject.dto.Response.CategoryDTOResp;
import com.example.SpringBootProject.dto.Response.ProductDTOResp;
import com.example.SpringBootProject.dto.UserDTO;
import com.example.SpringBootProject.mapper.UserMapper;
import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.service.CategoryService;
import com.example.SpringBootProject.service.ProductService;
import com.example.SpringBootProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminActionsController {

    private final UserService userService;

    private final ProductService productService;

    private final CategoryService categoryService;

    @GetMapping("/user/getPage")
    public Page<User> get(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam String columnName, @RequestParam String order){
        return userService.getUserByPage(pageNumber, pageSize,columnName, order);
    }

    @GetMapping("/user/getAll")
    public List<UserDTO> getAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/user/getById")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@RequestParam Long id) {
        return userService.findById(id);
    }

    @DeleteMapping("/user/deleteById")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestParam Long id) {
        userService.deleteById(id);
    }

    @PostMapping("/product/saveProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTOResp createProduct(@RequestBody ProductDTOReq dto) {
        return productService.saveProduct(dto);
    }

    @DeleteMapping("/product/deleteById")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
    }

    @PutMapping("/product/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody ProductDTOReq dto) {
        productService.updateProduct(dto);
    }

    @GetMapping("/category/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDTOResp> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping("/category/create")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTOResp createCategory(@RequestBody CategoryDTOReq dto){
        return categoryService.createCategory(dto);
    }

    @DeleteMapping("/category/deleteById")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategoryById(@RequestParam Long id){
        categoryService.deleteCategory(id);
    }

    @PutMapping("/category/update")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTOResp updateCategory(@RequestBody CategoryDTOReq dto){
        return categoryService.updateCategory(dto);
    }
}
