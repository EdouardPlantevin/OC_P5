package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:script.sql")
public class SessionServiceTest {

    @Autowired
    private SessionService sessionService;

    @Test
    void create_ShouldReturnSession() {
        // Given
        Session newSession = new Session();
        newSession.setName("Nouvelle Session");
        newSession.setDescription("Description de la nouvelle session");
        newSession.setDate(new Date());

        // When
        Session result = sessionService.create(newSession);

        // Then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Nouvelle Session", result.getName());
    }

    @Test
    void delete_ShouldDeleteSession() {
        // Given
        Long sessionId = 3L;

        // When
        sessionService.delete(sessionId);

        // Then
        Session deletedSession = sessionService.getById(sessionId);
        assertNull(deletedSession);
    }

    @Test
    void findAll_ShouldReturnAllSessions() {
        // When
        List<Session> result = sessionService.findAll();

        // Then
        assertNotNull(result);
        assertTrue(result.size() >= 3);
    }

    @Test
    void getById_WithValidId_ShouldReturnSession() {
        // When
        Session result = sessionService.getById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Yoga Vinyasa Débutant", result.getName());
    }

    @Test
    void getById_WithInvalidId_ShouldReturnNull() {
        // When
        Session result = sessionService.getById(999L);

        // Then
        assertNull(result);
    }

    @Test
    void update_ShouldReturnUpdatedSession() {
        // Given
        Session sessionToUpdate = new Session();
        sessionToUpdate.setName("Session Modifiée");
        sessionToUpdate.setDescription("Description modifiée");
        sessionToUpdate.setDate(new Date());

        // When
        Session result = sessionService.update(1L, sessionToUpdate);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Session Modifiée", result.getName());
    }

    @Test
    void participate_WithNonExistentSession_ShouldThrowNotFoundException() {
        // Given
        Long sessionId = 999L;
        Long userId = 1L;

        // When & Then
        assertThrows(NotFoundException.class, () -> sessionService.participate(sessionId, userId));
    }

    @Test
    void participate_WithNonExistentUser_ShouldThrowNotFoundException() {
        // Given
        Long sessionId = 1L;
        Long userId = 999L;

        // When & Then
        assertThrows(NotFoundException.class, () -> sessionService.participate(sessionId, userId));
    }

    @Test
    void participate_WithAlreadyParticipatingUser_ShouldThrowBadRequestException() {
        // Given
        Long sessionId = 1L;
        Long userId = 2L; // User qui participe déjà (selon le script)

        // When & Then
        assertThrows(BadRequestException.class, () -> sessionService.participate(sessionId, userId));
    }

    @Test
    void noLongerParticipate_WithValidData_ShouldRemoveUserFromSession() {
        // Given
        Long sessionId = 1L;
        Long userId = 2L; // User qui participe déjà

        // When
        sessionService.noLongerParticipate(sessionId, userId);

        // Then
        Session updatedSession = sessionService.getById(sessionId);
        assertFalse(updatedSession.getUsers().stream().anyMatch(u -> u.getId().equals(userId)));
    }

    @Test
    void noLongerParticipate_WithNonExistentSession_ShouldThrowNotFoundException() {
        // Given
        Long sessionId = 999L;
        Long userId = 1L;

        // When & Then
        assertThrows(NotFoundException.class, () -> sessionService.noLongerParticipate(sessionId, userId));
    }
}
