package br.com.tastemanager.validator;

import br.com.tastemanager.entity.User;
import br.com.tastemanager.entity.UserType;
import br.com.tastemanager.repository.MenuRepository;
import br.com.tastemanager.repository.RestaurantRepository;
import br.com.tastemanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantValidatorTest {

    private RestaurantRepository restaurantRepository;
    private UserRepository userRepository;
    private MenuRepository menuRepository;
    private RestaurantValidator validator;

    @BeforeEach
    void setUp() {
        restaurantRepository = mock(RestaurantRepository.class);
        userRepository = mock(UserRepository.class);
        menuRepository = mock(MenuRepository.class); // Adicionado mock do MenuRepository
        validator = new RestaurantValidator(restaurantRepository, userRepository, menuRepository);
    }

    @Test
    void validateOwner_shouldReturnUser_whenUserIsOwner() {
        Long ownerId = 1L;

        UserType userType = new UserType();
        userType.setId(2L);

        User user = new User();
        user.setUserTypeId(userType);

        when(userRepository.findById(ownerId)).thenReturn(Optional.of(user));

        User result = validator.validateOwner(ownerId);

        assertEquals(user, result);
        verify(userRepository, times(1)).findById(ownerId);
    }

    @Test
    void validateOwner_shouldThrowException_whenUserNotFound() {
        Long ownerId = 1L;
        when(userRepository.findById(ownerId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateOwner(ownerId);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void validateOwner_shouldThrowException_whenUserTypeIdIsNot2() {
        Long ownerId = 1L;

        UserType userType = new UserType();
        userType.setId(3L);

        User user = new User();
        user.setUserTypeId(userType);

        when(userRepository.findById(ownerId)).thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateOwner(ownerId);
        });

        assertEquals("Owner must be a user with UserType ID = 2", exception.getMessage());
    }

    @Test
    void validateRestaurantName_shouldPass_whenNameDoesNotExist() {
        String name = "Novo Restaurante";

        when(restaurantRepository.existsByName(name)).thenReturn(false);

        assertDoesNotThrow(() -> validator.validateRestaurantName(name));

        verify(restaurantRepository, times(1)).existsByName(name);
    }

    @Test
    void validateRestaurantName_shouldThrowException_whenNameExists() {
        String name = "Restaurante Existente";

        when(restaurantRepository.existsByName(name)).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateRestaurantName(name);
        });

        assertEquals("Restaurant with this name already exists", exception.getMessage());
    }

    @Test
    void validateRestaurantExists_shouldPass_whenIdExists() {
        Long restaurantId = 1L;

        when(restaurantRepository.existsById(restaurantId)).thenReturn(true);

        assertDoesNotThrow(() -> validator.validateRestaurantExists(restaurantId));

        verify(restaurantRepository, times(1)).existsById(restaurantId);
    }

    @Test
    void validateRestaurantExists_shouldThrowException_whenIdDoesNotExist() {
        Long restaurantId = 1L;

        when(restaurantRepository.existsById(restaurantId)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateRestaurantExists(restaurantId);
        });

        assertEquals("Restaurant not found", exception.getMessage());
    }
}
