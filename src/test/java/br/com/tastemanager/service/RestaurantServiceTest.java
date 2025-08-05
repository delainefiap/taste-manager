package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.RestaurantRequestDTO;
import br.com.tastemanager.dto.response.RestaurantResponseDTO;
import br.com.tastemanager.entity.Restaurant;
import br.com.tastemanager.entity.User;
import br.com.tastemanager.mapper.RestaurantMapper;
import br.com.tastemanager.repository.RestaurantRepository;
import br.com.tastemanager.validator.RestaurantValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @Mock
    private RestaurantValidator restaurantValidator;

    @InjectMocks
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRestaurant_shouldCreateSuccessfully() {
        RestaurantRequestDTO request = new RestaurantRequestDTO();
        request.setName("Sabor Brasil");
        request.setOwnerId(1L);

        User owner = new User();
        owner.setId(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Sabor Brasil");
        restaurant.setOwner(owner);

        RestaurantResponseDTO response = new RestaurantResponseDTO();
        response.setName("Sabor Brasil");

        when(restaurantValidator.validateOwner(1L)).thenReturn(owner);
        doNothing().when(restaurantValidator).validateRestaurantName("Sabor Brasil");
        when(restaurantMapper.toEntity(request)).thenReturn(restaurant);
        when(restaurantMapper.toResponseDTO(restaurant)).thenReturn(response);

        RestaurantResponseDTO result = restaurantService.createRestaurant(request);

        assertEquals("Sabor Brasil", result.getName());
        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void findAllRestaurants_shouldReturnList() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Comida Boa");

        RestaurantResponseDTO response = new RestaurantResponseDTO();
        response.setName("Comida Boa");

        when(restaurantRepository.findAll()).thenReturn(List.of(restaurant));
        when(restaurantMapper.toResponseDTO(restaurant)).thenReturn(response);

        List<RestaurantResponseDTO> result = restaurantService.findAllRestaurants();

        assertEquals(1, result.size());
        assertEquals("Comida Boa", result.get(0).getName());
    }

    @Test
    void findRestaurantById_shouldReturnRestaurant() {
        Long id = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName("Cantina");

        RestaurantResponseDTO response = new RestaurantResponseDTO();
        response.setName("Cantina");

        doNothing().when(restaurantValidator).validateRestaurantExists(id);
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant));
        when(restaurantMapper.toResponseDTO(restaurant)).thenReturn(response);

        RestaurantResponseDTO result = restaurantService.findRestaurantById(id);

        assertEquals("Cantina", result.getName());
    }

    @Test
    void updateRestaurant_shouldUpdateSuccessfully() {
        Long id = 1L;

        RestaurantRequestDTO request = new RestaurantRequestDTO();
        request.setName("Atualizado");
        request.setAddress("Rua X");
        request.setTypeKitchen("Italiana");
        request.setOpeningHours("9h - 18h");
        request.setOwnerId(2L);

        User owner = new User();
        owner.setId(2L);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName("Antigo");

        RestaurantResponseDTO response = new RestaurantResponseDTO();
        response.setName("Atualizado");

        doNothing().when(restaurantValidator).validateRestaurantExists(id);
        when(restaurantValidator.validateOwner(2L)).thenReturn(owner);
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant));
        when(restaurantMapper.toResponseDTO(restaurant)).thenReturn(response);

        RestaurantResponseDTO result = restaurantService.updateRestaurant(id, request);

        assertEquals("Atualizado", result.getName());
        assertEquals("Rua X", restaurant.getAddress());
        assertEquals("Italiana", restaurant.getTypeKitchen());
        assertEquals("9h - 18h", restaurant.getOpeningHours());
        assertEquals(owner, restaurant.getOwner());
        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void deleteRestaurant_shouldDeleteSuccessfully() {
        Long id = 5L;

        doNothing().when(restaurantValidator).validateRestaurantExists(id);
        doNothing().when(restaurantRepository).deleteById(id);

        String result = restaurantService.deleteRestaurant(id);

        assertEquals("Restaurant deleted successfully", result);
        verify(restaurantRepository).deleteById(id);
    }

    @Test
    void findRestaurantById_shouldThrowIfNotFound() {
        Long id = 99L;

        doNothing().when(restaurantValidator).validateRestaurantExists(id);
        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                restaurantService.findRestaurantById(id)
        );

        assertEquals("Restaurant not found", ex.getMessage());
    }
}
