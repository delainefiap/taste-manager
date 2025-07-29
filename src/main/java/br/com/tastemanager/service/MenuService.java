package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.MenuRequestDTO;
import br.com.tastemanager.dto.response.MenuItemResponseDTO;
import br.com.tastemanager.dto.response.MenuResponseDTO;
import br.com.tastemanager.dto.response.RestaurantSummaryDTO;
import br.com.tastemanager.entity.Menu;
import br.com.tastemanager.entity.MenuItem;
import br.com.tastemanager.entity.Restaurant;
import br.com.tastemanager.repository.MenuRepository;
import br.com.tastemanager.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        Menu menu = new Menu();
        menu.setRestaurant(restaurant);

        menu.setItems(menuRequest.getItems().stream().map(itemRequest -> {
            MenuItem item = new MenuItem();
            item.setName(itemRequest.getName());
            item.setDescription(itemRequest.getDescription());
            item.setPrice(itemRequest.getPrice());
            item.setPhotoPath(itemRequest.getPhotoPath());
            item.setAvailableOnlyAtRestaurant(itemRequest.getAvailableOnlyInRestaurant());
            item.setMenu(menu);
            return item;
        }).collect(Collectors.toList()));

        Menu savedMenu = menuRepository.save(menu);

        MenuResponseDTO responseDTO = new MenuResponseDTO();
        responseDTO.setId(savedMenu.getId());
        responseDTO.setItems(savedMenu.getItems().stream().map(item -> {
            MenuItemResponseDTO itemResponse = new MenuItemResponseDTO();
            itemResponse.setName(item.getName());
            itemResponse.setDescription(item.getDescription());
            itemResponse.setPrice(item.getPrice());
            itemResponse.setPhotoPath(item.getPhotoPath());
            itemResponse.setAvailableOnlyAtRestaurant(item.getAvailableOnlyAtRestaurant()); // Adicionado
            return itemResponse;
        }).collect(Collectors.toList()));

        // Preenche apenas o id e o nome do restaurante
        RestaurantSummaryDTO restaurantSummary = new RestaurantSummaryDTO();
        restaurantSummary.setName(restaurant.getName());
        responseDTO.setRestaurant(restaurantSummary);

        return responseDTO;
    }

    public List<MenuResponseDTO> getMenusByRestaurant(Long restaurantId) {
        return menuRepository.findByRestaurantId(restaurantId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public MenuResponseDTO updateMenu(Long id, MenuRequestDTO menuRequest) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

        menu.setItems(menuRequest.getItems().stream().map(itemRequest -> {
            MenuItem item = new MenuItem();
            item.setName(itemRequest.getName());
            item.setDescription(itemRequest.getDescription());
            item.setPrice(itemRequest.getPrice());
            item.setPhotoPath(itemRequest.getPhotoPath());
            item.setAvailableOnlyAtRestaurant(itemRequest.getAvailableOnlyInRestaurant());
            item.setMenu(menu);
            return item;
        }).collect(Collectors.toList()));

        Menu updatedMenu = menuRepository.save(menu);

        return convertToResponseDTO(updatedMenu);
    }

    public void deleteMenu(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

        // Remove os itens associados ao menu
        menu.getItems().clear();
        menuRepository.save(menu);

        // Exclui o menu
        menuRepository.deleteById(id);
    }

    public void deleteMenuItem(Long menuId, Long itemId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

        // Filtra o item a ser removido
        MenuItem itemToRemove = menu.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        // Remove o item do menu
        menu.getItems().remove(itemToRemove);
        menuRepository.save(menu);
    }

    private MenuResponseDTO convertToResponseDTO(Menu menu) {
        MenuResponseDTO response = new MenuResponseDTO();
        response.setId(menu.getId());
        response.setItems(menu.getItems().stream().map(item -> {
            MenuItemResponseDTO itemResponse = new MenuItemResponseDTO();
            itemResponse.setName(item.getName());
            itemResponse.setDescription(item.getDescription());
            itemResponse.setPrice(item.getPrice());
            itemResponse.setPhotoPath(item.getPhotoPath());
            itemResponse.setAvailableOnlyAtRestaurant(item.getAvailableOnlyAtRestaurant());
            return itemResponse;
        }).collect(Collectors.toList()));
        return response;
    }

}