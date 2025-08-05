package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.MenuItemRequestDTO;
import br.com.tastemanager.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.dto.request.MenuRequestDTO;
import br.com.tastemanager.dto.response.MenuResponseDTO;
import br.com.tastemanager.entity.ItemMenu;
import br.com.tastemanager.entity.Menu;
import br.com.tastemanager.entity.Restaurant;
import br.com.tastemanager.mapper.MenuMapper;
import br.com.tastemanager.repository.MenuRepository;
import br.com.tastemanager.repository.RestaurantRepository;
import br.com.tastemanager.validator.MenuValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuMapper menuMapper;

    @Mock
    private MenuValidator menuValidator;

    @InjectMocks
    private MenuService menuService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMenu_shouldCreateMenuWithItems() {
        Long restaurantId = 1L;

        MenuRequestDTO requestDTO = new MenuRequestDTO();
        MenuItemRequestDTO itemDTO = new MenuItemRequestDTO();
        itemDTO.setName("Pizza");
        itemDTO.setDescription("Pizza de calabresa");
        itemDTO.setPrice((40.00));
        requestDTO.setItems(List.of(itemDTO));

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        Menu emptyMenu = new Menu();
        emptyMenu.setItems(new ArrayList<>());
        emptyMenu.setRestaurant(restaurant);

        ItemMenu item = new ItemMenu();
        item.setName("Pizza");

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuRepository.findByRestaurantId(restaurantId)).thenReturn(Optional.empty());
        when(menuMapper.toEntity(itemDTO)).thenReturn(item);
        when(menuRepository.save(any(Menu.class))).thenReturn(emptyMenu);

        String result = menuService.createMenu(restaurantId, requestDTO);

        assertEquals("Menu and items created successfully ", result);
        verify(menuValidator).validateMenuRequest(requestDTO);
        verify(menuRepository).save(any(Menu.class));
    }

    @Test
    void updateMenu_shouldUpdateItemSuccessfully() {
        Long menuId = 1L;
        Long itemId = 10L;

        MenuItemUpdateRequestDTO updateDTO = new MenuItemUpdateRequestDTO();
        updateDTO.setId(itemId);
        updateDTO.setName("Novo nome");

        ItemMenu item = new ItemMenu();
        item.setItemMenuId(itemId);
        item.setName("Antigo nome");

        Menu menu = new Menu();
        menu.setItems(new ArrayList<>(List.of(item)));

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));
        when(menuRepository.save(menu)).thenReturn(menu);

        String result = menuService.updateMenu(menuId, updateDTO);

        assertEquals("Item updated successfully ", result);
        verify(menuMapper).updateItemFromDTO(updateDTO, item);
        verify(menuRepository).save(menu);
    }

    @Test
    void deleteMenu_shouldRemoveMenu() {
        Long menuId = 1L;

        Menu menu = new Menu();
        menu.setItems(new ArrayList<>(List.of(new ItemMenu())));

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));

        menuService.deleteMenu(menuId);

        assertTrue(menu.getItems().isEmpty());
        verify(menuRepository).save(menu);
        verify(menuRepository).deleteById(menuId);
    }

    @Test
    void deleteMenuItem_shouldRemoveSpecificItem() {
        Long menuId = 1L;
        Long itemId = 100L;

        ItemMenu item = new ItemMenu();
        item.setItemMenuId(itemId);

        Menu menu = new Menu();
        menu.setItems(new ArrayList<>(List.of(item)));

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));

        menuService.deleteMenuItem(menuId, itemId);

        assertTrue(menu.getItems().isEmpty());
        verify(menuRepository).save(menu);
    }

    @Test
    void createMenu_shouldThrowExceptionWhenRestaurantNotFound() {
        Long restaurantId = 99L;
        MenuRequestDTO request = new MenuRequestDTO();

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                menuService.createMenu(restaurantId, request));

        assertEquals("Restaurant not found", exception.getMessage());
    }

    @Test
    void getMenusByRestaurant_ShouldReturnMenuResponseDTOList() {
        Long restaurantId = 1L;

        Menu menu = new Menu();
        menu.setMenuId(1L);

        MenuResponseDTO menuResponseDTO = new MenuResponseDTO();

        when(menuRepository.findByRestaurantId(restaurantId)).thenReturn(Optional.of(menu));
        when(menuMapper.toResponseDTO(menu)).thenReturn(menuResponseDTO);

        List<MenuResponseDTO> result = menuService.getMenusByRestaurant(restaurantId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(menuResponseDTO, result.get(0));
        verify(menuRepository).findByRestaurantId(restaurantId);
        verify(menuMapper).toResponseDTO(menu);
    }

    @Test
    void findAll_ShouldReturnListOfItemMaps() {
        int page = 1;
        int size = 5;

        Menu menu = new Menu();
        menu.setMenuId(1L);

        ItemMenu item = new ItemMenu();
        item.setItemMenuId(100L);
        item.setAvailableOnlyAtRestaurant(true);
        item.setDescription("Item description");
        item.setName("Item name");
        item.setPhotoPath("photo/path");
        item.setPrice(50.0);
        item.setMenu(menu);

        menu.setItems(List.of(item));

        Pageable pageable = PageRequest.of(page - 1, size);

        when(menuRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(menu)));

        List<Map<String, Object>> result = menuService.findAll(page, size);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).get("itemMenuId"));
        assertEquals(true, result.get(0).get("availableOnlyAtRestaurant"));
        assertEquals("Item description", result.get(0).get("description"));
        assertEquals("Item name", result.get(0).get("name"));
        assertEquals("photo/path", result.get(0).get("photoPath"));
        assertEquals(50.0, result.get(0).get("price"));
        assertEquals(1L, result.get(0).get("menuId"));

        verify(menuRepository).findAll(pageable);
    }
}
