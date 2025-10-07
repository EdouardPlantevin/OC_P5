package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SignupRequestTest {

    @Test
    void testGettersAndSetters() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@test.com");
        request.setFirstName("test");
        request.setLastName("test");
        request.setPassword("password");

        assertEquals("test@test.com", request.getEmail());
        assertEquals("test", request.getFirstName());
        assertEquals("test", request.getLastName());
        assertEquals("password", request.getPassword());
    }

    @Test
    void testToString() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@test.com");
        request.setFirstName("test");
        request.setLastName("test");
        request.setPassword("password");
        
        assertNotNull(request.toString());
    }
}
