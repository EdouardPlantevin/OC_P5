package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@Rollback
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:script.sql")

public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findById_WithValidId_ShouldReturnSession() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/session/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Yoga Vinyasa Débutant"))
                .andExpect(jsonPath("$.description").value("Séance de yoga dynamique parfaite pour débuter."))
                .andExpect(jsonPath("$.teacher_id").value(1));
    }


    @Test
    void findAll_ShouldReturnOkResponse() throws Exception {
        // When é Then
        mockMvc.perform(get("/api/session"))
                .andExpect(status().isOk());
    }

    @Test
    void createSession_ShouldReturnSession() throws Exception {
        // Given
        SessionDto newSessionDto = new SessionDto();
        newSessionDto.setName("Nouvelle Session de Test");
        newSessionDto.setDescription("Description de la nouvelle session de test");
        newSessionDto.setDate(new Date());
        newSessionDto.setTeacher_id(1L);

        // When & Then
        mockMvc.perform(post("/api/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSessionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nouvelle Session de Test"))
                .andExpect(jsonPath("$.description").value("Description de la nouvelle session de test"))
                .andExpect(jsonPath("$.teacher_id").value(1))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void updateSession_ShouldReturnSession() throws Exception {
        // Given
        SessionDto updateSessionDto = new SessionDto();
        updateSessionDto.setName("Yoga Vinyasa Intermédiaire");
        updateSessionDto.setDescription("Séance de yoga dynamique pour niveau intermédiaire");
        updateSessionDto.setDate(new Date());
        updateSessionDto.setTeacher_id(1L);

        // When & Then
        mockMvc.perform(put("/api/session/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateSessionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Yoga Vinyasa Intermédiaire"))
                .andExpect(jsonPath("$.description").value("Séance de yoga dynamique pour niveau intermédiaire"))
                .andExpect(jsonPath("$.teacher_id").value(1));
    }

    @Test
    void deleteSession_ShouldReturnOk() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/session/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSession_WithInvalidId_ShouldReturnNotFound() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/session/999"))
                .andExpect(status().isNotFound());
    }


    @Test
    void participateSession_ShouldReturnOk() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/session/1/participate/1"))
                .andExpect(status().isOk());
    }

    @Test
    void noLongerParticipateSession_ShouldReturnOk() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/session/1/participate/2"))
                .andExpect(status().isOk());
    }

    @Test
    void findById_WithInvalidId_ShouldReturnNotFound() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/session/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findById_WithInvalidFormat_ShouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/session/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createSession_WithMissingName_ShouldReturnBadRequest() throws Exception {
        // Given
        SessionDto sessionDto = new SessionDto();
        // Name manquant
        sessionDto.setDescription("Description de la session");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);

        // When & Then
        mockMvc.perform(post("/api/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createSession_WithMissingDescription_ShouldReturnBadRequest() throws Exception {
        // Given
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Nouvelle Session");
        // Description manquante
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);

        // When & Then
        mockMvc.perform(post("/api/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createSession_WithMissingDate_ShouldReturnBadRequest() throws Exception {
        // Given
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Nouvelle Session");
        sessionDto.setDescription("Description de la session");
        // Date manquante
        sessionDto.setTeacher_id(1L);

        // When & Then
        mockMvc.perform(post("/api/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createSession_WithMissingTeacherId_ShouldReturnBadRequest() throws Exception {
        // Given
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Nouvelle Session");
        sessionDto.setDescription("Description de la session");
        sessionDto.setDate(new Date());
        // Teacher_id manquant

        // When & Then
        mockMvc.perform(post("/api/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateSession_WithMissingName_ShouldReturnBadRequest() throws Exception {
        // Given
        SessionDto sessionDto = new SessionDto();
        // Name manquant
        sessionDto.setDescription("Description modifiée");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);

        // When & Then
        mockMvc.perform(put("/api/session/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateSession_WithMissingDescription_ShouldReturnBadRequest() throws Exception {
        // Given
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Session Modifiée");
        // Description manquante
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);

        // When & Then
        mockMvc.perform(put("/api/session/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateSession_WithMissingDate_ShouldReturnBadRequest() throws Exception {
        // Given
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Session Modifiée");
        sessionDto.setDescription("Description modifiée");
        // Date manquante
        sessionDto.setTeacher_id(1L);

        // When & Then
        mockMvc.perform(put("/api/session/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateSession_WithMissingTeacherId_ShouldReturnBadRequest() throws Exception {
        // Given
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Session Modifiée");
        sessionDto.setDescription("Description modifiée");
        sessionDto.setDate(new Date());
        // Teacher_id manquant

        // When & Then
        mockMvc.perform(put("/api/session/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isBadRequest());
    }

}