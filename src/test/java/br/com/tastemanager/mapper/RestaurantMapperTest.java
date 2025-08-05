package br.com.tastemanager.mapper;

import br.com.tastemanager.dto.request.RestaurantRequestDTO;
import br.com.tastemanager.dto.response.RestaurantResponseDTO;
import br.com.tastemanager.entity.Restaurant;
import br.com.tastemanager.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestaurantMapperTest {

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Test
    void shouldMapRestaurantToResponseDTO() {
        User owner = new User();
        owner.setName("John Doe");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");
        restaurant.setOwner(owner);

        RestaurantResponseDTO responseDTO = restaurantMapper.toResponseDTO(restaurant);

        assertNotNull(responseDTO);
        assertEquals("Test Restaurant", responseDTO.getName());
        assertEquals("John Doe", responseDTO.getOwnerName());
    }

    @Test
    void shouldMapRequestDTOToRestaurantEntity() {
        RestaurantRequestDTO requestDTO = new RestaurantRequestDTO();
        requestDTO.setName("New Restaurant");

        Restaurant restaurant = restaurantMapper.toEntity(requestDTO);

        assertNotNull(restaurant);
        assertNull(restaurant.getId());
        assertNull(restaurant.getOwner());
        assertEquals("New Restaurant", restaurant.getName());
    }
}