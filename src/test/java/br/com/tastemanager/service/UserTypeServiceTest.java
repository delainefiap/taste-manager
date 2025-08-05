package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.dto.response.UserTypeResponseDTO;
import br.com.tastemanager.entity.UserType;
import br.com.tastemanager.mapper.UserTypeMapper;
import br.com.tastemanager.repository.UserTypeRepository;
import br.com.tastemanager.validator.UserTypeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTypeServiceTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @Mock
    private UserTypeMapper userTypeMapper;

    @Mock
    private UserTypeValidator userTypeValidator;

    @InjectMocks
    private UserTypeService userTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUserType_ShouldReturnCreatedUserType() {
        UserTypeRequestDTO request = new UserTypeRequestDTO();
        request.setName("CLIENTE");

        UserType userType = new UserType();
        userType.setName("CLIENTE");

        UserTypeResponseDTO response = new UserTypeResponseDTO();
        response.setName("CLIENTE");

        when(userTypeMapper.toEntity(request)).thenReturn(userType);
        when(userTypeMapper.toResponseDTO(userType)).thenReturn(response);

        UserTypeResponseDTO result = userTypeService.createUserType(request);

        assertEquals("CLIENTE", result.getName());
        verify(userTypeValidator).validateUserTypeName("CLIENTE");
        verify(userTypeRepository).save(userType);
    }

    @Test
    void findAllUserTypes_ShouldReturnPaginatedList() {
        UserType userType = new UserType();
        userType.setId(1L);
        userType.setName("DONO");

        when(userTypeRepository.findAll(PageRequest.of(0, 5)))
                .thenReturn(new PageImpl<>(List.of(userType)));

        List<UserType> result = userTypeService.findAllUserTypes(1, 5);

        assertEquals(1, result.size());
        assertEquals("DONO", result.get(0).getName());
    }

    @Test
    void updateUserType_ShouldUpdateNameAndReturnDTO() {
        Long id = 1L;
        UserTypeRequestDTO request = new UserTypeRequestDTO();
        request.setName("NOVO");

        UserType userType = new UserType();
        userType.setId(id);
        userType.setName("ANTIGO");

        UserTypeResponseDTO response = new UserTypeResponseDTO();
        response.setName("NOVO");

        when(userTypeRepository.findById(id)).thenReturn(Optional.of(userType));
        when(userTypeMapper.toResponseDTO(userType)).thenReturn(response);

        UserTypeResponseDTO result = userTypeService.updateUserType(id, request);

        assertEquals("NOVO", result.getName());
        verify(userTypeRepository).save(userType);
    }

    @Test
    void deleteUserType_ShouldDeleteSuccessfully() {
        Long id = 1L;

        when(userTypeRepository.existsById(id)).thenReturn(true);
        doNothing().when(userTypeValidator).validateUserTypeIsInUse(id);

        String result = userTypeService.deleteUserType(id);

        assertEquals("User type deleted successfully", result);
        verify(userTypeRepository).deleteById(id);
    }

    @Test
    void deleteUserType_ShouldThrowIfNotExists() {
        Long id = 99L;
        when(userTypeRepository.existsById(id)).thenReturn(false);

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                userTypeService.deleteUserType(id));

        assertEquals("UserType do not exists", ex.getMessage());
    }

    @Test
    void findUserTypeById_ShouldReturnDTO() {
        Long id = 1L;
        UserType userType = new UserType();
        userType.setId(id);
        userType.setName("CLIENTE");

        UserTypeResponseDTO response = new UserTypeResponseDTO();
        response.setName("CLIENTE");

        when(userTypeRepository.findById(id)).thenReturn(Optional.of(userType));
        when(userTypeMapper.toResponseDTO(userType)).thenReturn(response);

        UserTypeResponseDTO result = userTypeService.findUserTypeById(id);

        assertEquals("CLIENTE", result.getName());
    }

    @Test
    void findUserTypeById_ShouldThrowIfNotFound() {
        Long id = 42L;
        when(userTypeRepository.findById(id)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                userTypeService.findUserTypeById(id));

        assertEquals("UserType not found", ex.getMessage());
    }
}
