package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTest {

    @Test
    void testUserEntity() {
        User user = new User();
        user.equals(new User());
        user.hashCode();
        user.toString();
        assertNotNull(user.toString());
    }

    @Test
    void testUserEntityBuilder() {
        User user = new User();
        user.equals(User.builder()
                .email("test@test.com")
                .firstName("test")
                .lastName("test")
                .password("password")
                .admin(false)
                .build());
        assertNotNull(user.toString());
    }
}
