package com.example.makanyukk.model;

public class Menu {
    private String menuImageURL;
    private String menuName;
    private int menuPrice;
    private String menuCategory;
    private boolean recommendedMenu;
    private boolean isAvailable;
    public Menu(){}
    public Menu(String menuImageURL, String menuName, int menuPrice, String menuCategory, boolean recommendedMenu, boolean isAvailable) {
        this.menuImageURL = menuImageURL;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuCategory = menuCategory;
        this.recommendedMenu = recommendedMenu;
        this.isAvailable = isAvailable;
    }


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

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
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
