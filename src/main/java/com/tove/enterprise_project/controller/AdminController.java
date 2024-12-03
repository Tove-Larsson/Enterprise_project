package com.tove.enterprise_project.controller;

import com.tove.enterprise_project.model.dto.AppUserDTO;
import com.tove.enterprise_project.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<AppUserDTO> adminHomePage(@AuthenticationPrincipal UserDetails userDetails) {

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


    @DeleteMapping("delete-user")
    public ResponseEntity<String> deleteUser(@RequestParam String username) {

        return userService.adminDeleteUser(username);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<AppUserDTO> addUser(@RequestBody AppUserDTO appUserDTO) {

        return userService.createAdminUser(appUserDTO);
    }

}
