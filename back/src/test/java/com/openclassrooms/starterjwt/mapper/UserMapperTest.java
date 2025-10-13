package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    private UserMapper userMapper;
    private User testUser;
    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("user1@test.com");
        testUser.setFirstName("user_firstname_1");
        testUser.setLastName("user_lastname_1");
        testUser.setPassword("password");
        testUser.setAdmin(true);

        testUserDto = new UserDto();
        testUserDto.setId(1L);
        testUserDto.setEmail("user1@test.com");
        testUserDto.setFirstName("user_firstname_1");
        testUserDto.setLastName("user_lastname_1");
        testUserDto.setPassword("password");
        testUserDto.setAdmin(true);
    }

    @Test
    void toDto_WithValidUser_ShouldMapCorrectly() {
        // When
        UserDto result = userMapper.toDto(testUser);

        // Then
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(testUser.getFirstName(), result.getFirstName());
        assertEquals(testUser.getLastName(), result.getLastName());
        assertEquals(testUser.getPassword(), result.getPassword());
        assertEquals(testUser.isAdmin(), result.isAdmin());
    }

    @Test
    void toEntity_WithValidUserDto_ShouldMapCorrectly() {
        // When
        User result = userMapper.toEntity(testUserDto);

        // Then
        assertNotNull(result);
        assertEquals(testUserDto.getId(), result.getId());
        assertEquals(testUserDto.getEmail(), result.getEmail());
        assertEquals(testUserDto.getFirstName(), result.getFirstName());
        assertEquals(testUserDto.getLastName(), result.getLastName());
        assertEquals(testUserDto.getPassword(), result.getPassword());
        assertEquals(testUserDto.isAdmin(), result.isAdmin());
    }

    @Test
    void toDto_WithList_ShouldMapCorrectly() {
        // Given
        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("user2@test.com");
        user2.setFirstName("user_firstname_2");
        user2.setLastName("user_lastname_2");
        user2.setPassword("password");
        user2.setAdmin(false);

        List<User> userList = Arrays.asList(testUser, user2);

        // When
        List<UserDto> result = userMapper.toDto(userList);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testUser.getId(), result.get(0).getId());
        assertEquals(user2.getId(), result.get(1).getId());
    }

    @Test
    void toEntity_WithList_ShouldMapCorrectly() {
        // Given
        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);
        userDto2.setEmail("user2@test.com");
        userDto2.setFirstName("user_firstname_2");
        userDto2.setLastName("user_lastname_2");
        userDto2.setPassword("password");
        userDto2.setAdmin(false);

        List<UserDto> userDtoList = Arrays.asList(testUserDto, userDto2);

        // When
        List<User> result = userMapper.toEntity(userDtoList);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testUserDto.getId(), result.get(0).getId());
        assertEquals(userDto2.getId(), result.get(1).getId());
    }

    @Test
    void toDto_WithNullUser_ShouldReturnNull() {
        // When
        UserDto result = userMapper.toDto((User) null);

        // Then
        assertNull(result);
    }

    @Test
    void toEntity_WithNullUserDto_ShouldReturnNull() {
        // When
        User result = userMapper.toEntity((UserDto) null);

        // Then
        assertNull(result);
    }

    @Test
    void toDto_WithEmptyList_ShouldReturnEmptyList() {
        // Given
        List<User> emptyList = Arrays.asList();

        // When
        List<UserDto> result = userMapper.toDto(emptyList);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toEntity_WithEmptyList_ShouldReturnEmptyList() {
        // Given
        List<UserDto> emptyList = Arrays.asList();

        // When
        List<User> result = userMapper.toEntity(emptyList);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
