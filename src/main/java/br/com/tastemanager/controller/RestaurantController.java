package br.com.tastemanager.controller;

import br.com.tastemanager.dto.request.RestaurantRequestDTO;
import br.com.tastemanager.dto.response.RestaurantResponseDTO;
import br.com.tastemanager.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/create")
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@Valid @RequestBody RestaurantRequestDTO requestDTO) {
        var response = restaurantService.createRestaurant(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Busca todos os restaurantes cadastrados.")
    @GetMapping("/find-all")
    public ResponseEntity<List<RestaurantResponseDTO>> findAllRestaurants() {
        var response = restaurantService.findAllRestaurants();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Busca um restaurante pelo ID.")
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<RestaurantResponseDTO> findRestaurantById(@PathVariable Long id) {
        var response = restaurantService.findRestaurantById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Atualiza os dados de um restaurante.")
    @PatchMapping("/update/{id}")
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantRequestDTO requestDTO) {
        var response = restaurantService.updateRestaurant(id, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Exclui um restaurante pelo ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) {
        var response = restaurantService.deleteRestaurant(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}