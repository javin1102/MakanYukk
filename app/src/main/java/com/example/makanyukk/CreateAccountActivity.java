package com.example.makanyukk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.makanyukk.databinding.ActivityCreateAccountBinding;
import com.example.makanyukk.model.User;
import com.example.makanyukk.util.UsersAPI;
import com.example.makanyukk.util.Util;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private CollectionReference collectionReference =db.collection(Util.USERS_COLLECTION_REF);
    private FirebaseUser user;
    private static final String TAG = "qwerty";
    private ActivityCreateAccountBinding binding;
    //asdasd


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null)
                Log.d(TAG, "onAuthStateChanged: " + firebaseAuth.getCurrentUser());
                else
                    Log.d(TAG, "onAuthStateChanged: null ");
            }
        };

        binding = DataBindingUtil.setContentView(CreateAccountActivity.this,R.layout.activity_create_account);
        binding.lanjutButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(binding.createAccEmailEt.getText().toString())
                    && !TextUtils.isEmpty(binding.createAccPasswordEt.getText().toString())
                    && !TextUtils.isEmpty(binding.createAccConfirmEt.getText().toString()))
            {
                if(!binding.createAccPasswordEt.getText().toString().equals(binding.createAccConfirmEt.getText().toString())){
                    //Display error, password not match
                }
                else{
                    String email = binding.createAccEmailEt.getText().toString().trim();
                    String password = binding.createAccPasswordEt.getText().toString().trim();
                    createAccount(email,password);

                }

            }
            else{
                //Display error, some field is missing

            }
        });

        binding.createAccPunyaAkunText.setOnClickListener(v -> {
            startActivity(new Intent(CreateAccountActivity.this,LoginActivity.class));
            finish();
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
        user = firebaseAuth.getCurrentUser();
        if(user != null)
        Log.d(TAG, "onStart: "+user.getDisplayName());
    }

    private void createAccount(String email, String password){
        binding.loadingBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if(authResult != null){
                    user = authResult.getUser();
                    String userId = user.getUid() ;

                    User user = new User();
                    user.setUserID(userId);
                    user.setEmail(email);
                    user.setHasRes(false);


                    collectionReference.document(userId).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                           UsersAPI.getInstance().setUserID(userId);
                            UsersAPI.getInstance().setEmail(email);
                            UsersAPI.getInstance().setHasRes(false);
                            binding.loadingBar.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(CreateAccountActivity.this, ProfileSetupActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: "+e);
                        }
                    });

//                    collectionReference.add(userObj).addOnSuccessListener(documentReference -> {
//
//                        documentReference.get().addOnCompleteListener(task -> {
//
//
//                        });
//                    }).addOnFailureListener(e -> {
//                        binding.loadingBar.setVisibility(View.INVISIBLE);
//                        Log.d(TAG, "add to database Failure: " + e);
//                    });

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.loadingBar.setVisibility(View.INVISIBLE);
                Log.d(TAG, "authentication error: " + e);
            }
        });
    }
}
