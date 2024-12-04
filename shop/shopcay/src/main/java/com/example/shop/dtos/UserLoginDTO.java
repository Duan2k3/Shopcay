package com.example.shop.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    @JsonProperty("account_login")
    @NotBlank(message = "Account is required")
    private String accountLogin;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Min(value = 2, message = "You must enter role's Id")
    @JsonProperty("role_id")
    private Long roleId;

}
