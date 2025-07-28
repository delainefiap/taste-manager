package br.com.tastemanager.dto.request;

import java.util.List;

public class MenuRequestDTO {

    private Long restaurantId;
    private List<MenuItemRequestDTO> items;


    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<MenuItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<MenuItemRequestDTO> items) {
        this.items = items;
    }
}