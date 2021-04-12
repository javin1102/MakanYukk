package com.example.makanyukk.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Util {
    public static final String USERS_COLLECTION_REF = "Users";
    public static final String USER_ID_REF = "userId";
    public static final String USERNAME_REF = "username";
    public static final String USER_PHONE_NUMBER ="phone_number";
    public static final String CATEGORY_NAME = "category_name";
    public static final String MAIN_CATEGORY_COLLECTION_REF = "Category";
    public static final String EXPLORE_CATEGORY_COLLECTION_REF = "ExploreCategory";
    public static final String USER_RESTAURANT_COLLECTION_REF = "Restaurants";

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
