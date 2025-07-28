package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.RestaurantRequestDTO;
import br.com.tastemanager.dto.response.RestaurantResponseDTO;
import br.com.tastemanager.entity.Restaurant;
import br.com.tastemanager.entity.User;
import br.com.tastemanager.repository.RestaurantRepository;
import br.com.tastemanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public RestaurantResponseDTO createRestaurant(RestaurantRequestDTO requestDTO) {
        User owner = userRepository.findById(requestDTO.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!owner.getUserTypeId().getId().equals(2L)) {
            throw new IllegalArgumentException("Owner must be a user with UserType ID = 2");
        }

        if(restaurantRepository.existsByName(requestDTO.getName())) {
            throw new IllegalArgumentException("Restaurant with this name already exists");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setName(requestDTO.getName());
        restaurant.setAddress(requestDTO.getAddress());
        restaurant.setTypeKitchen(requestDTO.getTypeKitchen());
        restaurant.setOpeningHours(requestDTO.getOpeningHours());
        restaurant.setOwner(owner);

        restaurantRepository.save(restaurant);

        return toResponseDTO(restaurant);
    }

    public List<RestaurantResponseDTO> findAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public RestaurantResponseDTO findRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        return toResponseDTO(restaurant);
    }

    public RestaurantResponseDTO updateRestaurant(Long id, RestaurantRequestDTO requestDTO) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        User owner = userRepository.findById(requestDTO.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!owner.getUserTypeId().getId().equals(2L)) {
            throw new IllegalArgumentException("Owner must be a user with UserTypeId = 2");
        }

        restaurant.setName(requestDTO.getName());
        restaurant.setAddress(requestDTO.getAddress());
        restaurant.setTypeKitchen(requestDTO.getTypeKitchen());
        restaurant.setOpeningHours(requestDTO.getOpeningHours());
        restaurant.setOwner(owner);

        restaurantRepository.save(restaurant);

        return toResponseDTO(restaurant);
    }

    public String deleteRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new IllegalArgumentException("Restaurant not found");
        }
        restaurantRepository.deleteById(id);
        return "Restaurant deleted successfully";
    }

    private RestaurantResponseDTO toResponseDTO(Restaurant restaurant) {
        RestaurantResponseDTO responseDTO = new RestaurantResponseDTO();
        responseDTO.setId(restaurant.getId());
        responseDTO.setName(restaurant.getName());
        responseDTO.setAddress(restaurant.getAddress());
        responseDTO.setTypeKitchen(restaurant.getTypeKitchen());
        responseDTO.setOpeningHours(restaurant.getOpeningHours());
        responseDTO.setOwnerName(restaurant.getOwner().getName());
        return responseDTO;
    }
}