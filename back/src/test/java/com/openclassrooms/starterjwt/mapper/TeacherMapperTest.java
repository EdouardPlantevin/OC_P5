package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:script.sql")
public class TeacherMapperTest {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private TeacherRepository teacherRepository;

    private Teacher testTeacher;
    private TeacherDto testTeacherDto;

    @BeforeEach
    void setUp() {
        testTeacher = teacherRepository.findById(1L).orElse(null);

        testTeacherDto = new TeacherDto();
        testTeacherDto.setId(1L);
        testTeacherDto.setFirstName("teacher_firstname_1");
        testTeacherDto.setLastName("teacher_lastname_1");
    }

    @Test
    void toEntity_ShouldMapTeacherDtoToTeacher() {
        // When
        Teacher result = teacherMapper.toEntity(testTeacherDto);

        // Then
        assertNotNull(result);
        assertEquals(testTeacherDto.getId(), result.getId());
        assertEquals(testTeacherDto.getFirstName(), result.getFirstName());
        assertEquals(testTeacherDto.getLastName(), result.getLastName());
    }

    @Test
    void toDto_WithList_ShouldMapCorrectly() {
        // Given
        Teacher teacher2 = teacherRepository.findById(2L).orElse(null);
        List<Teacher> teacherList = Arrays.asList(testTeacher, teacher2);

        // When
        List<TeacherDto> result = teacherMapper.toDto(teacherList);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testTeacher.getId(), result.get(0).getId());
        assertEquals(teacher2.getId(), result.get(1).getId());
    }

    @Test
    void toEntity_WithList_ShouldMapCorrectly() {
        // Given
        TeacherDto teacherDto2 = new TeacherDto();
        teacherDto2.setId(2L);
        teacherDto2.setFirstName("teacher_firstname_2");
        teacherDto2.setLastName("teacher_lastname_2");

        List<TeacherDto> teacherDtoList = Arrays.asList(testTeacherDto, teacherDto2);

        // When
        List<Teacher> result = teacherMapper.toEntity(teacherDtoList);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testTeacherDto.getId(), result.get(0).getId());
        assertEquals(teacherDto2.getId(), result.get(1).getId());
    }
}
