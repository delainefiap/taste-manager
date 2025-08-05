package br.com.tastemanager.controller.openapi;

import br.com.tastemanager.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.dto.request.MenuRequestDTO;
import br.com.tastemanager.dto.response.MenuResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface MenuControllerDocs {

    @Operation(summary = "Cria um novo menu para um restaurante.")
    @PostMapping("/create/{restaurantId}")
    ResponseEntity<String> createMenu(@PathVariable Long restaurantId, @RequestBody MenuRequestDTO menu);

    @Operation(summary = "Busca todos os menus de um restaurante.")
    @GetMapping("/find-all-by-restaurant/{restaurantId}")
    ResponseEntity<List<MenuResponseDTO>> getMenusByRestaurant(@PathVariable Long restaurantId);

    @Operation(summary = "Pesquisa todos os itens cadastrados.")
    @GetMapping("/find-all")
    ResponseEntity<?>getAllMenus(@RequestParam int page, @RequestParam int size);

    @Operation(summary = "Atualiza um menu pelo ID.")
    @PutMapping("/update/{id}")
    ResponseEntity<String> updateMenu(@PathVariable Long id, @RequestBody MenuItemUpdateRequestDTO menuItemRequest);

    @Operation(summary = "Exclui um menu pelo ID.")
    @DeleteMapping("/delete/{menuId}")
    ResponseEntity<String> deleteMenu(@PathVariable Long menuId);

    @Operation(summary = "Exclui um item de um menu pelo ID.")
    @DeleteMapping("/delete-item/{menuId}/{itemId}")
    ResponseEntity<String> deleteMenuItem(@PathVariable Long menuId, @PathVariable Long itemId);
}