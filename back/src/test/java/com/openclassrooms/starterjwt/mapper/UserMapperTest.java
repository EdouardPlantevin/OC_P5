package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:script.sql")
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        testUser = userRepository.findById(1L).orElse(null);

        testUserDto = new UserDto();
        testUserDto.setId(1L);
        testUserDto.setEmail("user1@test.com");
        testUserDto.setFirstName("user_firstname_1");
        testUserDto.setLastName("user_lastname_1");
        testUserDto.setPassword("password");
        testUserDto.setAdmin(true);
    }

    @Test
    void toDto_WithList_ShouldMapCorrectly() {
        // Given
        User user2 = userRepository.findById(2L).orElse(null);
        List<User> userList = Arrays.asList(testUser, user2);

        // When
        List<UserDto> result = userMapper.toDto(userList);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testUser.getId(), result.get(0).getId());
        assertNotNull(user2);
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
}
