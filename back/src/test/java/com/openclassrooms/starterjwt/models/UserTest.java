package com.openclassrooms.starterjwt.models;

import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:script.sql")
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testUserEntity() {
        User user = new User();
        user.equals(new User());
        user.hashCode();
        user.toString();
        assertNotNull(user.toString());
    }


    @Test
    void testUserGettersAndSetters() {
        User user = new User();

        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password");
        user.setAdmin(true);

        assertEquals("test@example.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("password", user.getPassword());
        assertTrue(user.isAdmin());
    }

    @Test
    void testUserNonNullValidation() {
        User user = new User();

        assertThrows(NullPointerException.class, () -> user.setEmail(null));
        assertThrows(NullPointerException.class, () -> user.setFirstName(null));
        assertThrows(NullPointerException.class, () -> user.setLastName(null));
        assertThrows(NullPointerException.class, () -> user.setPassword(null));
    }

    @Test
    void testUserEqualsAndHashCode() {
        User user1 = userRepository.findById(1L).orElse(null);
        User user2 = userRepository.findById(2L).orElse(null);

        assertNotEquals(user1, user2);
        assertEquals(user1, user1);
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

}
