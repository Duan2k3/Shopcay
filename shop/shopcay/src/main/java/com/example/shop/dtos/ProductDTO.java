package com.example.shop.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    @JsonProperty("name")
    @NotNull(message = "name product is not null")
    private String name;

    @JsonProperty("original_price")
    @Min(value = 0 , message = "Price must be than 0")
    private Float originalPrice;

    @JsonProperty("promotion_price")
    @Min(value = 0 , message = "Price must be than 0")
    private Float promotionPrice;


    private String description;

    private String image;
    @JsonProperty("categoryID")
    @NotNull(message = "Category is not null")
    private Long categoryID;

    @JsonProperty("sold_count")
    private int soldCount;

    private int quantity;

}
