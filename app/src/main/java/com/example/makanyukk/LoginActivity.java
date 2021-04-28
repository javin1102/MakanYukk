package com.example.makanyukk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.makanyukk.databinding.ActivityLoginBinding;
import com.example.makanyukk.model.Restaurant;
import com.example.makanyukk.util.RestaurantsAPI;
import com.example.makanyukk.util.UsersAPI;
import com.example.makanyukk.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private CollectionReference collectionReference =db.collection(Util.USERS_COLLECTION_REF);
    private CollectionReference restaurantReference = db.collection(Util.USER_RESTAURANT_COLLECTION_REF);
    private FirebaseUser user;
    private final String TAG = "ASDF";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                Log.d(TAG, "onAuthStateChanged: "+user.getUid());
            }
        };

        binding.loginBelumPunyaAkunButton.setOnClickListener(this);
        binding.loginMasukButton.setOnClickListener(this);




    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_belumPunyaAkun_button:
                Intent intent = new Intent(LoginActivity.this,CreateAccountActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.login_masuk_button:
                if(!TextUtils.isEmpty(binding.loginEmailEt.getText().toString()) && !TextUtils.isEmpty(binding.loginPasswordEt.getText().toString())){
                    String email = binding.loginEmailEt.getText().toString().trim();
                    String password = binding.loginPasswordEt.getText().toString().trim();
                    loginAcc(email,password);
                }
                break;
        }
    }

    private void loginAcc(String email,String password){
        binding.loginLoadingBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(authResult -> {
            user = authResult.getUser();
            String userId = user.getUid();
            UsersAPI.getInstance().setUserId(userId);


            collectionReference.whereEqualTo("userId",userId).addSnapshotListener((value, error) -> {
                binding.loginLoadingBar.setVisibility(View.INVISIBLE);
                for (QueryDocumentSnapshot snapshot : value){

                    String username = snapshot.getString(Util.USER_NAME_REF);
                    String phoneNumber = snapshot.getString(Util.USER_PHONE_NUMBER_REF);
                    boolean hasRes = snapshot.getBoolean(Util.USER_HAS_RES_REF);

                    if(!username.isEmpty() && !phoneNumber.isEmpty()){
                        UsersAPI.getInstance().setUsername(username);
                        UsersAPI.getInstance().setPhoneNumber(phoneNumber);
                        UsersAPI.getInstance().setHasRes(hasRes);
                        if(hasRes){
                            Log.d(TAG, "onEvent: "+hasRes);
                            restaurantReference.whereEqualTo(Util.RESTAURANT_USER_ID,userId).get().addOnSuccessListener(queryDocumentSnapshots -> {
                                Restaurant restaurant = (Restaurant) queryDocumentSnapshots.toObjects(Restaurant.class).get(0);
                                RestaurantsAPI.getInstance().setRestaurant(restaurant);
                            }).addOnFailureListener(e -> {});
                        }
                        startActivity(new Intent(LoginActivity.this, MainPageActivity.class));
                        finish();
                    }
                    else{
                        startActivity(new Intent(LoginActivity.this, ProfileSetupActivity.class));
                        finish();
                    }


                }



            });
        }).addOnFailureListener(e -> {
            binding.loginLoadingBar.setVisibility(View.INVISIBLE);
            // e instance of FirebaseAuthEmailException
            Log.d(TAG, "onFailure: "+e.getLocalizedMessage());
            //if(firebaseAuth.applyActionCode())

        });
    }
}