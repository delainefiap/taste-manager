package br.com.tastemanager.controller;

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

//    @GetMapping("/find-all-by-restaurant/{restaurantId}")
//    public ResponseEntity<List<Menu>> getMenusByRestaurant(@PathVariable Long restaurantId) {
//        List<Menu> menus = menuService.getMenusByRestaurant(restaurantId);
//        return ResponseEntity.ok(menus);
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @RequestBody Menu menu) {
//        Menu updatedMenu = menuService.updateMenu(id, menu);
//        return ResponseEntity.ok(updatedMenu);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteMenu(@PathVariable Long id) {
//        menuService.deleteMenu(id);
//        return ResponseEntity.ok("Menu deleted successfully");
//    }
}