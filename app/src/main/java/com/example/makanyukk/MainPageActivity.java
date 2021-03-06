package com.example.makanyukk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.makanyukk.model.Category;
import com.example.makanyukk.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MainPageActivity extends AppCompatActivity {
    private FirebaseFirestore db  = FirebaseFirestore.getInstance();
    private List<Category> categories;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String TAG ="page123";
    private int selectedId = 0;

    private CollectionReference collectionReference = db.collection(Util.MAIN_CATEGORY_COLLECTION_REF);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.home_nav_button && selectedId != R.id.home_nav_button){
                selectedId = R.id.home_nav_button;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,MainFragment.newInstance()).commit();
                return true;

            }
            else if(id == R.id.explore_nav_button && selectedId != R.id.explore_nav_button){
                selectedId = R.id.explore_nav_button;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,ExploreFragment.newInstance()).commit();
                return true;
            }

            else if(id == R.id.profile_nav_button && selectedId !=  R.id.profile_nav_button){
                selectedId = R.id.profile_nav_button;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,ProfileFragment.newInstance()).commit();
                return true;
            }


            return false;

        });
    }


}