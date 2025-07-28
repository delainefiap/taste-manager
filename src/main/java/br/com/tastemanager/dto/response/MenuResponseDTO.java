package br.com.tastemanager.dto.response;

import br.com.tastemanager.entity.Restaurant;

public class MenuResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean isAvailableOnlyAtRestaurant;
    private String photoPath;
    private Restaurant restaurant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailableOnlyAtRestaurant() {
        return isAvailableOnlyAtRestaurant;
    }

    public void setAvailableOnlyAtRestaurant(Boolean availableOnlyAtRestaurant) {
        isAvailableOnlyAtRestaurant = availableOnlyAtRestaurant;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}