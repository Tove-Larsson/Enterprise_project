package com.tove.enterprise_project.controller;

import com.tove.enterprise_project.model.dto.AppUserDTO;
import com.tove.enterprise_project.model.dto.TokenDTO;
import com.tove.enterprise_project.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;


    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AppUserDTO appUserDTO) {

         System.out.println(appUserDTO);

         return ResponseEntity.ok(userService.verify(appUserDTO));

    }
}
