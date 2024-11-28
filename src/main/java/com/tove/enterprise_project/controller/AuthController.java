package com.tove.enterprise_project.controller;

import com.tove.enterprise_project.model.dto.AppUserDTO;
import com.tove.enterprise_project.model.dto.AuthResponseDTO;
import com.tove.enterprise_project.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AppUserDTO appUserDTO) {

         System.out.println(appUserDTO);

         return ResponseEntity.ok(authService.verify(appUserDTO));

    }
}
