package com.example.makanyukk;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import com.example.makanyukk.databinding.ActivityDaftarRestoranBinding;
import com.example.makanyukk.model.Category;
import com.example.makanyukk.model.Restaurant;
import com.example.makanyukk.util.RestaurantsAPI;
import com.example.makanyukk.util.UsersAPI;
import com.example.makanyukk.util.Util;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DaftarRestoranActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener {

    private ActivityDaftarRestoranBinding binding;
    private static final int RES_IMAGES_CODE = 1;
    private static final int RES_LOGO_CODE = 2;
    private List<Uri> imageUri;
    private Uri[]uris;
    private List<Category> categoryList;
    private Uri logoUri;

    private Category category1,category2,category3;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth.AuthStateListener authStateListener;
    private CollectionReference restaurantReference = db.collection(Util.USER_RESTAURANT_COLLECTION_REF);
    private CollectionReference categoryReference = db.collection(Util.MAIN_CATEGORY_COLLECTION_REF);


    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_restoran);

        storageReference =FirebaseStorage.getInstance().getReference();
        imageUri = new ArrayList<>();
        uris = new Uri[3];

        categoryList = new ArrayList<>();
        binding = DataBindingUtil.setContentView(DaftarRestoranActivity.this,R.layout.activity_daftar_restoran);
        Toolbar toolbar = findViewById(binding.daftarResToolbar.getId());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> {
            Log.d("1102", "onCreate: "+ Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());

        };



        binding.daftarResTambahTV.setOnClickListener(v -> {
            if(imageUri.size() < 3){
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                galleryIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(galleryIntent,"Select Picture"), RES_IMAGES_CODE);
            }

        });
        binding.daftarResLogoTambahTV.setOnClickListener(v->{
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(Intent.createChooser(galleryIntent,"Select Picture"), RES_LOGO_CODE);
        });



        binding.daftarResFoto1Close.setOnClickListener(this);
        binding.daftarResFoto2Close.setOnClickListener(this);
        binding.daftarResFoto3Close.setOnClickListener(this);
        binding.daftarResKategori1Close.setOnClickListener(this);
        binding.daftarResKategori2Close.setOnClickListener(this);
        binding.daftarResKategori3Close.setOnClickListener(this);


        binding.daftarResKategoriTambah.setOnClickListener(v->{
            if(categoryList.size() < 3){
                onClickshowCategory();
            }
        });

        binding.daftarResDaftarButton.setOnClickListener(v -> {
            if(isValid()){
                String name = capitalize(binding.daftarResNamaResET.getText().toString().trim());
                String email = binding.daftarResEmailET.getText().toString().trim();
                String description = capitalize(binding.daftarResDescriptionET.getText().toString().trim());
                String address = capitalize(binding.daftarResAlamatET.getText().toString().trim());
                String city = capitalize(binding.daftarResKotaET.getText().toString().trim());
                String zipCode = binding.daftarResKodeposET.getText().toString().trim();
                double latitude = Double.parseDouble(binding.daftarResLatitudeET.getText().toString().trim());
                double longitude = Double.parseDouble(binding.daftarResLongitudeET.getText().toString().trim());
                insertResData(name,description,email,address,city,zipCode,latitude,longitude);
                Util.hideKeyboard(this);
            }
        });

        binding.daftarResOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.daftarResDaftarButton.setEnabled(true);
                binding.linearLayout.setForeground(null);
                binding.linearLayout.setClickable(true);
                binding.progressBarHolder.setVisibility(View.GONE);
                startActivity(new Intent(DaftarRestoranActivity.this,RestoranAndaActivity.class));
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.daftar_res_foto1_close:
                removeImageList(0);
                binding.daftarResFoto1IV.setImageURI(null);
                binding.daftarResFoto1.setVisibility(View.GONE);
                break;
            case R.id.daftar_res_foto2_close:
                    removeImageList(1);
                binding.daftarResFoto2IV.setImageURI(null);
                binding.daftarResFoto2.setVisibility(View.GONE);
                break;
            case R.id.daftar_res_foto3_close:
                removeImageList(2);
                binding.daftarResFoto3IV.setImageURI(null);
                binding.daftarResFoto3.setVisibility(View.GONE);
                break;

            case R.id.daftar_res_kategori1_close:
                binding.daftarResKategori1.setVisibility(View.GONE);
                removeCategoryList(category1);
                category1 = null;
                break;
            case R.id.daftar_res_kategori2_close:
                binding.daftarResKategori2.setVisibility(View.GONE);

                removeCategoryList(category2);
                category2 = null;
                break;
            case R.id.daftar_res_kategori3_close:
                binding.daftarResKategori3.setVisibility(View.GONE);

                removeCategoryList(category3);
                category3 = null;
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            onBackPressed();
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
        if(requestCode == RES_IMAGES_CODE && resultCode == RESULT_OK){
            ClipData clipData = data.getClipData();
            if(clipData != null){
                if(clipData.getItemCount() + imageUri.size() <= 3){

                    for(int i = 0 ; i < clipData.getItemCount();i++){
                       imageUri.add(clipData.getItemAt(i).getUri());

                    }
                }
            }

            else if(data.getData() != null){
                imageUri.add(data.getData());
            }

            for(int i = 0 ; i < imageUri.size() ;i++){

                if(imageUri.get(i) != null){
                    if(i == 0){

                        binding.daftarResFoto1.setVisibility(View.VISIBLE);
                        binding.daftarResFoto1IV.setImageURI(imageUri.get(0));
                        uris[0] = imageUri.get(0);
                    }
                    if(i == 1){

                        binding.daftarResFoto2.setVisibility(View.VISIBLE);
                        binding.daftarResFoto2IV.setImageURI(imageUri.get(1));
                        uris[1] = imageUri.get(1);
                    }
                    if(i == 2){

                        binding.daftarResFoto3.setVisibility(View.VISIBLE);
                        binding.daftarResFoto3IV.setImageURI(imageUri.get(2));
                        uris[2] = imageUri.get(2);
                    }

                }
            }

        }
        if(requestCode == RES_LOGO_CODE && resultCode == RESULT_OK){
            if(data != null){
                logoUri = data.getData();
                binding.daftarResLogoIV.setImageURI(logoUri);
            }
        }

    }

    private void removeImageList(int index){
       for(int i = 0 ; i < imageUri.size();i++){
           if(imageUri.get(i) == uris[index])
           {
               Log.d("1102", "onActivityResult: "+i);
               imageUri.remove(i);
               uris[index] = null;

           }
       }
    }

    private boolean isValid(){
        if(!imageUri.isEmpty() && !TextUtils.isEmpty(binding.daftarResNamaResET.getText().toString().trim()) && !TextUtils.isEmpty(binding.daftarResEmailET.getText().toString().trim()) && !categoryList.isEmpty()
        &&!TextUtils.isEmpty(binding.daftarResAlamatET.getText().toString().trim()) && !TextUtils.isEmpty(binding.daftarResKotaET.getText().toString().trim())
        && !TextUtils.isEmpty(binding.daftarResKodeposET.getText().toString().trim()) && !TextUtils.isEmpty(binding.daftarResLatitudeET.getText().toString().trim())
        && !TextUtils.isEmpty(binding.daftarResLongitudeET.getText().toString().trim())
        &&(Double.parseDouble(binding.daftarResLatitudeET.getText().toString()) >= -90 && Double.parseDouble(binding.daftarResLatitudeET.getText().toString()) <=90 )
        &&(Double.parseDouble(binding.daftarResLongitudeET.getText().toString()) >= -180 && Double.parseDouble(binding.daftarResLongitudeET.getText().toString()) <=180 ))
        {
            return true;

        }
        return false;
    }

    public void onClickshowCategory(){
        AlertDialog.Builder menu = new AlertDialog.Builder(DaftarRestoranActivity.this);

        LayoutInflater inflater = this.getLayoutInflater();
        View menus = inflater.inflate(R.layout.category_popup_window,null);
        EditText editText = menus.findViewById(R.id.daftar_res_category_ET);

        menu.setView(menus);
        menu.setPositiveButton("SUBMIT", (dialog, which) -> {
            Log.d("1102", "onClickshowCategory: "+editText.getText());
            Category category = new Category();
            category.setCategoryName(editText.getText().toString().trim());
            categoryList.add(category);

            for(int i = 0 ; i < categoryList.size();i++){

                if(i == 0) {
                    category1 = categoryList.get(0);
                }
                if(i == 1) {
                    category2 = categoryList.get(1);
                }
                if(i == 2) {
                    category3 = categoryList.get(2);
                }

            }

            if(category1 != null) {
                binding.daftarResKategori1.setVisibility(View.VISIBLE);
                binding.daftarResCategory1TV.setText(category1.getCategoryName());
            }

            if(category2 != null) {
                binding.daftarResKategori2.setVisibility(View.VISIBLE);
                binding.daftarResCategory2TV.setText(category2.getCategoryName());
            }
            if(category3 != null) {
                binding.daftarResKategori3.setVisibility(View.VISIBLE);
                binding.daftarResCategory3TV.setText(category3.getCategoryName());
            }


        });

        menu.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
        menu.show();
    }

    private void removeCategoryList(Category category){
        for(int i = 0 ; i < categoryList.size();i++){
            if(categoryList.get(i).getCategoryName().equals(category.getCategoryName())){
                categoryList.remove(i);
                return;
            }
        }
    }

    private void insertResData(String resName, String resDescription,String email, String address, String city, String zipCode, double latitude, double longitude){

        //Loading Screen Animation
        binding.daftarResDaftarButton.setEnabled(false);
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressBarHolder.setVisibility(View.VISIBLE);
        binding.linearLayout.setForeground(new ColorDrawable(ContextCompat.getColor(this, R.color.light_gray)));
        binding.linearLayout.setClickable(false);
        binding.daftarResSV.setOnTouchListener((v, event) -> true);


        String id = restaurantReference.document().getId();
        List<String> resUri = new ArrayList<>();
        GeoPoint geoPoint = new GeoPoint(latitude,longitude);
        Restaurant restaurant = new Restaurant(categoryList);
        restaurant.setName(resName);
        restaurant.setDescription(resDescription);
        restaurant.setId(id);
        restaurant.setEmail(email);
        restaurant.setAddress(address);
        restaurant.setZipCode(zipCode);
        restaurant.setCity(city);
        restaurant.setGeoPoint(geoPoint);
        restaurant.setUserId(UsersAPI.getInstance().getUserId());
        restaurant.setRating(1f);

        //Adding logo to storage
        StorageReference filepath = storageReference.child("Restaurants").child(id).child("res_logo").child(resName+Timestamp.now().getNanoseconds());
        filepath.putFile(logoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        restaurant.setLogoUrl(uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        //Adding res pics to storage
        for(Uri uri : imageUri){
            //Store image to storage
            StorageReference filePath = storageReference.child("Restaurants").child(id).child("res_images").child(resName + Timestamp.now().getNanoseconds());
            filePath.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                //Get Image Download Url
                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri1) {
                        resUri.add(uri1.toString());
                        restaurant.setImageUrl(resUri);
                        if(resUri.size() == imageUri.size()){
                            db.collection(Util.USERS_COLLECTION_REF).document(UsersAPI.getInstance().getUserId()).update(Util.USER_HAS_RES_REF,true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });

                            restaurantReference.add(restaurant);
                            RestaurantsAPI.getInstance().setRestaurant(restaurant);
                            outAnimation = new AlphaAnimation(1f, 0f);
                            outAnimation.setDuration(200);
                            binding.daftarResProgressBar.setVisibility(View.GONE);
                            binding.daftarResProgressDescription.setText("Success");
                            binding.daftarResOKButton.setVisibility(View.VISIBLE);
                            binding.daftarResProgressDescription.setTextColor(Color.GREEN);


                        }

                    }


                    //fail getting download URL
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
                //fail put file
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }








    }

    private void addCategoryToRes(String category){

    }
    private String capitalize(String sentence){
        return sentence.substring(0,1).toUpperCase()+sentence.substring(1).toLowerCase();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }
}