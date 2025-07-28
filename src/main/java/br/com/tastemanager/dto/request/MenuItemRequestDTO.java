package br.com.tastemanager.dto.request;

public class MenuItemRequestDTO {

    private String name;
    private String description;
    private Double price;

    private Boolean isAvailableOnlyInRestaurant;
    private String photoPath;

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

    public Boolean getAvailableOnlyInRestaurant() {
        return isAvailableOnlyInRestaurant;
    }

    public void setAvailableOnlyInRestaurant(Boolean availableOnlyInRestaurant) {
        isAvailableOnlyInRestaurant = availableOnlyInRestaurant;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}