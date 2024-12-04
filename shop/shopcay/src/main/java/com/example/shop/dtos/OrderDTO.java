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
public class OrderDTO {
    @JsonProperty("user_id")
//    @NotNull(message = "User id is not null")
    private Long userId;



    @JsonProperty("fullname")
    private String fullName;
    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    private String note;

    @JsonProperty("payment_method")
    private String paymentMethod;

    private String status = "Processing";

}
