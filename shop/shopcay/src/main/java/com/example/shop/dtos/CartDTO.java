package com.example.shop.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    @NotEmpty(message = "UserId is not null")
    private int userId;


    @NotBlank(message = "ProductId is not null")
    private int productId;
    @Min(value = 0 , message = "Product price must be greater more than 0")
    private long productPrice;

    private String productName;

    private String productImage;
}