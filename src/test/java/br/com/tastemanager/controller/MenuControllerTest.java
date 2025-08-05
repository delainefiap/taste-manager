package br.com.tastemanager.controller;

import br.com.tastemanager.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.dto.request.MenuRequestDTO;
import br.com.tastemanager.dto.response.MenuResponseDTO;
import br.com.tastemanager.service.MenuService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuControllerTest {

    @Mock
    private MenuService menuService;

    @InjectMocks
    private MenuController menuController;

    @Test
    void createMenuReturnsCreatedStatusWhenMenuIsValid() {
        Long restaurantId = 1L;
        MenuRequestDTO menuRequest = new MenuRequestDTO();
        String expectedResponse = "Menu created successfully";

        when(menuService.createMenu(restaurantId, menuRequest)).thenReturn(expectedResponse);

        ResponseEntity<String> response = menuController.createMenu(restaurantId, menuRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void getMenusByRestaurantReturnsMenusWhenRestaurantExists() {
        Long restaurantId = 1L;
        List<MenuResponseDTO> expectedMenus = List.of(new MenuResponseDTO());

        when(menuService.getMenusByRestaurant(restaurantId)).thenReturn(expectedMenus);

        ResponseEntity<List<MenuResponseDTO>> response = menuController.getMenusByRestaurant(restaurantId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedMenus, response.getBody());
    }


    @Test
    void updateMenuReturnsOkStatusWhenMenuIsUpdated() {
        Long menuId = 1L;
        MenuItemUpdateRequestDTO menuItemRequest = new MenuItemUpdateRequestDTO();
        String expectedResponse = "Menu updated successfully";

        when(menuService.updateMenu(menuId, menuItemRequest)).thenReturn(expectedResponse);

        ResponseEntity<String> response = menuController.updateMenu(menuId, menuItemRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void deleteMenuReturnsOkStatusWhenMenuIsDeleted() {
        Long menuId = 1L;

        doNothing().when(menuService).deleteMenu(menuId);

        ResponseEntity<String> response = menuController.deleteMenu(menuId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Menu deleted successfully", response.getBody());
    }

    @Test
    void deleteMenuItemReturnsOkStatusWhenMenuItemIsDeleted() {
        Long menuId = 1L;
        Long itemId = 2L;

        doNothing().when(menuService).deleteMenuItem(menuId, itemId);

        ResponseEntity<String> response = menuController.deleteMenuItem(menuId, itemId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Menu item deleted successfully", response.getBody());
    }

    @Test
    void getAllMenusReturnsOkStatusWithMenus() {
        int page = 0;
        int size = 10;
        List<Map<String, Object>> expectedMenus = List.of(
                Map.of("id", 1, "name", "Menu 1"),
                Map.of("id", 2, "name", "Menu 2")
        );

        when(menuService.findAll(page, size)).thenReturn(expectedMenus);

        ResponseEntity<?> response = menuController.getAllMenus(page, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMenus, response.getBody());
        verify(menuService, times(1)).findAll(page, size);
    }

    @Test
    void getAllMenusReturnsEmptyListWhenNoMenusExist() {
        int page = 0;
        int size = 10;

        when(menuService.findAll(page, size)).thenReturn(List.of());

        ResponseEntity<?> response = menuController.getAllMenus(page, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(), response.getBody());
        verify(menuService, times(1)).findAll(page, size);
    }
}