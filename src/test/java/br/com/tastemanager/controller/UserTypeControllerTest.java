package br.com.tastemanager.controller;

import br.com.tastemanager.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.dto.response.UserTypeResponseDTO;
import br.com.tastemanager.service.UserTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class UserTypeControllerTest {

    @Mock
    private UserTypeService userTypeService;

    @InjectMocks
    private UserTypeController userTypeController;

    @Test
    void createUserTypeReturnsCreatedResponse() {
        UserTypeRequestDTO requestDTO = new UserTypeRequestDTO();
        UserTypeResponseDTO responseDTO = new UserTypeResponseDTO();

        when(userTypeService.createUserType(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<UserTypeResponseDTO> response = userTypeController.createUserType(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void findUserTypeByIdReturnsUserTypeWhenFound() {
        Long id = 1L;
        UserTypeResponseDTO responseDTO = new UserTypeResponseDTO();

        when(userTypeService.findUserTypeById(id)).thenReturn(responseDTO);

        ResponseEntity<UserTypeResponseDTO> response = userTypeController.findUserTypeById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void updateUserTypeReturnsUpdatedUserType() {
        Long id = 1L;
        UserTypeRequestDTO requestDTO = new UserTypeRequestDTO();
        UserTypeResponseDTO responseDTO = new UserTypeResponseDTO();

        when(userTypeService.updateUserType(id, requestDTO)).thenReturn(responseDTO);

        ResponseEntity<UserTypeResponseDTO> response = userTypeController.updateUserType(id, requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void deleteUserTypeReturnsSuccessMessage() {
        Long id = 1L;
        String successMessage = "User type deleted successfully";

        when(userTypeService.deleteUserType(id)).thenReturn(successMessage);

        ResponseEntity<String> response = userTypeController.deleteUserType(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(successMessage, response.getBody());
    }

    @Test
    void findUserTypeByIdReturnsErrorMessageWhenUserTypeDoesNotExist() {
        Long id = 1L;

        when(userTypeService.findUserTypeById(id)).thenThrow(new IllegalArgumentException("UserType not found"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userTypeController.findUserTypeById(id);
        });

        assertEquals("UserType not found", exception.getMessage());
        verify(userTypeService).findUserTypeById(id);
    }

    @Test
    void findAllUserTypesReturnsEmptyListWhenNoUserTypesExist() {
        when(userTypeService.findAllUserTypes(anyInt(), anyInt())).thenReturn(List.of());

        ResponseEntity<?> response = userTypeController.findAllUserTypes(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, ((List<?>) response.getBody()).size());
    }

    @Test
    void deleteUserTypeReturnsNotFoundWhenUserTypeDoesNotExist() {
        Long id = 1L;

        when(userTypeService.deleteUserType(id)).thenThrow(new IllegalArgumentException("UserType do not exists"));

        ResponseEntity<String> response = null;
        try {
            response = userTypeController.deleteUserType(id);
        } catch (IllegalArgumentException ex) {
            assertEquals("UserType do not exists", ex.getMessage());
        }

        assertEquals(null, response);
        verify(userTypeService).deleteUserType(id);
    }
}