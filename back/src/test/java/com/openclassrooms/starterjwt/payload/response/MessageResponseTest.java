package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageResponseTest {

    @Test
    void testMessageResponse() {
        MessageResponse messageResponse = new MessageResponse("Test message");
        
        assertEquals("Test message", messageResponse.getMessage());
        assertNotNull(messageResponse.toString());
    }

    @Test
    void testMessageResponseSetter() {
        MessageResponse messageResponse = new MessageResponse("Test message");
        
        messageResponse.setMessage("New message");
        
        assertEquals("New message", messageResponse.getMessage());
    }
}
