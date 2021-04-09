package com.example.makanyukk;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import com.example.makanyukk.databinding.ActivityDaftarRestoranBinding;
import com.example.makanyukk.model.Category;

import java.util.ArrayList;
import java.util.List;

public class DaftarRestoranActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityDaftarRestoranBinding binding;
    private static final int GALLERY_CODE = 1;
    private List<Uri> imageUri;
    private Uri[]uris;
    private List<Category> categoryList;

    private Category category1,category2,category3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_restoran);
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


        binding.daftarResTambahTV.setOnClickListener(v -> {
            if(imageUri.size() < 3){
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                galleryIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(galleryIntent,"Select Picture"), GALLERY_CODE);
            }

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
        if(requestCode == GALLERY_CODE && resultCode == RESULT_OK){
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

    private boolean checkEditText(){
        if(!imageUri.isEmpty() && !TextUtils.isEmpty(binding.daftarResNamaResET.getText().toString().trim()) && !TextUtils.isEmpty(binding.daftarResEmailET.getText().toString().trim()) && !categoryList.isEmpty()
        &&!TextUtils.isEmpty(binding.daftarResAlamatET.getText().toString().trim()) && !TextUtils.isEmpty(binding.daftarResKotaET.getText().toString().trim())
                && !TextUtils.isEmpty(binding.daftarResKodeposET.getText().toString().trim()) && !TextUtils.isEmpty(binding.daftarResLatitudeET.getText().toString().trim())
                && !TextUtils.isEmpty(binding.daftarResLongitudeET.getText().toString().trim()))
        {

        }
        return true;
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

}