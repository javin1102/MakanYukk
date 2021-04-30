package com.example.makanyukk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.makanyukk.adapter.MenuAndaViewAdapter;
import com.example.makanyukk.databinding.ActivityMenuAndaBinding;
import com.example.makanyukk.model.Category;
import com.example.makanyukk.model.MenuCategory;
import com.example.makanyukk.util.RestaurantsAPI;
import com.example.makanyukk.util.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuAndaActivity extends AppCompatActivity {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference restaurantReference = db.collection(Util.RESTAURANT_COLLECTION_REF);
    private CollectionReference menuCategoryReference = restaurantReference.document(RestaurantsAPI.getInstance().getRestaurant().getId())
            .collection(Util.MENU_CATEGORY_COLLECTION_REFERENCE);

    private ActivityMenuAndaBinding binding;
    private final int REQUEST_CODE = 3;
    private List<com.example.makanyukk.model.Menu> menuList;
    private String TAG = "menuanda";
    private List<MenuCategory> menuCategoryList;
    private MenuAndaViewAdapter menuAndaLayouRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_anda);
        binding = DataBindingUtil.setContentView(MenuAndaActivity.this,R.layout.activity_menu_anda);

        menuList = new ArrayList<>();
        menuCategoryList = new ArrayList<>();



        Toolbar toolbar = findViewById(binding.menuAndaToolbar.getId());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        restaurantReference.document(RestaurantsAPI.getInstance().getRestaurant().getId()).collection(Util.MENU_CATEGORY_COLLECTION_REFERENCE)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
            if(queryDocumentSnapshots.isEmpty()){
                binding.menuAndaNoMenu.setVisibility(View.VISIBLE);
                binding.menuAndaHasMenu.setVisibility(View.GONE);
            }
            else
            {
                binding.menuAndaNoMenu.setVisibility(View.GONE);
                binding.menuAndaHasMenu.setVisibility(View.VISIBLE);

                for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                    Category category = new Category();
                    category.setCategoryName(snapshot.getId());
                    MenuCategory menuCategory = new MenuCategory();
                    menuCategory.setCategory(category);

                    menuCategoryReference.document(snapshot.getId()).collection(Util.MENU_COLLECTION_REFERENCE).get().addOnSuccessListener(documentSnapshot -> {
                        List<com.example.makanyukk.model.Menu> menuList = new ArrayList<>();
                        for(QueryDocumentSnapshot documentSnapshot1 : documentSnapshot){
                            menuList.add(documentSnapshot1.toObject(com.example.makanyukk.model.Menu.class));

                        }
                        menuCategory.setMenuList(menuList);
                        menuCategoryList.add(menuCategory);
//                        for(MenuCategory menuCategory1 : menuCategoryList){
//                            for(com.example.makanyukk.model.Menu menu : menuCategory1.getMenuList()){
//                                Log.d(TAG, "category: " + menu.getMenuCategory());
//                                Log.d(TAG, "category: " + menu.getMenuImageURL());
//                                Log.d(TAG, "category: " + menu.getMenuName());
//                                Log.d(TAG, "category: " + menu.getMenuPrice());
//                                Log.d(TAG, "category: " + menu.isRecommendedMenu());
//                            }
//
//
//                        }

                        MenuAndaViewAdapter menuAndaViewAdapter = new MenuAndaViewAdapter(menuCategoryList);
                        binding.menuAndaRV.setAdapter(menuAndaViewAdapter);
                        binding.menuAndaRV.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MenuAndaActivity.this);
                        binding.menuAndaRV.setLayoutManager(linearLayoutManager);



                    });



                }









            }


        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        else if (id == R.id.menuAnda_add_button){
            startActivityForResult(new Intent(MenuAndaActivity.this,TambahMenuActivity.class),REQUEST_CODE);
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_anda_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            finish();
            startActivity(getIntent());
        }
    }
}