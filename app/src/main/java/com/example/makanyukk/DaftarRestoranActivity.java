package com.example.makanyukk;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.example.makanyukk.databinding.ActivityDaftarRestoranBinding;

public class DaftarRestoranActivity extends AppCompatActivity  {

    private TextView mTextView;
    private ActionBar actionBar;
    private ActivityDaftarRestoranBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_restoran);

        Toolbar toolbar = findViewById(R.id.daftar_res_toolbar);
        setSupportActionBar(toolbar);
        binding = DataBindingUtil.setContentView(DaftarRestoranActivity.this,R.layout.activity_daftar_restoran);


        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        

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
}