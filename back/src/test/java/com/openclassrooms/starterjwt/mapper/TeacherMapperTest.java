package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TeacherMapperTest {

    private TeacherMapper teacherMapper;
    private Teacher testTeacher;
    private TeacherDto testTeacherDto;

    @BeforeEach
    void setUp() {
        teacherMapper = Mappers.getMapper(TeacherMapper.class);

        testTeacher = new Teacher();
        testTeacher.setId(1L);
        testTeacher.setLastName("teacher_lastname_1");
        testTeacher.setFirstName("teacher_firstname_1");

        testTeacherDto = new TeacherDto();
        testTeacherDto.setId(1L);
        testTeacherDto.setLastName("teacher_lastname_1");
        testTeacherDto.setFirstName("teacher_firstname_1");
    }

    @Test
    void toDto_WithValidTeacher_ShouldMapCorrectly() {
        // When
        TeacherDto result = teacherMapper.toDto(testTeacher);

        // Then
        assertNotNull(result);
        assertEquals(testTeacher.getId(), result.getId());
        assertEquals(testTeacher.getLastName(), result.getLastName());
        assertEquals(testTeacher.getFirstName(), result.getFirstName());
    }

    @Test
    void toEntity_WithValidTeacherDto_ShouldMapCorrectly() {
        // When
        Teacher result = teacherMapper.toEntity(testTeacherDto);

        // Then
        assertNotNull(result);
        assertEquals(testTeacherDto.getId(), result.getId());
        assertEquals(testTeacherDto.getLastName(), result.getLastName());
        assertEquals(testTeacherDto.getFirstName(), result.getFirstName());
    }

    @Test
    void toDto_WithList_ShouldMapCorrectly() {
        // Given
        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setLastName("teacher_lastname_2");
        teacher2.setFirstName("teacher_firstname_2");

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
        teacherDto2.setLastName("teacher_lastname_2");
        teacherDto2.setFirstName("teacher_firstname_2");

        List<TeacherDto> teacherDtoList = Arrays.asList(testTeacherDto, teacherDto2);

        // When
        List<Teacher> result = teacherMapper.toEntity(teacherDtoList);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testTeacherDto.getId(), result.get(0).getId());
        assertEquals(teacherDto2.getId(), result.get(1).getId());
    }

    @Test
    void toDto_WithNullTeacher_ShouldReturnNull() {
        // When
        TeacherDto result = teacherMapper.toDto((Teacher) null);

        // Then
        assertNull(result);
    }

    @Test
    void toEntity_WithNullTeacherDto_ShouldReturnNull() {
        // When
        Teacher result = teacherMapper.toEntity((TeacherDto) null);

        // Then
        assertNull(result);
    }

    @Test
    void toDto_WithEmptyList_ShouldReturnEmptyList() {
        // Given
        List<Teacher> emptyList = Arrays.asList();

        // When
        List<TeacherDto> result = teacherMapper.toDto(emptyList);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toEntity_WithEmptyList_ShouldReturnEmptyList() {
        // Given
        List<TeacherDto> emptyList = Arrays.asList();

        // When
        List<Teacher> result = teacherMapper.toEntity(emptyList);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}