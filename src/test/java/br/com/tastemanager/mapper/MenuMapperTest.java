package br.com.tastemanager.mapper;

import br.com.tastemanager.dto.request.MenuItemRequestDTO;
import br.com.tastemanager.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.dto.request.MenuRequestDTO;
import br.com.tastemanager.dto.response.MenuResponseDTO;
import br.com.tastemanager.entity.ItemMenu;
import br.com.tastemanager.entity.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MenuMapperTest {

    private MenuMapper menuMapper;

    @BeforeEach
    void setUp() {
        menuMapper = Mappers.getMapper(MenuMapper.class);
    }

    @Test
    void shouldMapMenuRequestDTOToEntity() {
        MenuRequestDTO dto = new MenuRequestDTO();


        Menu menu = menuMapper.toEntity(dto);

        assertNotNull(menu);
        assertNull(menu.getMenuId());
    }



    @Test
    void shouldMapMenuItemRequestDTOToItemMenuEntity() {
        MenuItemRequestDTO dto = new MenuItemRequestDTO();
        dto.setName("Pizza");
        dto.setPrice(45.00);
        dto.setDescription("Delicious pizza");
        dto.setAvailableOnlyAtRestaurant(true);
        dto.setPhotoPath("/images/pizza.jpg");

        ItemMenu item = menuMapper.toEntity(dto);

        assertNotNull(item);
        assertEquals("Pizza", item.getName());
        assertEquals(45.00, item.getPrice());
        assertEquals("Delicious pizza", item.getDescription());
        assertTrue(item.getAvailableOnlyAtRestaurant());
        assertEquals("/images/pizza.jpg", item.getPhotoPath());
    }

    @Test
    void shouldMapMenuToMenuResponseDTO() {
        Menu menu = new Menu();
        menu.setMenuId(10L);
        menu.setItems(Collections.emptyList());

        MenuResponseDTO dto = menuMapper.toResponseDTO(menu);

        assertNotNull(dto);
        assertEquals(10L, dto.getMenuId());
        assertNotNull(dto.getItems());
        assertTrue(dto.getItems().isEmpty());
    }

    @Test
    void shouldUpdateItemMenuFromUpdateDTO() {
        MenuItemUpdateRequestDTO updateDTO = new MenuItemUpdateRequestDTO();
        updateDTO.setName("Updated Name");
        updateDTO.setPrice(55.00);
        updateDTO.setDescription("Updated Description");
        updateDTO.setPhotoPath("/new/image.jpg");
        updateDTO.setAvailableOnlyAtRestaurant(false);

        ItemMenu item = new ItemMenu();
        item.setName("Old Name");
        item.setPrice(10.00);
        item.setDescription("Old Description");
        item.setPhotoPath("/old/image.jpg");
        item.setAvailableOnlyAtRestaurant(true);

        menuMapper.updateItemFromDTO(updateDTO, item);

        assertEquals("Updated Name", item.getName());
        assertEquals(55.00, item.getPrice());
        assertEquals("Updated Description", item.getDescription());
        assertEquals("/new/image.jpg", item.getPhotoPath());
        assertFalse(item.getAvailableOnlyAtRestaurant());
    }
}
