package com.example.shop.responses;

import com.example.shop.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseResponse{

    private Long id;
    private String name;
    private float promotionPrice;
    private String image;
    private float originalPrice;
    private String description;
    private int soldCount;

    @JsonProperty("category_id")
    private Long categoryId;
    public  static ProductResponse fromProduct(Product product)
    {
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .promotionPrice(product.getPromotionPrice())
                .originalPrice(product.getOriginalPrice())
                .description(product.getDescription())
                .soldCount(product.getSoldCount())
                .image(product.getImage())
                .categoryId(product.getCategory().getId())


                .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }
}
