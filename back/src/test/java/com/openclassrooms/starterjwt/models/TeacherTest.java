package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TeacherTest {

    @Test
    void testTeacherEntity() {
        Teacher teacher = new Teacher();
        teacher.equals(new Teacher());
        teacher.hashCode();
        teacher.toString();
        assertNotNull(teacher.toString());
    }

    @Test
    void testTeacherEntityBuilder() {
        Teacher teacher = new Teacher();
        teacher.equals(Teacher.builder().build());
        assertNotNull(teacher.toString());
    }

    @Test
    void testTeacherEquals() {
        Teacher teacher1 = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        Teacher teacher2 = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        Teacher teacher3 = Teacher.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .build();

        assertEquals(teacher1, teacher2);
        assertNotEquals(teacher1, teacher3);
        assertNotEquals(teacher1, null);
        assertNotEquals(teacher1, "not a teacher");
        assertEquals(teacher1, teacher1);
    }

    @Test
    void testTeacherHashCode() {
        Teacher teacher1 = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        Teacher teacher2 = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        Teacher teacher3 = Teacher.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .build();

        assertEquals(teacher1.hashCode(), teacher2.hashCode());
        assertNotEquals(teacher1.hashCode(), teacher3.hashCode());
    }
}
