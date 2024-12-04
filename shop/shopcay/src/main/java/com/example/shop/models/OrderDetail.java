package com.example.shop.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_details")
@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("product_price")
    @Column(name = "productPrice")
    private float productPrice;

    @JsonProperty("product_name")
    @Column(name = "productName")
    private String productName;

    @JsonProperty("product_image")
    @Column(name = "productImage")
    private String productImage;

    @JsonProperty("quantity")
    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
