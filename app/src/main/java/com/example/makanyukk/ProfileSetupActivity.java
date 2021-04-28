package com.example.makanyukk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.makanyukk.R;
import com.example.makanyukk.databinding.ActivityProfileSettingsBinding;
import com.example.makanyukk.model.User;
import com.example.makanyukk.util.UsersAPI;
import com.example.makanyukk.util.Util;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class ProfileSetupActivity extends AppCompatActivity {

    private ActivityProfileSettingsBinding binding;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private UsersAPI userInstance;
    private CollectionReference collectionReference = db.collection("Users");
    private String TAG = "ProfileSetup";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        binding = DataBindingUtil.setContentView(ProfileSetupActivity.this,R.layout.activity_profile_settings);
        firebaseAuth = FirebaseAuth.getInstance();
        userInstance = UsersAPI.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        binding.okButton.setOnClickListener(v -> {
            String username = binding.profileSettingsNamaEt.getText().toString().trim();
            String phoneNumber = binding.profileSettingsHpEt.getText().toString().trim();
            add_Username_Phone(username,phoneNumber);

        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    private void add_Username_Phone(String username,String number){
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(username)){
            UsersAPI.getInstance().setPhoneNumber(number);
            UsersAPI.getInstance().setUsername(username);
            User user = new User();
            UsersAPI.getInstance().setUser(user);
            collectionReference.document(userInstance.getUserID()).set(user, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    startActivity(new Intent(ProfileSetupActivity.this,MainPageActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }
        else {
            //username is empty, display error
        }
    }
}