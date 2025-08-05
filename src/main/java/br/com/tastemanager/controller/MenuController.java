package br.com.tastemanager.controller;

import br.com.tastemanager.controller.openapi.MenuControllerDocs;
import br.com.tastemanager.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.dto.request.MenuRequestDTO;
import br.com.tastemanager.dto.response.MenuResponseDTO;
import br.com.tastemanager.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController implements MenuControllerDocs {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    public ResponseEntity<String> createMenu(@PathVariable Long restaurantId, @RequestBody MenuRequestDTO menu) {
        var response = menuService.createMenu(restaurantId, menu);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<List<MenuResponseDTO>> getMenusByRestaurant(@PathVariable Long restaurantId) {
        List<MenuResponseDTO> menus = menuService.getMenusByRestaurant(restaurantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(menus);
    }

    public  ResponseEntity<?>  getAllMenus(@RequestParam int page, @RequestParam int size) {
            var response = menuService.findAll(page, size);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    public ResponseEntity<String> updateMenu(@PathVariable Long id, @RequestBody MenuItemUpdateRequestDTO menuItemRequest) {
        var response = menuService.updateMenu(id, menuItemRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<String> deleteMenu(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.ok("Menu deleted successfully");
    }

    public ResponseEntity<String> deleteMenuItem(@PathVariable Long menuId, @PathVariable Long itemId) {
        menuService.deleteMenuItem(menuId, itemId);
        return ResponseEntity.ok("Menu item deleted successfully");
    }
}