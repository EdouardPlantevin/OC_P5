package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtResponseTest {

    @Test
    void testJwtResponse() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "test@test.com", "test", "test", true);
        
        assertEquals("token", jwtResponse.getToken());
        assertEquals("Bearer", jwtResponse.getType());
        assertEquals(1L, jwtResponse.getId());
        assertEquals("test@test.com", jwtResponse.getUsername());
        assertEquals("test", jwtResponse.getFirstName());
        assertEquals("test", jwtResponse.getLastName());
        assertTrue(jwtResponse.getAdmin());
        assertNotNull(jwtResponse.toString());
    }

    @Test
    void testJwtResponseSetters() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "test@test.com", "test", "test", true);
        
        jwtResponse.setToken("newToken");
        jwtResponse.setType("newType");
        jwtResponse.setId(2L);
        jwtResponse.setUsername("new@test.com");
        jwtResponse.setFirstName("newFirst");
        jwtResponse.setLastName("newLast");
        jwtResponse.setAdmin(false);
        
        assertEquals("newToken", jwtResponse.getToken());
        assertEquals("newType", jwtResponse.getType());
        assertEquals(2L, jwtResponse.getId());
        assertEquals("new@test.com", jwtResponse.getUsername());
        assertEquals("newFirst", jwtResponse.getFirstName());
        assertEquals("newLast", jwtResponse.getLastName());
        assertFalse(jwtResponse.getAdmin());
    }
}
