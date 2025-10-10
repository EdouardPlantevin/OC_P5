package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:script.sql")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void findById_WithValidId_ShouldReturnUser() {
        // When
        User result = userService.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("user1@test.com", result.getEmail());
        assertEquals("user_firstname_1", result.getFirstName());
        assertEquals("user_lastname_1", result.getLastName());
        assertTrue(result.isAdmin());
    }

    @Test
    void findById_WithInvalidId_ShouldReturnNull() {
        // When
        User result = userService.findById(999L);

        // Then
        assertNull(result);
    }

    @Test
    void delete_ShouldDeleteUser() {
        // Given
        Long userId = 3L;

        // When
        userService.delete(userId);

        // Then
        User deletedUser = userService.findById(userId);
        assertNull(deletedUser);
    }
}
