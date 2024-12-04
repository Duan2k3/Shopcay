package com.example.shop.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "status")
    private String status;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "note")
    private String note;
    @Column(name = "payment_method")
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
