package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.ChangePasswordRequestDTO;
import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.dto.response.UserResponseDTO;
import br.com.tastemanager.entity.User;
import br.com.tastemanager.entity.UserType;
import br.com.tastemanager.mapper.UserMapper;
import br.com.tastemanager.repository.UserRepository;
import br.com.tastemanager.validator.UserTypeValidator;
import br.com.tastemanager.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordService passwordService;

    @Mock
    private UserValidator userValidation;

    @Mock
    private UserTypeValidator userTypeValidator;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ValidRequest_ReturnsUserResponse() {
        UserRequestDTO request = new UserRequestDTO();
        request.setLogin("validLogin");
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");

        User user = new User();
        UserResponseDTO expectedResponse = new UserResponseDTO();

        when(userMapper.UserRequestDtoToEntity(request)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.userToUserResponseDto(user)).thenReturn(expectedResponse);

        UserResponseDTO response = userService.createUser(request);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(userValidation).validateLoginAvailability(request.getLogin());
        verify(userRepository).save(user);
    }

    @Test
    void updateUser_InvalidName_ThrowsException() {
        Long userId = 1L;
        UserUpdateRequestDTO request = new UserUpdateRequestDTO();
        request.setName("   ");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(userId, request);
        });

        assertEquals("Name cannot be blank or empty.", exception.getMessage());
    }

    @Test
    void deleteUser_ValidId_ReturnsSuccessMessage() {
        Long userId = 1L;

        doNothing().when(userValidation).validateUserExistsById(userId);
        doNothing().when(userValidation).validateUserIsAOwnerInUseById(userId);

        String result = userService.deleteUser(userId);

        assertEquals("User deleted successfully", result);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void updatePassword_InvalidOldPassword_ThrowsException() {
        Long userId = 1L;
        ChangePasswordRequestDTO request = new ChangePasswordRequestDTO();
        request.setOldPassword("wrongPassword");
        request.setNewPassword("newPassword");

        when(passwordService.isPasswordValid(userId, request.getOldPassword())).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updatePassword(userId, request);
        });

        assertEquals("Old password is incorrect", exception.getMessage());
    }

    @Test
    void validateLogin_ValidCredentials_ReturnsTrue() {
        String login = "validLogin";
        String password = "validPassword";

        User user = new User();
        user.setPassword(password);
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));

        boolean isValid = userService.validateLogin(login, password);

        assertTrue(isValid);
    }

    @Test
    void findAllUsers_ValidPageAndSize_ReturnsUserList() {
        int page = 1;
        int size = 5;

        Pageable pageable = PageRequest.of(page - 1, size);
        List<User> users = List.of(new User(), new User());
        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(users));

        List<User> result = userService.findAllUsers(page, size);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository).findAll(pageable);
    }

    @Test
    void findAllUsers_InvalidPage_ThrowsException() {
        int page = 0;
        int size = 5;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.findAllUsers(page, size);
        });

        assertEquals("Page index must not be less than zero", exception.getMessage());
    }

    @Test
    void findAllUsers_ValidPageAndSize_ReturnsCorrectPageContent() {
        int page = 2;
        int size = 3;

        Pageable pageable = PageRequest.of(page - 1, size);
        List<User> users = List.of(new User(), new User(), new User());
        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(users));

        List<User> result = userService.findAllUsers(page, size);

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(userRepository).findAll(pageable);
    }

    @Test
    void findAllUsers_EmptyResult_ReturnsEmptyList() {
        int page = 1;
        int size = 5;

        Pageable pageable = PageRequest.of(page - 1, size);
        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of()));

        List<User> result = userService.findAllUsers(page, size);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository).findAll(pageable);
    }

    @Test
    void findAllUsers_NegativePage_ThrowsException() {
        int page = -1;
        int size = 5;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.findAllUsers(page, size);
        });

        assertEquals("Page index must not be less than zero", exception.getMessage());
    }

    @Test
    void findAllUsers_ZeroSize_ThrowsException() {
        int page = 1;
        int size = 0;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.findAllUsers(page, size);
        });

        assertEquals("Page size must not be less than one", exception.getMessage());
    }

    @Test
    void updateUser_InvalidEmail_ThrowsException() {
        Long userId = 1L;
        UserUpdateRequestDTO request = new UserUpdateRequestDTO();
        request.setEmail("invalidEmail");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(userId, request);
        });

        assertEquals("E-mail cannot be blank or empty.", exception.getMessage());
    }

    @Test
    void updateUser_ValidNameAndEmail_UpdatesUserSuccessfully() {
        Long userId = 1L;
        UserUpdateRequestDTO request = new UserUpdateRequestDTO();
        request.setName("Updated Name");
        request.setEmail("updated.email@example.com");

        User existingUser = new User();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        String result = userService.updateUser(userId, request);

        assertEquals("User updated successfully", result);
        assertEquals("Updated Name", existingUser.getName());
        assertEquals("updated.email@example.com", existingUser.getEmail());
        verify(userRepository).save(existingUser);
    }

    @Test
    void updateUser_ValidAddress_UpdatesUserSuccessfully() {
        Long userId = 1L;
        UserUpdateRequestDTO request = new UserUpdateRequestDTO();
        request.setAddress("Updated Address");

        User existingUser = new User();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        String result = userService.updateUser(userId, request);

        assertEquals("User updated successfully", result);
        assertEquals("Updated Address", existingUser.getAddress());
        verify(userRepository).save(existingUser);
    }

    @Test
    void updateUser_ValidUserTypeId_UpdatesUserSuccessfully() {
        Long userId = 1L;
        UserUpdateRequestDTO request = new UserUpdateRequestDTO();
        UserType userType = new UserType();
        userType.setId(5L);
        request.setUserTypeId(userType);

        User existingUser = new User();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        doNothing().when(userTypeValidator).validateUserTypeId(5L);

        String result = userService.updateUser(userId, request);

        assertEquals("User updated successfully", result);
        assertEquals(userType, existingUser.getUserTypeId());
        verify(userTypeValidator).validateUserTypeId(5L);
        verify(userRepository).save(existingUser);
    }

    @Test
    void updatePassword_ValidOldPassword_UpdatesPasswordSuccessfully() {
        Long userId = 1L;
        ChangePasswordRequestDTO request = new ChangePasswordRequestDTO();
        request.setOldPassword("validOldPassword");
        request.setNewPassword("newPassword");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setPassword("validOldPassword");

        when(passwordService.isPasswordValid(userId, request.getOldPassword())).thenReturn(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.updatePassword(userId, request);

        assertEquals("newPassword", existingUser.getPassword());
        verify(userRepository).save(existingUser);
    }

}
