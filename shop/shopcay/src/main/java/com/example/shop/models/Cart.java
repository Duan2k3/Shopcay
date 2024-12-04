package com.example.shop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart")
@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "productPrice")
    private float productPrice;

    @Column(name = "productName")
    private String productName;

    @Column(name = "productImage")
    private String productImage;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "orderId")
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}


