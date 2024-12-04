package com.example.shop.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @NotNull(message = "OrderId is not null")
    private Long orderID;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_id")
    @NotNull(message = "Product ID is not null")
    private long productId;

    @JsonProperty("product_price")
    private Long productPrice;

//    @JsonProperty("product_image")
//    private String productImage;


    private int quantity;
}
