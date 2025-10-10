package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:script.sql")
public class TeacherServiceTest {

    @Autowired
    private TeacherService teacherService;

    @Test
    void findAll_ShouldReturnAllTeachers() {
        // When
        List<Teacher> result = teacherService.findAll();

        // Then
        assertNotNull(result);
        assertTrue(result.size() >= 3);
    }

    @Test
    void findById_WithValidId_ShouldReturnTeacher() {
        // When
        Teacher result = teacherService.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("teacher_firstname_1", result.getFirstName());
        assertEquals("teacher_lastname_1", result.getLastName());
    }

    @Test
    void findById_WithInvalidId_ShouldReturnNull() {
        // When
        Teacher result = teacherService.findById(999L);

        // Then
        assertNull(result);
    }
}
