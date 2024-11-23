package com.tove.enterprise_project.controller;

import com.tove.enterprise_project.model.AppUser;
import com.tove.enterprise_project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController()
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;


    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AppUser appUser) {
        String token = userService.verify(appUser);
        if (!token.equals("Failed authentication")) {
            return ResponseEntity.ok().body(Map.of("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
