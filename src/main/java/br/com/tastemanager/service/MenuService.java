package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.dto.request.MenuRequestDTO;
import br.com.tastemanager.dto.response.MenuItemResponseDTO;
import br.com.tastemanager.dto.response.MenuResponseDTO;
import br.com.tastemanager.dto.response.RestaurantSummaryDTO;
import br.com.tastemanager.entity.ItemMenu;
import br.com.tastemanager.entity.Menu;
import br.com.tastemanager.entity.Restaurant;
import br.com.tastemanager.repository.MenuRepository;
import br.com.tastemanager.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public MenuResponseDTO createMenu(Long restaurantId, MenuRequestDTO menuRequest) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));

        Menu menu = menuRepository.findByRestaurantId(restaurantId)
                .orElseGet(() -> {
                    Menu newMenu = new Menu();
                    newMenu.setRestaurant(restaurant);
                    newMenu.setItems(new ArrayList<>());
                    return newMenu;
                });

        if (menu.getItems() == null) {
            menu.setItems(new ArrayList<>());
        }

        menuRequest.getItems().forEach(itemRequest -> {
            ItemMenu item = new ItemMenu();
            item.setName(itemRequest.getName());
            item.setDescription(itemRequest.getDescription());
            item.setPrice(itemRequest.getPrice());
            item.setPhotoPath(itemRequest.getPhotoPath());
            item.setAvailableOnlyAtRestaurant(itemRequest.getAvailableOnlyAtRestaurant());
            item.setMenu(menu);
            menu.getItems().add(item);
        });

        Menu savedMenu = menuRepository.save(menu);
        return convertToResponseDTO(savedMenu);
    }


    public List<MenuResponseDTO> getMenusByRestaurant(Long restaurantId) {
        return menuRepository.findByRestaurantId(restaurantId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public MenuResponseDTO updateMenu(Long id, MenuItemUpdateRequestDTO menuItemUpdateRequestDTO) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

        ItemMenu itemToUpdate = menu.getItems().stream()
                .filter(item -> item.getItemMenuId().equals(menuItemUpdateRequestDTO.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Could not found this item in the menu"));

        if (menuItemUpdateRequestDTO.getName() != null) {
            itemToUpdate.setName(menuItemUpdateRequestDTO.getName());
        }
        if (menuItemUpdateRequestDTO.getDescription() != null) {
            itemToUpdate.setDescription(menuItemUpdateRequestDTO.getDescription());
        }
        if (menuItemUpdateRequestDTO.getPrice() != null) {
            itemToUpdate.setPrice(menuItemUpdateRequestDTO.getPrice());
        }
        if (menuItemUpdateRequestDTO.getPhotoPath() != null) {
            itemToUpdate.setPhotoPath(menuItemUpdateRequestDTO.getPhotoPath());
        }
        if (menuItemUpdateRequestDTO.getAvailableOnlyAtRestaurant() != null) {
            itemToUpdate.setAvailableOnlyAtRestaurant(menuItemUpdateRequestDTO.getAvailableOnlyAtRestaurant());
        }

        Menu updatedMenu = menuRepository.save(menu);

        return convertToResponseDTO(updatedMenu);
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

    private MenuResponseDTO convertToResponseDTO(Menu menu) {
        MenuResponseDTO response = new MenuResponseDTO();
        response.setMenuId(menu.getMenuId());
        response.setItems(menu.getItems().stream().map(item -> {
            MenuItemResponseDTO itemResponse = new MenuItemResponseDTO();
            itemResponse.setItemId(item.getItemMenuId());
            itemResponse.setName(item.getName());
            itemResponse.setDescription(item.getDescription());
            itemResponse.setPrice(item.getPrice());
            itemResponse.setPhotoPath(item.getPhotoPath());
            itemResponse.setAvailableOnlyAtRestaurant(item.getAvailableOnlyAtRestaurant());
            return itemResponse;
        }).collect(Collectors.toList()));

        RestaurantSummaryDTO restaurantSummary = new RestaurantSummaryDTO();
        restaurantSummary.setName(menu.getRestaurant().getName());
        response.setRestaurant(restaurantSummary);

        return response;
    }
}