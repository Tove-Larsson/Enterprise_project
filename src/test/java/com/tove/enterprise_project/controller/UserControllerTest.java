package com.tove.enterprise_project.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    private final MockMvc mockMvc;

    private final UserController userController;

    @Autowired
    UserControllerTest(MockMvc mockMvc, UserController userController) {
        this.mockMvc = mockMvc;
        this.userController = userController;
    }

    @Test
    void testRegisterUser() throws Exception {
        mockMvc.perform(post("/user/register")
                        .contentType("application/json")
                        .content(""" 
                                {
                                  "username": "batman",
                                  "password": "brucewayne"
                                }
                                """))
                .andExpect(status().isCreated());
    }


    @Test
    @WithMockUser(username = "batman", roles = "USER")
    @DisplayName("Testing if authenticated user can fetch their data")
    void fetchAuthUser() throws Exception {
        mockMvc.perform(get("/user/test"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"username\":\"batman\"}"));
    }



}