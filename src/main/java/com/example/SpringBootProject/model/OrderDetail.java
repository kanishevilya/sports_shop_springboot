package com.example.SpringBootProject.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="orderDetails")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orderdetail")
    private Long id;


    @ManyToOne
    @JoinColumn(name="id_order")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    private Integer productsAmount;
}
