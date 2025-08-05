package br.com.tastemanager.validator;

import br.com.tastemanager.repository.RestaurantRepository;
import br.com.tastemanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserValidatorTest {

    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;
    private UserValidator userValidator;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        restaurantRepository = mock(RestaurantRepository.class);
        userValidator = new UserValidator(userRepository, restaurantRepository);
    }

    @Nested
    @DisplayName("validateLoginAvailability")
    class ValidateLoginAvailability {

        @Test
        @DisplayName("throws exception when login already exists")
        void shouldThrowWhenLoginExists() {
            when(userRepository.findIdByLogin("admin")).thenReturn(Optional.of(1L));

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> userValidator.validateLoginAvailability("admin")
            );

            assertEquals("This login is unavailable. Please choose a different one.", exception.getMessage());
        }

        @Test
        @DisplayName("does not throw when login is available")
        void shouldNotThrowWhenLoginIsAvailable() {
            when(userRepository.findIdByLogin("newUser")).thenReturn(Optional.empty());

            assertDoesNotThrow(() -> userValidator.validateLoginAvailability("newUser"));
        }
    }

    @Nested
    @DisplayName("validateUserExistsById")
    class ValidateUserExistsById {

        @Test
        @DisplayName("throws exception when user does not exist")
        void shouldThrowWhenUserDoesNotExist() {
            when(userRepository.findById(99L)).thenReturn(Optional.empty());

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> userValidator.validateUserExistsById(99L)
            );

            assertEquals("User with the given ID does not exist.", exception.getMessage());
        }

        @Test
        @DisplayName("does not throw when user exists")
        void shouldNotThrowWhenUserExists() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(mock()));

            assertDoesNotThrow(() -> userValidator.validateUserExistsById(1L));
        }
    }

    @Nested
    @DisplayName("validateUserIsAOwnerInUseById")
    class ValidateUserIsAOwnerInUseById {

        @Test
        @DisplayName("throws exception when user is owner of a restaurant")
        void shouldThrowWhenUserIsOwner() {
            when(restaurantRepository.existsByOwnerId(5L)).thenReturn(true);

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> userValidator.validateUserIsAOwnerInUseById(5L)
            );

            assertEquals("Cannot delete: the user is the owner of a restaurant.", exception.getMessage());
        }

        @Test
        @DisplayName("does not throw when user is not owner of any restaurant")
        void shouldNotThrowWhenUserIsNotOwner() {
            when(restaurantRepository.existsByOwnerId(10L)).thenReturn(false);

            assertDoesNotThrow(() -> userValidator.validateUserIsAOwnerInUseById(10L));
        }
    }
}
