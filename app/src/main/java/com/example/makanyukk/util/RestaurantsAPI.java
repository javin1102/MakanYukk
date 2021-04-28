package com.example.makanyukk.util;

import com.example.makanyukk.model.Category;
import com.example.makanyukk.model.Restaurant;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class RestaurantsAPI {
    public static RestaurantsAPI instance;
    private Restaurant restaurant;
    public RestaurantsAPI(){}
    public static RestaurantsAPI getInstance(){
        if(instance == null)
        {
            instance = new RestaurantsAPI();
        }
        return instance;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
