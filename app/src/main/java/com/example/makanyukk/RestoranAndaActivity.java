package com.example.makanyukk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.makanyukk.databinding.ActivityRestoranAndaBinding;

public class RestoranAndaActivity extends AppCompatActivity {
    private ActivityRestoranAndaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_anda);
        binding = DataBindingUtil.setContentView(RestoranAndaActivity.this,R.layout.activity_restoran_anda);
        binding.resAndaLayoutButton.setOnClickListener(v -> {

        });

    }
}