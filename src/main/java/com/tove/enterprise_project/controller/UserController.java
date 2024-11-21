package com.tove.enterprise_project.controller;

import com.tove.enterprise_project.authorities.UserRole;
import com.tove.enterprise_project.config.security.CustomUserDetail;
import com.tove.enterprise_project.model.AppUser;
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


    @DeleteMapping("/deleteUser")
    public ResponseEntity<AppUserDTO> deleteUser(Authentication authentication) {
        return userService.deleteAuthenticatedUser(authentication);
    }

    @GetMapping("/test")
    public ResponseEntity<AppUserDTO> testFetchUser(@AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails != null) {
            AppUserDTO customUerDTO = new AppUserDTO(
                    userDetails.getUsername(),
                    userDetails.getPassword()
            );

            System.out.println("User " + customUerDTO.username() + " is authenticated");

            return ResponseEntity.ok(customUerDTO);
        } else {

            return ResponseEntity.ok().body(new AppUserDTO("clarkkent", "superman"));
        }

    }

    @PostMapping("/login")
    public String login(@RequestBody AppUser appUser) {
        System.out.println(appUser);
        return userService.verify(appUser);
    }

}
