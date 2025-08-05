package br.com.tastemanager.controller.openapi;

import br.com.tastemanager.dto.request.RestaurantRequestDTO;
import br.com.tastemanager.dto.response.RestaurantResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface RestaurantControllerDocs {

    @Operation(summary = "Cria um novo restaurante.")
    @PostMapping("/create")
    ResponseEntity<RestaurantResponseDTO> createRestaurant(@Valid @RequestBody RestaurantRequestDTO requestDTO);

    @Operation(summary = "Busca todos os restaurantes cadastrados.")
    @GetMapping("/find-all")
    ResponseEntity<List<RestaurantResponseDTO>> findAllRestaurants();

    @Operation(summary = "Busca um restaurante pelo ID.")
    @GetMapping("/find-by-id/{id}")
    ResponseEntity<RestaurantResponseDTO> findRestaurantById(@PathVariable Long id);

    @Operation(summary = "Atualiza os dados de um restaurante.")
    @PatchMapping("/update/{id}")
    ResponseEntity<RestaurantResponseDTO> updateRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantRequestDTO requestDTO);

    @Operation(summary = "Exclui um restaurante pelo ID.")
    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteRestaurant(@PathVariable Long id);
}