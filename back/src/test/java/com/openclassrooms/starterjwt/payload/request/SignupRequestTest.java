package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SignupRequestTest {

    @Test
    void testSignupRequestEquals() {
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("test@example.com");
        request1.setFirstName("John");
        request1.setLastName("Doe");
        request1.setPassword("password123");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("test@example.com");
        request2.setFirstName("John");
        request2.setLastName("Doe");
        request2.setPassword("password123");

        SignupRequest request3 = new SignupRequest();
        request3.setEmail("test2@example.com");
        request3.setFirstName("Jane");
        request3.setLastName("Smith");
        request3.setPassword("password456");

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertNotEquals(request1, null);
        assertNotEquals(request1, "not a signup request");
        assertEquals(request1, request1);
    }

    @Test
    void testSignupRequestEqualsWithNullFields() {
        SignupRequest request1 = new SignupRequest();
        SignupRequest request2 = new SignupRequest();

        assertEquals(request1, request2);

        request1.setEmail("test@example.com");
        assertNotEquals(request1, request2);
        assertNotEquals(request2, request1);

        request1 = new SignupRequest();
        request2 = new SignupRequest();
        request1.setFirstName("John");
        request2.setFirstName("Jane");
        assertNotEquals(request1, request2);
    }

    @Test
    void testSignupRequestEqualsWithPartialNulls() {
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("test@example.com");
        request1.setFirstName("John");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("test@example.com");
        request2.setFirstName("John");

        assertEquals(request1, request2);

        request2.setLastName("Doe");
        assertNotEquals(request1, request2);
    }

    @Test
    void testSignupRequestEqualsWithAllFieldCombinations() {
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("test@example.com");
        request1.setFirstName("John");
        request1.setLastName("Doe");
        request1.setPassword("password123");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("test@example.com");
        request2.setFirstName("John");
        request2.setLastName("Doe");
        request2.setPassword("password123");
        assertEquals(request1, request2);

        request2.setEmail("test2@example.com");
        assertNotEquals(request1, request2);

        request2.setEmail("test@example.com");
        request2.setFirstName("Jane");
        assertNotEquals(request1, request2);

        request2.setFirstName("John");
        request2.setLastName("Smith");
        assertNotEquals(request1, request2);

        request2.setLastName("Doe");
        request2.setPassword("password456");
        assertNotEquals(request1, request2);
    }

    @Test
    void testSignupRequestHashCode() {
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("test@example.com");
        request1.setFirstName("John");
        request1.setLastName("Doe");
        request1.setPassword("password123");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("test@example.com");
        request2.setFirstName("John");
        request2.setLastName("Doe");
        request2.setPassword("password123");

        SignupRequest request3 = new SignupRequest();
        request3.setEmail("test2@example.com");
        request3.setFirstName("Jane");
        request3.setLastName("Smith");
        request3.setPassword("password456");

        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }

    @Test
    void testSignupRequestHashCodeWithNullFields() {
        SignupRequest request1 = new SignupRequest();
        SignupRequest request2 = new SignupRequest();

        assertEquals(request1.hashCode(), request2.hashCode());

        request1.setEmail("test@example.com");
        request2.setEmail("test@example.com");
        assertEquals(request1.hashCode(), request2.hashCode());

        request2.setEmail("test2@example.com");
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }


    @Test
    void testToStringWithNullValues() {
        SignupRequest request = new SignupRequest();
        String toString = request.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("SignupRequest"));
    }
}
