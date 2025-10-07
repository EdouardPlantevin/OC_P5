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

}
