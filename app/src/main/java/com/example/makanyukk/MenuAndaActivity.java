package com.example.makanyukk;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.makanyukk.databinding.ActivityMenuAndaBinding;
import com.example.makanyukk.util.RestaurantsAPI;
import com.example.makanyukk.util.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MenuAndaActivity extends AppCompatActivity {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference restaurantReference = db.collection(Util.USER_RESTAURANT_COLLECTION_REF);
    private ActivityMenuAndaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_anda);
        binding = DataBindingUtil.setContentView(MenuAndaActivity.this,R.layout.activity_menu_anda);

        Toolbar toolbar = findViewById(binding.menuAndaToolbar.getId());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        restaurantReference.document(RestaurantsAPI.getInstance().getRestaurant().getId()).collection("Menu")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
            if(queryDocumentSnapshots.isEmpty()){
                binding.menuAndaNoMenu.setVisibility(View.VISIBLE);
                binding.menuAndaHasMenu.setVisibility(View.GONE);
            }
            else{
                binding.menuAndaNoMenu.setVisibility(View.GONE);
                binding.menuAndaHasMenu.setVisibility(View.VISIBLE);

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
}