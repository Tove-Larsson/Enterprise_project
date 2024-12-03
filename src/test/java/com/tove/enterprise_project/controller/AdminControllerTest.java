package com.tove.enterprise_project.controller;

import com.tove.enterprise_project.dao.impl.UserDAO;
import com.tove.enterprise_project.model.AppUser;
import com.tove.enterprise_project.repository.UserRepository;
import com.tove.enterprise_project.service.UserService;
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

import static com.tove.enterprise_project.authorities.UserRole.USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdminControllerTest {

    private final MockMvc mockMvc;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserDAO userDAO;

    @Autowired
    AdminControllerTest(MockMvc mockMvc, UserRepository userRepository, PasswordEncoder passwordEncoder, UserDAO userDAO) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
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
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Testing if admin can access admin")
    public void testAdminHomePageAdminAuth() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Testing if user can access admin")
    public void testAdminHomePageUserAuth() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Testing if admin can delete a user")
    public void testAdminDeleteUserAsAdmin() throws Exception {
        mockMvc.perform(delete("/admin/delete-user")
                        .param("username", "TestUser"))
                .andExpect(status().isNoContent());

        boolean userExists = userDAO.findByUsername("TestUser").isPresent();
        assertThat(userExists).isFalse();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Test delete User when not found")
    public void testDeleteUserNotFoundAsAdmin() throws Exception {
        mockMvc.perform(delete("/admin/delete-user")
                        .param("username", "nonExistentUser"))
                .andExpect(content().string(containsString("nonExistentUser could not be found")));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Test delete User as a User")
    public void testDeleteUserAsUser() throws Exception {
        mockMvc.perform(delete("/admin/delete-user")
                        .param("username", "TestUser"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Test creating admin user as an Admin")
    public void testCreateAdmin() throws Exception {
        mockMvc.perform(post("/admin/create-admin")
                        .contentType("application/json")
                        .content(""" 
                                {
                                  "username": "Grisen",
                                  "password": "TestPassword"
                                }
                                """))
                .andExpect(status().isCreated());
    }

}


