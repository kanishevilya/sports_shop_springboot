package com.example.SpringBootProject.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ColumnDefault("NOW()")
    private LocalDateTime orderDate;

    private String comments;

    private Double totalPrice;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

}
