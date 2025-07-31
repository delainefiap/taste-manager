package br.com.tastemanager.entity;

import jakarta.persistence.*;

@Entity
public class ItemMenu {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemMenuId;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    private String name;
    private String description;
    private double price;
    private String photoPath;
    private boolean availableOnlyAtRestaurant;


    public Long getItemMenuId() {
        return itemMenuId;
    }

    public void setItemMenuId(Long itemMenuId) {
        this.itemMenuId = itemMenuId;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public boolean getAvailableOnlyAtRestaurant() {
        return availableOnlyAtRestaurant;
    }

    public void setAvailableOnlyAtRestaurant(boolean availableOnlyAtRestaurant) {
        this.availableOnlyAtRestaurant = availableOnlyAtRestaurant;
    }
}