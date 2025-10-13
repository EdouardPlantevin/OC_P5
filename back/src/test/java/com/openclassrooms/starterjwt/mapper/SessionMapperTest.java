package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionMapperTest {

    private SessionMapper sessionMapper;
    
    @Mock
    private TeacherService teacherService;
    
    @Mock
    private UserService userService;

    private Session testSession;
    private SessionDto testSessionDto;
    private Teacher testTeacher;
    private User testUser;

    @BeforeEach
    void setUp() {
        sessionMapper = new SessionMapperImpl();
        
        ReflectionTestUtils.setField(sessionMapper, "teacherService", teacherService);
        ReflectionTestUtils.setField(sessionMapper, "userService", userService);

        testTeacher = new Teacher();
        testTeacher.setId(1L);
        testTeacher.setFirstName("teacher_firstname_1");
        testTeacher.setLastName("teacher_lastname_1");

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("user1@test.com");
        testUser.setFirstName("user_firstname_1");
        testUser.setLastName("user_lastname_1");

        testSession = new Session();
        testSession.setId(1L);
        testSession.setName("Test Session");
        testSession.setDescription("Test Description");
        testSession.setDate(new Date());
        testSession.setTeacher(testTeacher);
        testSession.setUsers(Arrays.asList(testUser));

        testSessionDto = new SessionDto();
        testSessionDto.setId(1L);
        testSessionDto.setName("Test Session");
        testSessionDto.setDescription("Test Description");
        testSessionDto.setDate(new Date());
        testSessionDto.setTeacher_id(1L);
        testSessionDto.setUsers(Arrays.asList(1L));
    }

    @Test
    void toDto_WithValidSession_ShouldMapCorrectly() {
        // When
        SessionDto result = sessionMapper.toDto(testSession);

        // Then
        assertNotNull(result);
        assertEquals(testSession.getId(), result.getId());
        assertEquals(testSession.getName(), result.getName());
        assertEquals(testSession.getDescription(), result.getDescription());
        assertEquals(testSession.getDate(), result.getDate());
        assertEquals(testSession.getTeacher().getId(), result.getTeacher_id());
        assertEquals(1, result.getUsers().size());
        assertEquals(testSession.getUsers().get(0).getId(), result.getUsers().get(0));
    }

    @Test
    void toEntity_WithValidSessionDto_ShouldMapCorrectly() {
        // Given
        when(teacherService.findById(1L)).thenReturn(testTeacher);
        when(userService.findById(1L)).thenReturn(testUser);

        // When
        Session result = sessionMapper.toEntity(testSessionDto);

        // Then
        assertNotNull(result);
        assertEquals(testSessionDto.getId(), result.getId());
        assertEquals(testSessionDto.getName(), result.getName());
        assertEquals(testSessionDto.getDescription(), result.getDescription());
        assertEquals(testSessionDto.getDate(), result.getDate());
        assertNotNull(result.getTeacher());
        assertEquals(testSessionDto.getTeacher_id(), result.getTeacher().getId());
        assertEquals(1, result.getUsers().size());
        assertEquals(testSessionDto.getUsers().get(0), result.getUsers().get(0).getId());
        
        verify(teacherService).findById(1L);
        verify(userService).findById(1L);
    }

    @Test
    void toDto_WithNullSession_ShouldReturnNull() {
        // When
        SessionDto result = sessionMapper.toDto((Session) null);

        // Then
        assertNull(result);
    }

    @Test
    void toEntity_WithNullSessionDto_ShouldReturnNull() {
        // When
        Session result = sessionMapper.toEntity((SessionDto) null);

        // Then
        assertNull(result);
    }

}
