package com.openclassrooms.starterjwt.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@Rollback
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:script.sql")

public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll_ShouldReturnOkResponse() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/teacher"))
                .andExpect(status().isOk());
    }

    @Test
    void findById_WithValidId_ShouldReturnTeacher() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/teacher/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("teacher_firstname_1"))
                .andExpect(jsonPath("$.lastName").value("teacher_lastname_1"));
    }

    @Test
    void findById_WithInvalidId_ShouldReturnNotFound() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/teacher/123"))
                .andExpect(status().isNotFound());
    }

}