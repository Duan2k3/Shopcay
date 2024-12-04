package com.example.shop.controllers;

import com.example.shop.models.Role;
import com.example.shop.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/roles")
public class RoleController {
private final RoleService roleService;
    @GetMapping("")
    public ResponseEntity<?> getAllRoles(

    ) {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(  roles);
    }


}
