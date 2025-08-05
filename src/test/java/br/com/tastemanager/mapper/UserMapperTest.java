package br.com.tastemanager.mapper;

import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.dto.response.UserResponseDTO;
import br.com.tastemanager.entity.User;
import br.com.tastemanager.entity.UserType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void shouldMapUserRequestDtoToEntity() {
        UserRequestDTO dto = new UserRequestDTO();
        UserType userType = new UserType();
        userType.setId(1L);

        dto.setName("John Doe");
        dto.setEmail("john.doe@example.com");
        dto.setLogin("johndoe");
        dto.setPassword("password123");
        dto.setUserTypeId(userType);
        dto.setAddress("123 Main St");

        User user = userMapper.UserRequestDtoToEntity(dto);

        assertNotNull(user);
        assertNull(user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("johndoe", user.getLogin());
        assertEquals("password123", user.getPassword());
        assertEquals(1L, user.getUserTypeId().getId());
        assertEquals("123 Main St", user.getAddress());
    }

    @Test
    void shouldMapUserUpdateRequestDtoToEntity() {
        UserUpdateRequestDTO dto = new UserUpdateRequestDTO();
        UserType userType = new UserType();
        userType.setId(2L);

        dto.setUserTypeId(userType);

        dto.setName("Jane Doe");
        dto.setEmail("jane.doe@example.com");
        dto.setUserTypeId(userType);
        dto.setAddress("456 Elm St");

        User user = userMapper.userUpdateRequestDtoToEntity(dto);

        assertNotNull(user);
        assertNull(user.getId());
        assertEquals("Jane Doe", user.getName());
        assertEquals("jane.doe@example.com", user.getEmail());
        assertEquals(2L, user.getUserTypeId().getId());
        assertEquals("456 Elm St", user.getAddress());
    }

    @Test
    void shouldMapUserToUserResponseDto() {
        UserType userType = new UserType();
        userType.setId(3L);

        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setLogin("johndoe");
        user.setUserTypeId(userType);
        user.setAddress("123 Main St");

        UserResponseDTO dto = userMapper.userToUserResponseDto(user);

        assertNotNull(dto);
        assertEquals("John Doe", dto.getName());
        assertEquals("john.doe@example.com", dto.getEmail());
        assertEquals("johndoe", dto.getLogin());
        assertEquals(3L, dto.getUserTypeId().getId());
        assertEquals("123 Main St", dto.getAddress());
    }
}