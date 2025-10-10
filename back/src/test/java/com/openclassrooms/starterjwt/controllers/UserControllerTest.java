package com.openclassrooms.starterjwt.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@Rollback
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:script.sql")

public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findById_WithValidId_ShouldReturnUser() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("user1@test.com"))
                .andExpect(jsonPath("$.firstName").value("user_firstname_1"))
                .andExpect(jsonPath("$.lastName").value("user_lastname_1"))
                .andExpect(jsonPath("$.admin").value(true));
    }

    @Test
    void findById_WithInvalidId_ShouldReturnNotFound() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/user/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_WithInvalidId_ShouldReturnNotFound() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/user/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_ShouldReturnOk() throws Exception {
        // Given - Simuler l'authentification de l'utilisateur ID 2
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                "user2@test.com", "password", java.util.Collections.emptyList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // When & Then
        mockMvc.perform(delete("/api/user/2"))
                .andExpect(status().isOk());
    }

}