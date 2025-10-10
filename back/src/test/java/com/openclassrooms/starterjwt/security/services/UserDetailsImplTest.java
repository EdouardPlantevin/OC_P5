package com.openclassrooms.starterjwt.security.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDetailsImplTest {

    @Test
    void testUserDetailsImplBuilder() {
        // When
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("user1@test.com")
                .firstName("user_firstname_1")
                .lastName("user_lastname_1")
                .password("password")
                .admin(true)
                .build();

        // Then
        assertEquals(1L, userDetails.getId());
        assertEquals("user1@test.com", userDetails.getUsername());
        assertEquals("user_firstname_1", userDetails.getFirstName());
        assertEquals("user_lastname_1", userDetails.getLastName());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAdmin());
    }


    @Test
    void testAccountStatus() {
        // Given
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("user1@test.com")
                .firstName("user_firstname_1")
                .lastName("user_lastname_1")
                .password("password")
                .build();

        // Then
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testEquals() {
        // Given
        UserDetailsImpl userDetails1 = UserDetailsImpl.builder()
                .id(1L)
                .username("user1@test.com")
                .firstName("user_firstname_1")
                .lastName("user_lastname_1")
                .password("password")
                .build();

        UserDetailsImpl userDetails2 = UserDetailsImpl.builder()
                .id(1L)
                .username("user2@test.com")
                .firstName("user_firstname_2")
                .lastName("user_lastname_2")
                .password("password2")
                .build();

        UserDetailsImpl userDetails3 = UserDetailsImpl.builder()
                .id(2L)
                .username("user1@test.com")
                .firstName("user_firstname_1")
                .lastName("user_lastname_1")
                .password("password")
                .build();

        // Then
        assertEquals(userDetails1, userDetails2);
        assertNotEquals(userDetails1, userDetails3);
        assertEquals(userDetails1, userDetails1);
        assertNotEquals(userDetails1, null);
        assertNotEquals(userDetails1, "not a UserDetailsImpl");
    }

}
