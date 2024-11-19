package com.tove.enterprise_project.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AppUserDTO(

        @Size(min = 4, max = 20, message = "Your username must be between 4-20 characters")
        @NotBlank(message = "Username can not be blank")
        String username,

        @Size(min = 7, max = 30, message = "Your password must be between 7-30 characters")
        @NotBlank(message = "Your password can not be blank")
        String password
) {
        public AppUserDTO(String username) {
                this(username, "");
        }
}
