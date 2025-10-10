package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SessionTest {

    @Test
    void testSessionEntity() {
        Session session = new Session();
        session.equals(new Session());
        session.hashCode();
        session.toString();
        assertNotNull(session.toString());
    }

    @Test
    void testSessionEntityBuilder() {
        Session session = new Session();
        session.equals(Session.builder().build());
        assertNotNull(session.toString());
    }

    @Test
    void testSessionGettersAndSetters() {
        Session session = new Session();
        LocalDateTime now = LocalDateTime.now();
        Date date = new Date();
        Teacher teacher = new Teacher();
        List<User> users = new ArrayList<>();
        
        session.setId(1L);
        session.setName("Test Session");
        session.setDescription("Test Description");
        session.setDate(date);
        session.setTeacher(teacher);
        session.setUsers(users);
        session.setCreatedAt(now);
        session.setUpdatedAt(now);
        
        assertEquals(1L, session.getId());
        assertEquals("Test Session", session.getName());
        assertEquals("Test Description", session.getDescription());
        assertEquals(date, session.getDate());
        assertEquals(teacher, session.getTeacher());
        assertEquals(users, session.getUsers());
        assertEquals(now, session.getCreatedAt());
        assertEquals(now, session.getUpdatedAt());
    }

    @Test
    void testSessionEquals() {
        Session session1 = Session.builder()
                .id(1L)
                .name("Test Session")
                .description("Test Description")
                .build();

        Session session2 = Session.builder()
                .id(1L)
                .name("Test Session")
                .description("Test Description")
                .build();

        Session session3 = Session.builder()
                .id(2L)
                .name("Another Session")
                .description("Another Description")
                .build();

        assertEquals(session1, session2);
        assertNotEquals(session1, session3);
        assertNotEquals(session1, null);
        assertNotEquals(session1, "not a session");
        assertEquals(session1, session1);
    }

    @Test
    void testSessionEqualsWithNullId() {
        Session session1 = new Session();
        Session session2 = new Session();
        
        // Both sessions have null ID
        assertEquals(session1, session2);
        
        // Test with one null ID and one with ID
        Session session3 = Session.builder().id(1L).build();
        assertNotEquals(session1, session3);
        assertNotEquals(session3, session1);
    }

    @Test
    void testSessionHashCode() {
        Session session1 = Session.builder()
                .id(1L)
                .name("Test Session")
                .description("Test Description")
                .build();

        Session session2 = Session.builder()
                .id(1L)
                .name("Test Session")
                .description("Test Description")
                .build();

        Session session3 = Session.builder()
                .id(2L)
                .name("Another Session")
                .description("Another Description")
                .build();

        assertEquals(session1.hashCode(), session2.hashCode());
        assertNotEquals(session1.hashCode(), session3.hashCode());
    }
}