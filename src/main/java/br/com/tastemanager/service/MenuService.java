package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.MenuRequestDTO;
import br.com.tastemanager.dto.response.MenuResponseDTO;
import br.com.tastemanager.entity.Menu;
import br.com.tastemanager.entity.MenuItem;
import br.com.tastemanager.entity.Restaurant;
import br.com.tastemanager.repository.MenuRepository;
import br.com.tastemanager.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

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
            return item;
        }).collect(Collectors.toList()));

        menuRepository.save(menu);

        return new MenuResponseDTO();
    }
}