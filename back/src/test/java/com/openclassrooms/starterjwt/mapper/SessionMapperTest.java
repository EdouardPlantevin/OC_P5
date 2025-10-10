package com.openclassrooms.starterjwt.mapper;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:script.sql")
public class SessionMapperTest {

    @Autowired
    private SessionMapper sessionMapper;

    @Autowired
    private SessionRepository sessionRepository;

    private Session testSession;
    private SessionDto testSessionDto;

    @BeforeEach
    void setUp() {
        testSession = sessionRepository.findById(1L).orElse(null);

        testSessionDto = new SessionDto();
        testSessionDto.setId(1L);
        testSessionDto.setName("Yoga Vinyasa Débutant");
        testSessionDto.setDescription("Séance de yoga dynamique parfaite pour débuter.");
        testSessionDto.setDate(new Date());
        testSessionDto.setTeacher_id(1L);
        testSessionDto.setUsers(Arrays.asList(1L, 2L));
    }

    @Test
    void toDto_ShouldMapSessionToSessionDto() {
        // When
        SessionDto result = sessionMapper.toDto(testSession);

        // Then
        assertNotNull(result);
        assertEquals(testSession.getId(), result.getId());
        assertEquals(testSession.getName(), result.getName());
    }

    @Test
    void toEntity_ShouldMapSessionDtoToSession() {
        // When
        Session result = sessionMapper.toEntity(testSessionDto);

        // Then
        assertNotNull(result);
        assertEquals(testSessionDto.getId(), result.getId());
        assertEquals(testSessionDto.getName(), result.getName());
    }

    @Test
    void toDto_WithList_ShouldMapCorrectly() {
        // Given
        List<Session> sessions = Arrays.asList(testSession);

        // When
        List<SessionDto> result = sessionMapper.toDto(sessions);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testSession.getId(), result.get(0).getId());
        assertEquals(testSession.getName(), result.get(0).getName());
    }

    @Test
    void toEntity_WithList_ShouldMapCorrectly() {
        // Given
        List<SessionDto> sessionDtos = Arrays.asList(testSessionDto);

        // When
        List<Session> result = sessionMapper.toEntity(sessionDtos);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testSessionDto.getId(), result.get(0).getId());
        assertEquals(testSessionDto.getName(), result.get(0).getName());
    }

    @Test
    void toDto_WithEmptyList_ShouldReturnEmptyList() {
        // Given
        List<Session> emptySessions = Collections.emptyList();

        // When
        List<SessionDto> result = sessionMapper.toDto(emptySessions);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toEntity_WithEmptyList_ShouldReturnEmptyList() {
        // Given
        List<SessionDto> emptySessionDtos = Collections.emptyList();

        // When
        List<Session> result = sessionMapper.toEntity(emptySessionDtos);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toDto_WithNullList_ShouldReturnNull() {
        // When
        List<SessionDto> result = sessionMapper.toDto((List<Session>) null);

        // Then
        assertNull(result);
    }

    @Test
    void toEntity_WithNullList_ShouldReturnNull() {
        // When
        List<Session> result = sessionMapper.toEntity((List<SessionDto>) null);

        // Then
        assertNull(result);
    }

    @Test
    void toDto_WithMultipleSessions_ShouldMapAll() {
        // Given
        Session session2 = sessionRepository.findById(2L).orElse(null);
        List<Session> sessions = Arrays.asList(testSession, session2);

        // When
        List<SessionDto> result = sessionMapper.toDto(sessions);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testSession.getId(), result.get(0).getId());
        assertEquals(session2.getId(), result.get(1).getId());
    }

    @Test
    void toEntity_WithMultipleSessionDtos_ShouldMapAll() {
        // Given
        SessionDto sessionDto2 = new SessionDto();
        sessionDto2.setId(2L);
        sessionDto2.setName("Yoga Hatha");
        sessionDto2.setDescription("Séance de yoga doux et relaxant.");
        sessionDto2.setDate(new Date());
        sessionDto2.setTeacher_id(2L);
        sessionDto2.setUsers(Arrays.asList(1L));

        List<SessionDto> sessionDtos = Arrays.asList(testSessionDto, sessionDto2);

        // When
        List<Session> result = sessionMapper.toEntity(sessionDtos);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testSessionDto.getId(), result.get(0).getId());
        assertEquals(sessionDto2.getId(), result.get(1).getId());
    }

}
