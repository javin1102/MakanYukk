package com.example.makanyukk.model;

import java.util.List;

public class MenuCategory {
    private List<Menu> menuList;
    private Category category;

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


}
