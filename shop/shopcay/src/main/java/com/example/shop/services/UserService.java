package com.example.shop.services;

import com.example.shop.components.JwtTokenUtil;
import com.example.shop.components.LocalizationUtil;
import com.example.shop.dtos.UserDTO;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.Role;
import com.example.shop.models.User;
import com.example.shop.repositories.RoleRepository;
import com.example.shop.repositories.UserRepository;
import com.example.shop.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
      private final UserRepository userRepository;
      private final RoleRepository roleRepository;
      private final PasswordEncoder passwordEncoder;
      private final JwtTokenUtil jwtTokenUtil;
      private final AuthenticationManager authenticationManager;
      private final LocalizationUtil localizationUtil;

    @Override
    public User createUser(UserDTO userDTO) {
        String accountLogin = userDTO.getAccountLogin();
        if(userRepository.existsByAccountLogin(accountLogin)){
            throw new DataIntegrityViolationException("Account already exists");

        }
        try {
            Role role = roleRepository.findById(userDTO.getRoleId())
                    .orElseThrow(() -> new DataNotFoundException(localizationUtil.getLocalizedMessage(MessageKeys.ROLE_DOES_NOT_EXISTS)));
            if(role.getName().toUpperCase().equals(role.ADMIN)){
                throw new DataNotFoundException("You cannot register an account");
            }
            User user = User.builder()
                    .fullName(userDTO.getFullName())
                    .address(userDTO.getAddress())
                    .accountLogin(userDTO.getAccountLogin())
                    .password(userDTO.getPassword())
                    .phoneNumber(userDTO.getPhone_number())
                    .dateOfBirth(userDTO.getDateOfBirth())
                    .facebookAccountId(userDTO.getFacebookAccountId())
                    .googleAccountId(userDTO.getGoogleAccountId())
                    .active(true)

                    .build();
            user.setRole(role);

            if(userDTO.getGoogleAccountId()==0 && userDTO.getFacebookAccountId()==0){
                String password = userDTO.getPassword();
                String encodeedPassword = passwordEncoder.encode(password);
                user.setPassword(encodeedPassword);

            }
            return userRepository.save(user);
        } catch (DataNotFoundException e) {
            // Xử lý ngoại lệ, ví dụ: log lỗi hoặc trả về phản hồi lỗi
            System.out.println("Lỗi: " + e.getMessage());
        }




return null;
    }

    @Override
    public String login(String accountLogin, String password, Long roleId) throws Exception {
        Optional<User> optionalUser = userRepository.findByAccountLogin(accountLogin);
        if (optionalUser.isEmpty()){
            throw new DataNotFoundException(localizationUtil.getLocalizedMessage(MessageKeys.WRONG_PHONE_PASSWORD));
        }
        User existingUser = optionalUser.get();
        if (existingUser.getFacebookAccountId()==0 &&
                existingUser.getGoogleAccountId()==0

        ){
            if(!passwordEncoder.matches(password,existingUser.getPassword())){
                throw new BadCredentialsException(localizationUtil.getLocalizedMessage(MessageKeys.WRONG_PHONE_PASSWORD));
            }
        }
        Optional<Role> optionalRole= roleRepository.findById(roleId);
        if(optionalRole.isEmpty() || !roleId.equals(existingUser.getRole().getId())){
            throw new DataNotFoundException(localizationUtil.getLocalizedMessage(MessageKeys.ROLE_DOES_NOT_EXISTS));
        }
//        if(!optionalUser.get().isActive()) {
//            throw new DataNotFoundException(localizationUtil.getLocalizedMessage(MessageKeys.USER_IS_LOCKED));
//        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken
                (accountLogin,password,existingUser.getAuthorities()

        );
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return jwtTokenUtil.generteToken(existingUser);


    }
}
