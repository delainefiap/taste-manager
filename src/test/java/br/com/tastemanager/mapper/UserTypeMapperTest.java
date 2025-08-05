package br.com.tastemanager.mapper;

import br.com.tastemanager.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.dto.response.UserTypeResponseDTO;
import br.com.tastemanager.entity.UserType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserTypeMapperTest {

    private final UserTypeMapper userTypeMapper = Mappers.getMapper(UserTypeMapper.class);

    @Test
    void shouldMapRequestDTOToEntity() {
        UserTypeRequestDTO requestDTO = new UserTypeRequestDTO();
        requestDTO.setName("Admin");

        UserType userType = userTypeMapper.toEntity(requestDTO);

        assertNotNull(userType);
        assertEquals("ADMIN", userType.getName());
    }

    @Test
    void shouldMapEntityToRequestDTO() {
        UserType userType = new UserType();
        userType.setName("User");

        UserTypeRequestDTO requestDTO = userTypeMapper.toRequestDTO(userType);

        assertNotNull(requestDTO);
        assertEquals("USER", requestDTO.getName());
    }

    @Test
    void shouldMapEntityToResponseDTO() {
        UserType userType = new UserType();
        userType.setId(8L);
        userType.setName("MANAGER");

        UserTypeResponseDTO responseDTO = userTypeMapper.toResponseDTO(userType);

        assertNotNull(responseDTO);
        assertEquals(8L, responseDTO.getId());
        assertEquals("MANAGER", responseDTO.getName());
    }

    @Test
    void shouldReturnNullWhenMappingNullEntityToResponseDTO() {
        UserTypeResponseDTO responseDTO = userTypeMapper.mapUserTypeToResponseDTO(null);

        assertNull(responseDTO);
    }
}