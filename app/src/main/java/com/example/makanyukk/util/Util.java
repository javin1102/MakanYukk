package com.example.makanyukk.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Util {
    public static final String USERS_COLLECTION_REF = "Users";
    public static final String USER_ID_REF = "userID";
    public static final String USER_NAME_REF = "username";
    public static final String USER_PHONE_NUMBER_REF ="phoneNumber";
    public static final String USER_HAS_RES_REF = "hasRes";


    public static final String CATEGORY_NAME = "category_name";
    public static final String MAIN_CATEGORY_COLLECTION_REF = "Category";
    public static final String EXPLORE_CATEGORY_COLLECTION_REF = "ExploreCategory";

    //RESTAURANT STRING REFERENCE
    public static final String RESTAURANT_COLLECTION_REF = "Restaurants";
    public static final String RESTAURANT_NAME = "name";
    public static final String RESTAURANT_LATITUDE = "latitude";
    public static final String RESTAURANT_LONGTIUDE = "longitude";
    public static final String RESTAURANT_DESCRIPTION = "description";
    public static final String RESTAURANT_CATEGORIES = "categories";
    public static final String RESTAURANT_LOGO = "logo";
    public static final String RESTAURANT_ID = "id";
    public static final String RESTAURANT_USER_ID = "userId";

    //Menu STRING REFERENECE
    public static final String MENU_URL = "menuImageURL";
    public static final String MENU_COLLECTION_REFERENCE = "Menu";
    public static final String MENU_CATEGORY_COLLECTION_REFERENCE = "Menu Category";
    public static final String MENU_NAME = "menuName";
    public static final String MENU_PRICE = "menuPrice";
    public static final String MENU_CATEGORY = "menuCategory";
    public static final String MENU_RECOMMENDATION = "recommendedMenu";
    public static final String MENU_AVAILABLE = "available";
    public static final String MENU_STORAGE_REFERENCE = "res_menu";
    public static final String MENU_RECOMMENDED_CATEGORY_REFERENCE = "Recommended Menu";


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
