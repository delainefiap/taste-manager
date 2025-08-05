package br.com.tastemanager.controller;

import br.com.tastemanager.dto.request.RestaurantRequestDTO;
import br.com.tastemanager.dto.response.RestaurantResponseDTO;
import br.com.tastemanager.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    @Test
    void createRestaurantReturnsCreatedResponse() {
        RestaurantRequestDTO requestDTO = new RestaurantRequestDTO();
        RestaurantResponseDTO responseDTO = new RestaurantResponseDTO();

        when(restaurantService.createRestaurant(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<RestaurantResponseDTO> response = restaurantController.createRestaurant(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void findAllRestaurantsReturnsListOfRestaurants() {
        List<RestaurantResponseDTO> responseList = List.of(new RestaurantResponseDTO());

        when(restaurantService.findAllRestaurants()).thenReturn(responseList);

        ResponseEntity<List<RestaurantResponseDTO>> response = restaurantController.findAllRestaurants();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseList, response.getBody());
    }

    @Test
    void findRestaurantByIdReturnsRestaurantWhenFound() {
        Long id = 1L;
        RestaurantResponseDTO responseDTO = new RestaurantResponseDTO();

        when(restaurantService.findRestaurantById(id)).thenReturn(responseDTO);

        ResponseEntity<RestaurantResponseDTO> response = restaurantController.findRestaurantById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void updateRestaurantReturnsUpdatedRestaurant() {
        Long id = 1L;
        RestaurantRequestDTO requestDTO = new RestaurantRequestDTO();
        RestaurantResponseDTO responseDTO = new RestaurantResponseDTO();

        when(restaurantService.updateRestaurant(id, requestDTO)).thenReturn(responseDTO);

        ResponseEntity<RestaurantResponseDTO> response = restaurantController.updateRestaurant(id, requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void deleteRestaurantReturnsSuccessMessage() {
        Long id = 1L;
        String successMessage = "Restaurant deleted successfully";

        when(restaurantService.deleteRestaurant(id)).thenReturn(successMessage);

        ResponseEntity<String> response = restaurantController.deleteRestaurant(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(successMessage, response.getBody());
    }
}
