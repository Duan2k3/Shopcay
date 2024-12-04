package com.example.shop.services;

import com.example.shop.dtos.UserDTO;
import com.example.shop.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO);
    String login(String accountLogin , String password , Long roleId) throws Exception;
}
