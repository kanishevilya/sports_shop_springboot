package com.example.SpringBootProject.service;

import com.example.SpringBootProject.Exceptions.CustomException;
import com.example.SpringBootProject.dto.ProductCritetiaDTO;
import com.example.SpringBootProject.dto.Request.ProductDTOReq;
import com.example.SpringBootProject.dto.Response.ProductDTOResp;
import com.example.SpringBootProject.enums.ErrorCode;
import com.example.SpringBootProject.mapper.ProductMapper;
import com.example.SpringBootProject.model.Product;
import com.example.SpringBootProject.repository.CategoryRepository;
import com.example.SpringBootProject.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<ProductDTOResp> getAllProducts(){
        return ProductMapper.INSTANCE.productListToProductDTOList(productRepository.findAll());
    }

    @Transactional
    public ProductDTOResp saveProduct(ProductDTOReq productReq) {
        Product product = ProductMapper.INSTANCE.productDTOToProduct(productReq);
        product.setCategory( categoryRepository.findById(productReq.categoryId()).orElseThrow());
        productRepository.save(product);
        return ProductMapper.INSTANCE.productToProductDTO(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ProductDTOResp getProductById(Long id) throws CustomException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "Product with id "+id+" not found"));
        return ProductMapper.INSTANCE.productToProductDTO(product);
    }

    @Transactional
    public ProductDTOResp updateProduct(ProductDTOReq product) throws CustomException {
        Product existingProduct = productRepository.findById(product.id())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND,"Product with id "+product.id()+" not found"));

        existingProduct.builder()
                .title(product.title())
                .brand(product.brand())
                .price(product.price())
                .category(categoryRepository.findById(product.categoryId()).orElseThrow())
                .size(product.size())
                .color(product.color())
                .build();

        productRepository.save(existingProduct);
        return ProductMapper.INSTANCE.productToProductDTO(existingProduct);
    }

    // этот метод мы делали в дискорде с ребятами, так что я его никак не изменял

    @Transactional(readOnly = true)
    public List<ProductDTOResp> getByCriteria(ProductCritetiaDTO criterion) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> product = criteriaQuery.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        if (criterion.title() != null) {
            predicates.add(criteriaBuilder.equal(product.get("title"), criterion.title()));
        }

        if (criterion.brand() != null) {
            predicates.add(criteriaBuilder.equal(product.get("brand"), criterion.brand()));
        }

        if (criterion.price() != null) {
            predicates.add(criteriaBuilder.equal(product.get("price"), criterion.price()));
        }

        if (criterion.category() != null) {
            predicates.add(criteriaBuilder.equal(product.get("category"), criterion.category()));
        }

        if (criterion.size() != null) {
            predicates.add(criteriaBuilder.equal(product.get("size"), criterion.size()));
        }

        if (criterion.color() != null) {
            predicates.add(criteriaBuilder.equal(product.get("color"), criterion.color()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<Product> predicatedProducts = entityManager.createQuery(criteriaQuery).getResultList();

        return ProductMapper.INSTANCE.productListToProductDTOList(predicatedProducts);
    }
}
