package com.example.makanyukk;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.makanyukk.adapter.RestaurantMenuViewAdapter;
import com.example.makanyukk.databinding.FragmentRestaurantMenulistBinding;
import com.example.makanyukk.model.Category;
import com.example.makanyukk.model.Menu;
import com.example.makanyukk.model.MenuCategory;
import com.example.makanyukk.model.Restaurant;
import com.example.makanyukk.util.RestaurantsAPI;
import com.example.makanyukk.util.Util;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class RestaurantMenuListFragment extends Fragment {

    private List<MenuCategory> menuCategoryList;
    private FragmentRestaurantMenulistBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference restaurantReference = db.collection(Util.RESTAURANT_COLLECTION_REF);
    private String selectedResId;
    private final String TAG  = "BEKA";


    public RestaurantMenuListFragment() {
        // Required empty public constructor
    }

    public static RestaurantMenuListFragment newInstance() {
        RestaurantMenuListFragment fragment = new RestaurantMenuListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuCategoryList = new ArrayList<>();
        selectedResId = RestaurantsAPI.getInstance().getSelectedRestaurant().getId();

        if(selectedResId != null){
            CollectionReference menuCategoryReference = restaurantReference.document(selectedResId).collection(Util.MENU_CATEGORY_COLLECTION_REFERENCE);
                menuCategoryReference.get().addOnSuccessListener(queryDocumentSnapshots -> {

                for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                    Category category =  new Category();
                    category.setCategoryName(snapshot.getString("categoryName"));
                    MenuCategory menuCategory = new MenuCategory();
                    menuCategory.setCategory(category);

                    menuCategoryReference.document(snapshot.getId()).collection(Util.MENU_COLLECTION_REFERENCE).get().addOnSuccessListener(queryDocumentSnapshots1 -> {
                        List<Menu> menuList = new ArrayList<>();
                        for (QueryDocumentSnapshot snapshot1 : queryDocumentSnapshots1){
                            Menu menu = snapshot1.toObject(Menu.class);
                            menuList.add(menu);
                        }
                        menuCategory.setMenuList(menuList);
                        menuCategoryList.add(menuCategory);
                        RestaurantMenuViewAdapter adapter = new RestaurantMenuViewAdapter(menuCategoryList,getContext());
                        binding.restaurantMenuLayoutRV.setAdapter(adapter);
                    }).addOnFailureListener(e -> {

                    });

                }

            }).addOnFailureListener(e -> {

            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentRestaurantMenulistBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        binding.restaurantMenuLayoutRV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.restaurantMenuLayoutRV.setLayoutManager(linearLayoutManager);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}