package com.example.shop.controllers;

import com.example.shop.components.LocalizationUtil;
import com.example.shop.dtos.UserDTO;
import com.example.shop.dtos.UserLoginDTO;
import com.example.shop.models.User;
import com.example.shop.responses.LoginResponse;
import com.example.shop.responses.RegisterResponse;
import com.example.shop.services.IUserService;
import com.example.shop.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final LocalizationUtil localizationUtil;
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result

            ){
        RegisterResponse registerResponse = new RegisterResponse();

        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();

            registerResponse.setMessage(errorMessages.toString());
            return ResponseEntity.badRequest().body(registerResponse);
        }
        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())){
            registerResponse.setMessage(localizationUtil.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH));
        return ResponseEntity.badRequest().body(registerResponse);
        }
        try {
            User user = userService.createUser(userDTO);
            registerResponse.setMessage(localizationUtil.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY));
            registerResponse.setUser(user);
            return ResponseEntity.ok(registerResponse);

        }
        catch (Exception e){
            registerResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(registerResponse);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO

            ){try {
                String token = userService.login(userLoginDTO.getAccountLogin(),userLoginDTO.getPassword(),userLoginDTO.getRoleId()==null ? 1 : userLoginDTO.getRoleId());

                return ResponseEntity.ok(LoginResponse.builder()
                                .message(localizationUtil.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                                .token(token)

                        .build());
    }
    catch (Exception e){
                return ResponseEntity.badRequest().body(LoginResponse.builder()
                                .message(localizationUtil.getLocalizedMessage(MessageKeys.LOGIN_FAILED,e.getMessage()))
                        .build());
    }
    }
}
