package com.example.SpringBootProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long id;

    @Column(nullable = false)
    private String title;

    private String brand;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    private String size;

    private String color;
}
