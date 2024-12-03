package com.tove.enterprise_project.controller;

import static com.tove.enterprise_project.authorities.UserRole.USER;
import static org.assertj.core.api.Assertions.assertThat;

import com.tove.enterprise_project.model.AppUser;
import com.tove.enterprise_project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;

    private final UserController userController;

    private final UserRepository userRepository;


    @Autowired
    UserControllerTest(MockMvc mockMvc, PasswordEncoder passwordEncoder, UserController userController, UserRepository userRepository) {
        this.mockMvc = mockMvc;
        this.passwordEncoder = passwordEncoder;
        this.userController = userController;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setUpTestUser() throws Exception {
        AppUser testUser = new AppUser();
        testUser.setUsername("TestUser");
        testUser.setPassword(passwordEncoder.encode("TestPassword"));
        testUser.setUserRole(USER);
        testUser.setAccountNonExpired(true);
        testUser.setAccountNonLocked(true);
        testUser.setCredentialsNonExpired(true);
        testUser.setEnabled(true);
        userRepository.save(testUser);
    }


    @Test
    @DisplayName("Testing if controller is not null")
    void testControllerIsNotNull() throws Exception {
        assertThat(userController).isNotNull();
    }

    @Test
    @DisplayName("Testing if creating a user works")
    void testRegisterUser() throws Exception {
        mockMvc.perform(post("/user/register")
                        .contentType("application/json")
                        .content(""" 
                                {
                                  "username": "GanonDorf",
                                  "password": "IhateLink"
                                }
                                """))
                .andExpect(status().isCreated());
    }


    @Test
    @WithMockUser(username = "GanonDorf", roles = "USER")
    @DisplayName("Testing if authenticated user can fetch their data")
    void testFetchAuthUser() throws Exception {
        mockMvc.perform(get("/user/test"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"username\":\"GanonDorf\"}"));
    }

    @Test
    @DisplayName("Test if registering a user with already existing username works")
    void testRegisterUserWithExistingUsername() throws Exception {
        mockMvc.perform(post("/user/register")
                .contentType("application/json")
                .content("""
                        {
                        "username": "TestUser",
                        "password": "1234567"
                        }
                        
                        """))
                .andExpect(status().isConflict());

    }

}