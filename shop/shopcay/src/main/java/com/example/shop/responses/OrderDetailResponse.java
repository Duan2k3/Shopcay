package com.example.shop.responses;

import com.example.shop.models.OrderDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private Long id;

    @JsonProperty("order_id")
    private Long orderId;
    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("product_price")
    private Float productPrice;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_Image")
    private String productImage;

    @JsonProperty("quantity")
    private int quantity;

    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail){
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .productPrice(orderDetail.getProductPrice())
                .quantity(orderDetail.getQuantity())
                .productImage(orderDetail.getProductImage())
                .productName(orderDetail.getProductName())


                .build();
    }

}
