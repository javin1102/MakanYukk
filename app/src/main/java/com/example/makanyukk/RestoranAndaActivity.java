package com.example.makanyukk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.makanyukk.databinding.ActivityRestoranAndaBinding;
import com.example.makanyukk.model.Restaurant;
import com.example.makanyukk.util.RestaurantsAPI;
import com.example.makanyukk.util.UsersAPI;
import com.example.makanyukk.util.Util;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

public class RestoranAndaActivity extends AppCompatActivity {
    private ActivityRestoranAndaBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userReference = db.collection(Util.USERS_COLLECTION_REF);
    private CollectionReference restaurantReference = db.collection(Util.RESTAURANT_COLLECTION_REF);
    private boolean hasRes;
    private final int REQUEST_CODE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_anda);


        binding = DataBindingUtil.setContentView(RestoranAndaActivity.this,R.layout.activity_restoran_anda);

        setSupportActionBar(binding.resAndaToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        userReference.whereEqualTo(Util.USER_ID_REF,UsersAPI.getInstance().getUserID()).get().addOnSuccessListener(queryDocumentSnapshots -> {
           for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
               hasRes = snapshot.getBoolean(Util.USER_HAS_RES_REF);
           }

            if(hasRes){
                binding.resAndaHasres.setVisibility(View.VISIBLE);
                binding.resAndaNores.setVisibility(View.GONE);
                Restaurant restaurant = RestaurantsAPI.getInstance().getRestaurant();
                Uri uri = Uri.parse(restaurant.getLogoUrl());
                Picasso.get().load(uri).fit().centerCrop().into(binding.resAndaLogoIV);
                binding.resAndaResName.setText(restaurant.getName());
                binding.resAndaResEmail.setText(restaurant.getEmail());
                binding.resAndaResNumber.setText(restaurant.getAddress());

            }
            else{
                binding.resAndaHasres.setVisibility(View.GONE);
                binding.resAndaNores.setVisibility(View.VISIBLE);
                binding.resAndaDaftarSekarangTV.setOnClickListener(v -> {
                    Intent intent = new Intent(RestoranAndaActivity.this,DaftarRestoranActivity.class);
                    startActivityForResult(intent,REQUEST_CODE);


                });
            }

        }).addOnFailureListener(e -> {

        });

        binding.resAndaLayoutButton.setOnClickListener(v->{
            startActivity(new Intent(RestoranAndaActivity.this,MenuAndaActivity.class));
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            finish();
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
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