package br.com.tastemanager.dto.response;

import br.com.tastemanager.entity.Restaurant;

import java.util.List;

public class MenuResponseDTO {

    private Long id;
    private List<MenuItemResponseDTO> items;
    private RestaurantSummaryDTO restaurant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MenuItemResponseDTO> getItems() {
        return items;
    }

    public void setItems(List<MenuItemResponseDTO> items) {
        this.items = items;
    }

    public RestaurantSummaryDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantSummaryDTO restaurant) {
        this.restaurant = restaurant;
    }
}