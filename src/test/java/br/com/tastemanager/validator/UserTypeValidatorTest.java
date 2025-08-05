package br.com.tastemanager.validator;

import br.com.tastemanager.repository.UserRepository;
import br.com.tastemanager.repository.UserTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTypeValidatorTest {

    private UserRepository userRepository;
    private UserTypeRepository userTypeRepository;
    private UserTypeValidator userTypeValidator;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userTypeRepository = mock(UserTypeRepository.class);
        userTypeValidator = new UserTypeValidator(userRepository, userTypeRepository, userRepository);
    }

    @Nested
    @DisplayName("validateUserTypeName")
    class ValidateUserTypeName {

        @Test
        @DisplayName("throws exception when user type name already exists")
        void shouldThrowWhenUserTypeNameExists() {
            when(userTypeRepository.findByName("Admin")).thenReturn(Optional.of(mock()));

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> userTypeValidator.validateUserTypeName("Admin")
            );

            assertEquals("UserType with this name already exists. Please choose a different one.", exception.getMessage());
        }

        @Test
        @DisplayName("does not throw when user type name is new")
        void shouldNotThrowWhenUserTypeNameIsNew() {
            when(userTypeRepository.findByName("NewRole")).thenReturn(Optional.empty());

            assertDoesNotThrow(() -> userTypeValidator.validateUserTypeName("NewRole"));
        }
    }

    @Nested
    @DisplayName("validateUserTypeId")
    class ValidateUserTypeId {

        @Test
        @DisplayName("throws exception when user type id does not exist")
        void shouldThrowWhenUserTypeIdDoesNotExist() {
            when(userTypeRepository.findById(1L)).thenReturn(Optional.empty());

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> userTypeValidator.validateUserTypeId(1L)
            );

            assertEquals("UserType with this id do not exists. Please choose a different one.", exception.getMessage());
        }

        @Test
        @DisplayName("does not throw when user type id exists")
        void shouldNotThrowWhenUserTypeIdExists() {
            when(userTypeRepository.findById(1L)).thenReturn(Optional.of(mock()));

            assertDoesNotThrow(() -> userTypeValidator.validateUserTypeId(1L));
        }
    }

    @Nested
    @DisplayName("validateUserTypeIsInUse")
    class ValidateUserTypeIsInUse {

        @Test
        @DisplayName("throws exception when user type is in use")
        void shouldThrowWhenUserTypeIsInUse() {
            when(userRepository.countByUserTypeId(2L)).thenReturn(5L);

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> userTypeValidator.validateUserTypeIsInUse(2L)
            );

            assertEquals("Cannot delete UserType with associated users", exception.getMessage());
        }

        @Test
        @DisplayName("does not throw when user type is not in use")
        void shouldNotThrowWhenUserTypeIsNotInUse() {
            when(userRepository.countByUserTypeId(3L)).thenReturn(0L);

            assertDoesNotThrow(() -> userTypeValidator.validateUserTypeIsInUse(3L));
        }
    }
}
