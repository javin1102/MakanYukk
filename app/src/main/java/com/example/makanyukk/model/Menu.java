package com.example.makanyukk.model;

public class Menu {
    private String menuImageURL;
    private String menuName;
    private String menuPrice;
    private String menuCategory;
    private boolean recommendedMenu;
    private boolean isAvailable;

    public String getMenuImageURL() {
        return menuImageURL;
    }

    public void setMenuImageURL(String menuImageURL) {
        this.menuImageURL = menuImageURL;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(String menuPrice) {
        this.menuPrice = menuPrice;
    }

    public String getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(String menuCategory) {
        this.menuCategory = menuCategory;
    }

    public boolean isRecommendedMenu() {
        return recommendedMenu;
    }

    public void setRecommendedMenu(boolean recommendedMenu) {
        this.recommendedMenu = recommendedMenu;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
