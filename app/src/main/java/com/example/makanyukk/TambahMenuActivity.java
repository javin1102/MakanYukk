package com.example.makanyukk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.makanyukk.databinding.ActivityTambahMenuBinding;
import com.example.makanyukk.util.RestaurantsAPI;
import com.example.makanyukk.util.UsersAPI;
import com.example.makanyukk.util.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TambahMenuActivity extends AppCompatActivity {
    private ActivityTambahMenuBinding binding;
    private final int MENU_IMAGE_CODE = 4;
    private int idx;
    private List<Uri> uriList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference restaurantReference = db.collection(Util.RESTAURANT_COLLECTION_REF);
    private CollectionReference menuReference = restaurantReference.document(RestaurantsAPI.getInstance().getRestaurant().getId())
            .collection(Util.MENU_COLLECTION_REFERENCE);
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_menu);
        binding = DataBindingUtil.setContentView(TambahMenuActivity.this,R.layout.activity_tambah_menu);
        uriList = new ArrayList<>();
        storageReference = FirebaseStorage.getInstance().getReference();

        Toolbar toolbar = findViewById(binding.tambahMenuToolbar.getId());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        binding.tambahMenuTAMBAHMENUTV.setOnClickListener(v->{
            LayoutInflater inf = LayoutInflater.from(getApplicationContext());
            View child = inf.inflate(R.layout.tambah_menu_recyclerview,null);
            binding.tambahMenuContent.addView(child);

            int lastChild = binding.tambahMenuContent.getChildCount() - 1;
            uriList.add(null);
            Log.d("TAMBAH", "onCreate: "+uriList.size());
            //IV
            ImageView IV = binding.tambahMenuContent.getChildAt(lastChild).findViewById(R.id.tambah_menu_IV);
            IV.setTag(lastChild);

            //GALLERY INTENT
            IV.setOnClickListener(v1 -> {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                idx = (int) IV.getTag();
                startActivityForResult(galleryIntent, MENU_IMAGE_CODE);
            });

            //HAPUS
            TextView hapusTV = binding.tambahMenuContent.getChildAt(lastChild).findViewById(R.id.tambah_menu_hapus_TV);
            hapusTV.setTag(lastChild);
            hapusTV.setOnClickListener(v1 -> {
                //REMOVE VIEW
                binding.tambahMenuContent.removeViewAt((int)hapusTV.getTag());
                uriList.remove((int)hapusTV.getTag());

                //RESET IV AND HAPUS TAG
                for(int i = 0 ; i < binding.tambahMenuContent.getChildCount() ; i++){
                    binding.tambahMenuContent.getChildAt(i).findViewById(R.id.tambah_menu_IV).setTag(i);
                    binding.tambahMenuContent.getChildAt(i).findViewById(R.id.tambah_menu_hapus_TV).setTag(i);

                }
            });

        });

        binding.tambahMenuOK.setOnClickListener(v -> {
            binding.tambahMenuLayout.setClickable(true);
            binding.tambahMenuCardView.setVisibility(View.GONE);
            setResult(RESULT_OK,getIntent());
            finish();
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MENU_IMAGE_CODE && resultCode == RESULT_OK){
            if(data != null){
                ImageView IV = binding.tambahMenuContent.getChildAt(idx).findViewById(R.id.tambah_menu_IV);
                IV.setImageURI(data.getData());
                uriList.set(idx,data.getData());
                Log.d("TAMBAH", "onActivityResult: "+uriList.size());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tambah_menu_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        else if (id == R.id.tambah_menu_selesai){
            insertToFirestoreDB();
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }

    private void insertToFirestoreDB(){
        binding.tambahMenuLayout.setForeground(new ColorDrawable(ContextCompat.getColor(this, R.color.light_gray)));
        binding.tambahMenuCardView.setVisibility(View.VISIBLE);
        binding.tambahMenuLayout.setClickable(false);
        binding.tambahMenuProgressBar.setVisibility(View.VISIBLE);
       int len = binding.tambahMenuContent.getChildCount();
       if(len == 0){
           binding.tambahMenuLayout.setForeground(null);
           binding.tambahMenuCardView.setVisibility(View.GONE);
           binding.tambahMenuLayout.setClickable(true);
           return;
       }

       for(int i = 0 ; i < len ; i++){
           if(uriList.get(i) == null){
               binding.tambahMenuLayout.setForeground(null);
               binding.tambahMenuCardView.setVisibility(View.GONE);
               binding.tambahMenuLayout.setClickable(true);
               Log.d("TAMBAH", "insertToFirestoreDB: ");
               return;
           }
           Uri uri = uriList.get(i);
           EditText namaMenuET = binding.tambahMenuContent.getChildAt(i).findViewById(R.id.tambah_menu_namaMenu_ET);
           EditText hargaET = binding.tambahMenuContent.getChildAt(i).findViewById(R.id.tambah_menu_harga_ET);
           EditText kategori = binding.tambahMenuContent.getChildAt(i).findViewById(R.id.tambah_menu_kategori_ET);

           RadioGroup rekomendasiRG = binding.tambahMenuContent.getChildAt(i).findViewById(R.id.tambah_menu_rekomendasi_RG);
           int selectedRekomendasi = rekomendasiRG.getCheckedRadioButtonId();
           RadioButton rekomendasiRB = binding.tambahMenuContent.getChildAt(i).findViewById(selectedRekomendasi);

           RadioGroup ketersediaanRG = binding.tambahMenuContent.getChildAt(i).findViewById(R.id.tambah_menu_ketersediaan_RG);
           int selectedKetersediaan = ketersediaanRG.getCheckedRadioButtonId();
           RadioButton ketersediaanRB = binding.tambahMenuContent.getChildAt(i).findViewById(selectedKetersediaan);

           if(!isValid(namaMenuET.getText().toString().trim(),
                   hargaET.getText().toString().trim(),kategori.getText().toString().trim(),
                   rekomendasiRG,ketersediaanRG,uri.toString())){
               binding.tambahMenuLayout.setForeground(null);
               binding.tambahMenuCardView.setVisibility(View.GONE);
               binding.tambahMenuLayout.setClickable(true);
               return;
           }

       }

       for(int i = 0 ; i < len ; i++){
           Uri uri = uriList.get(i);
           EditText namaMenuET = binding.tambahMenuContent.getChildAt(i).findViewById(R.id.tambah_menu_namaMenu_ET);
           EditText hargaET = binding.tambahMenuContent.getChildAt(i).findViewById(R.id.tambah_menu_harga_ET);
           EditText kategori = binding.tambahMenuContent.getChildAt(i).findViewById(R.id.tambah_menu_kategori_ET);

           RadioGroup rekomendasiRG = binding.tambahMenuContent.getChildAt(i).findViewById(R.id.tambah_menu_rekomendasi_RG);
           int selectedRekomendasi = rekomendasiRG.getCheckedRadioButtonId();
           RadioButton rekomendasiRB = binding.tambahMenuContent.getChildAt(i).findViewById(selectedRekomendasi);

           RadioGroup ketersediaanRG = binding.tambahMenuContent.getChildAt(i).findViewById(R.id.tambah_menu_ketersediaan_RG);
           int selectedKetersediaan = ketersediaanRG.getCheckedRadioButtonId();
           RadioButton ketersediaanRB = binding.tambahMenuContent.getChildAt(i).findViewById(selectedKetersediaan);
           Boolean rekomendasi = false;
           if(rekomendasiRB.getText().equals("Ya")){
               rekomendasi = true;
           }

           Boolean ketersediaan = false;
           if(ketersediaanRB.getText().equals("Ya")) {
               ketersediaan = true;
           }

           com.example.makanyukk.model.Menu menu = new com.example.makanyukk.model.Menu();
           menu.setMenuName(namaMenuET.getText().toString().trim());
           menu.setMenuPrice(Integer.parseInt(hargaET.getText().toString()));
           menu.setMenuCategory(kategori.getText().toString().trim());
           menu.setRecommendedMenu(rekomendasi);
           menu.setAvailable(ketersediaan);

           StorageReference filePath = storageReference.child(Util.RESTAURANT_COLLECTION_REF)
                   .child(RestaurantsAPI.getInstance().getRestaurant().getId())
                   .child(Util.MENU_STORAGE_REFERENCE).child(menu.getMenuName()+ Timestamp.now().getNanoseconds());

           filePath.putFile(uri).addOnSuccessListener(taskSnapshot -> {
               filePath.getDownloadUrl().addOnSuccessListener(uri1 -> {
                   menu.setMenuImageURL(uri1.toString());
                   menuReference.add(menu);
                   binding.tambahMenuLoading.setVisibility(View.GONE);
                   binding.tambahMenuSukses.setVisibility(View.VISIBLE);
                   binding.tambahMenuOK.setVisibility(View.VISIBLE);
                   binding.tambahMenuCheck.setVisibility(View.VISIBLE);
                   binding.tambahMenuProgressBar.setVisibility(View.GONE);
               });
           });
       }
    }

    private boolean isValid(String namaMenu,String harga, String kategori,RadioGroup radioGroup1,RadioGroup radioGroup2, String uri){
        if(!TextUtils.isEmpty(namaMenu) && !TextUtils.isEmpty(harga) && !TextUtils.isEmpty(kategori)
                && radioGroup1.getCheckedRadioButtonId() != -1 && radioGroup2.getCheckedRadioButtonId() != -1
        && !TextUtils.isEmpty(uri))
            return true;
        return false;
    }


}