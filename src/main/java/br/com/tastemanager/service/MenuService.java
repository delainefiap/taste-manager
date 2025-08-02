package br.com.tastemanager.service;

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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuMapper menuMapper;
    private final MenuValidator menuValidator;

    public MenuService(MenuRepository menuRepository,
                       RestaurantRepository restaurantRepository,
                       MenuMapper menuMapper,
                       MenuValidator menuValidator) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuMapper = menuMapper;
        this.menuValidator = menuValidator;
    }

    public MenuResponseDTO createMenu(Long restaurantId, MenuRequestDTO menuRequest) {
        menuValidator.validateMenuRequest(menuRequest);

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        Menu menu = menuRepository.findByRestaurantId(restaurantId)
                .orElseGet(() -> {
                    Menu newMenu = new Menu();
                    newMenu.setRestaurant(restaurant);
                    newMenu.setItems(new ArrayList<>());
                    return newMenu;
                });

        menuRequest.getItems().forEach(itemRequest -> {
            ItemMenu item = menuMapper.toEntity(itemRequest);
            item.setMenu(menu);
            menu.getItems().add(item);
        });

        Menu savedMenu = menuRepository.save(menu);

        return menuMapper.toResponseDTO(savedMenu);
    }

    public List<MenuResponseDTO> getMenusByRestaurant(Long restaurantId) {
        return menuRepository.findByRestaurantId(restaurantId).stream()
                .map(menuMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MenuResponseDTO updateMenu(Long id, MenuItemUpdateRequestDTO menuItemUpdateRequestDTO) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

        ItemMenu itemToUpdate = menu.getItems().stream()
                .filter(item -> item.getItemMenuId().equals(menuItemUpdateRequestDTO.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        menuMapper.updateItemFromDTO(menuItemUpdateRequestDTO, itemToUpdate);

        Menu updatedMenu = menuRepository.save(menu);

        return menuMapper.toResponseDTO(updatedMenu);
    }

    public void deleteMenu(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu não encontrado"));

        menu.getItems().clear();
        menuRepository.save(menu);

        menuRepository.deleteById(id);
    }

    public void deleteMenuItem(Long menuId, Long itemId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menu não encontrado"));

        ItemMenu itemToRemove = menu.getItems().stream()
                .filter(item -> item.getItemMenuId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado"));

        menu.getItems().remove(itemToRemove);
        menuRepository.save(menu);
    }
}