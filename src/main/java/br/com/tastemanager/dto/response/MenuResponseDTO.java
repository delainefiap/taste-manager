package br.com.tastemanager.dto.response;

import java.util.List;

public class MenuResponseDTO {

    private Long menuId;
    private List<MenuItemResponseDTO> items;
    private RestaurantSummaryDTO restaurant;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
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