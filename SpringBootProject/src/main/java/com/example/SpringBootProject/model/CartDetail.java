package com.example.SpringBootProject.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="cartDetails")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cartdetail")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_cart")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    private Integer productsAmount;
}
