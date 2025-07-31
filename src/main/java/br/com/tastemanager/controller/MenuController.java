package br.com.tastemanager.controller;

import br.com.tastemanager.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.dto.request.MenuRequestDTO;
import br.com.tastemanager.dto.response.MenuResponseDTO;
import br.com.tastemanager.entity.Menu;
import br.com.tastemanager.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/create/{restaurantId}")
    public ResponseEntity<MenuResponseDTO> createMenu(@PathVariable Long restaurantId, @RequestBody MenuRequestDTO menu) {
        var response = menuService.createMenu(restaurantId, menu);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/find-all-by-restaurant/{restaurantId}")
    public ResponseEntity<List<MenuResponseDTO>> getMenusByRestaurant(@PathVariable Long restaurantId) {
        List<MenuResponseDTO> menus = menuService.getMenusByRestaurant(restaurantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(menus);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MenuResponseDTO> updateMenu(@PathVariable Long id, @RequestBody MenuItemUpdateRequestDTO menuItemRequest) {
        MenuResponseDTO updatedMenu = menuService.updateMenu(id, menuItemRequest);
        return ResponseEntity.ok(updatedMenu);
    }

    @DeleteMapping("/delete/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.ok("Menu deleted successfully");
    }

    @DeleteMapping("/delete-item/{menuId}/{itemId}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long menuId, @PathVariable Long itemId) {
        menuService.deleteMenuItem(menuId, itemId);
        return ResponseEntity.ok("Menu item deleted successfully");
    }
}