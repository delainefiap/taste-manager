package br.com.tastemanager.controller;

import br.com.tastemanager.dto.request.ChangePasswordRequestDTO;
import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.dto.response.UserResponseDTO;
import br.com.tastemanager.entity.User;
import br.com.tastemanager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        UserRequestDTO userRequest = new UserRequestDTO();
        UserResponseDTO userResponse = new UserResponseDTO();
        when(userService.createUser(userRequest)).thenReturn(userResponse);

        ResponseEntity<UserResponseDTO> response = userController.createUser(userRequest);

        assertEquals(201, response.getStatusCodeValue());
        verify(userService, times(1)).createUser(userRequest);
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        UserUpdateRequestDTO userUpdateRequest = new UserUpdateRequestDTO();
        String expectedResponse = "User updated successfully";

        when(userService.updateUser(userId, userUpdateRequest)).thenReturn(expectedResponse);

        ResponseEntity<String> response = userController.updateUser(userId, userUpdateRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
        verify(userService, times(1)).updateUser(userId, userUpdateRequest);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        String expectedResponse = "User deleted successfully";

        when(userService.deleteUser(userId)).thenReturn(expectedResponse);

        ResponseEntity<String> response = userController.deleteUser(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void testChangePassword() {
        Long userId = 1L;
        ChangePasswordRequestDTO changePasswordRequestDTO = new ChangePasswordRequestDTO();

        doNothing().when(userService).updatePassword(userId, changePasswordRequestDTO);

        ResponseEntity<String> response = userController.changePassword(userId, changePasswordRequestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Password changed successfully.", response.getBody());
        verify(userService, times(1)).updatePassword(userId, changePasswordRequestDTO);
    }

    @Test
    void testValidateLogin() {
        String login = "test";
        String password = "password";

        when(userService.validateLogin(login, password)).thenReturn(true);

        ResponseEntity<String> response = userController.validateLogin(login, password);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Login successful", response.getBody());
        verify(userService, times(1)).validateLogin(login, password);
    }

    @Test
    void testFindAllUsers() {
        int page = 0;
        int size = 10;
        List<User> users = List.of(new User(), new User());

        when(userService.findAllUsers(page, size)).thenReturn(users);

        ResponseEntity<?> response = userController.findAllUsers(page, size);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(users, response.getBody());
        verify(userService, times(1)).findAllUsers(page, size);
    }

    @Test
    void createUserThrowsExceptionWhenUserAlreadyExists() {
        UserRequestDTO userRequest = new UserRequestDTO();

        when(userService.createUser(userRequest)).thenThrow(new IllegalArgumentException("User already exists"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userController.createUser(userRequest);
        });

        assertEquals("User already exists", exception.getMessage());
        verify(userService, times(1)).createUser(userRequest);
    }

    @Test
    void updateUserReturnsNotFoundWhenUserDoesNotExist() {
        Long userId = 1L;
        UserUpdateRequestDTO userUpdateRequest = new UserUpdateRequestDTO();

        when(userService.updateUser(userId, userUpdateRequest)).thenThrow(new IllegalArgumentException("User not found"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userController.updateUser(userId, userUpdateRequest);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userService, times(1)).updateUser(userId, userUpdateRequest);
    }

    @Test
    void deleteUserReturnsNotFoundWhenUserDoesNotExist() {
        Long userId = 1L;

        when(userService.deleteUser(userId)).thenThrow(new IllegalArgumentException("User not found"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userController.deleteUser(userId);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void validateLoginReturnsUnauthorizedWhenCredentialsAreInvalid() {
        String login = "test";
        String password = "wrongPassword";

        when(userService.validateLogin(login, password)).thenReturn(false);

        ResponseEntity<String> response = userController.validateLogin(login, password);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody());
        verify(userService, times(1)).validateLogin(login, password);
    }

    @Test
    void findAllUsersReturnsEmptyListWhenNoUsersExist() {
        int page = 0;
        int size = 10;

        when(userService.findAllUsers(page, size)).thenReturn(List.of());

        ResponseEntity<?> response = userController.findAllUsers(page, size);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(List.of(), response.getBody());
        verify(userService, times(1)).findAllUsers(page, size);
    }
}