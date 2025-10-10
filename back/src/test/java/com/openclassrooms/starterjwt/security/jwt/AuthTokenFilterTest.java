package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthTokenFilterTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private AuthTokenFilter authTokenFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain filterChain;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = new MockFilterChain();
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_WithValidToken_ShouldSetAuthentication() throws ServletException, IOException {
        // Given
        String validToken = "valid.jwt.token";
        String username = "user1@test.com";
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username(username)
                .firstName("user_firstname_1")
                .lastName("user_lastname_1")
                .password("password")
                .build();

        request.addHeader("Authorization", "Bearer " + validToken);

        when(jwtUtils.validateJwtToken(validToken)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(validToken)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        // When
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(username, SecurityContextHolder.getContext().getAuthentication().getName());
        verify(jwtUtils).validateJwtToken(validToken);
        verify(jwtUtils).getUserNameFromJwtToken(validToken);
        verify(userDetailsService).loadUserByUsername(username);
    }

    @Test
    void doFilterInternal_WithInvalidToken_ShouldNotSetAuthentication() throws ServletException, IOException {
        // Given
        String invalidToken = "invalid.jwt.token";
        request.addHeader("Authorization", "Bearer " + invalidToken);

        when(jwtUtils.validateJwtToken(invalidToken)).thenReturn(false);

        // When
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(jwtUtils).validateJwtToken(invalidToken);
        verify(jwtUtils, never()).getUserNameFromJwtToken(any());
        verify(userDetailsService, never()).loadUserByUsername(any());
    }

    @Test
    void doFilterInternal_WithInvalidAuthorizationFormat_ShouldNotSetAuthentication() throws ServletException, IOException {
        // Given
        request.addHeader("Authorization", "InvalidFormat token");

        // When
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(jwtUtils, never()).validateJwtToken(any());
        verify(jwtUtils, never()).getUserNameFromJwtToken(any());
        verify(userDetailsService, never()).loadUserByUsername(any());
    }

}
