package com.example.shop.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    @JsonProperty("account_login")
    @NotBlank(message = "Account is not null")
    private String accountLogin;

    @JsonProperty("full_name")
    @NotBlank(message = "Full Name is not null")
    private String fullName;

    @JsonProperty("retype_password")
    private String retypePassword;
    @JsonProperty("phone_number")
    private String phone_number;
    @JsonProperty("password")
    @NotBlank(message = "password is not null")
    private String password;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("address")
    private String address;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;

    @NotNull(message = "Role ID is required")
    @JsonProperty("role_id")
    private Long roleId;

}
