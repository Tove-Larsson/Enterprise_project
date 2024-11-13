package com.tove.enterprise_project.controller;

import com.tove.enterprise_project.model.dto.AppUserDTO;
import com.tove.enterprise_project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AppUserDTO> registerUser(@Valid @RequestBody AppUserDTO appUserDTO) {

        return userService.createUser(appUserDTO);
    }


}
