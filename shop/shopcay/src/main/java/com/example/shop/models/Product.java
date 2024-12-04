package com.example.shop.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
//@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name" , length = 300)
    private String name;

    @Column(name = "description" , length = 300)
    private String description;

    @Column(name = "original_price")
    private float originalPrice;

    @Column(name = "promotion_price")
    private float promotionPrice;


    @Column(name = "image")
    private String image;

    @Column(name = "sold_count")
    private int soldCount;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category category;


}
