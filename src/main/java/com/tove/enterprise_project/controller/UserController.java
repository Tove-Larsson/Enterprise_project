package com.tove.enterprise_project.controller;

import com.tove.enterprise_project.model.dto.AppUserDTO;
import com.tove.enterprise_project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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


    @DeleteMapping("/delete-user")
    public ResponseEntity<AppUserDTO> deleteUser(Authentication authentication) {
        return userService.deleteAuthenticatedUser(authentication);
    }

    @GetMapping("/test")
    public ResponseEntity<AppUserDTO> testFetchUser(@AuthenticationPrincipal UserDetails userDetails) {

        System.out.println("ENTERING TEST----------");

        if (userDetails == null) {
            return ResponseEntity.ok().body(new AppUserDTO("clarkkent", "superman"));
        }

        AppUserDTO appUserDTO = new AppUserDTO(
                userDetails.getUsername(),
                userDetails.getPassword()
        );

        System.out.println("User " + appUserDTO.username() + " is authenticated");

        return ResponseEntity.ok(appUserDTO);
    }

}
