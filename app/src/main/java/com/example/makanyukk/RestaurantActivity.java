package com.example.makanyukk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.makanyukk.adapter.RestaurantViewPagerAdapter;
import com.example.makanyukk.databinding.ActivityRestaurantBinding;
import com.example.makanyukk.interfaces.ExploreRestaurantClickListener;
import com.example.makanyukk.model.Restaurant;
import com.example.makanyukk.util.RestaurantsAPI;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.ViewPagerOnTabSelectedListener;
import com.squareup.picasso.Picasso;

public class RestaurantActivity extends AppCompatActivity {
    private ActivityRestaurantBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        binding = DataBindingUtil.setContentView(RestaurantActivity.this,R.layout.activity_restaurant);

        //SET View Model

        //SET RESTAURANT DESCRIPTION
        binding.restaurantDescriptionTV.addShowLessText("Tutup");
        binding.restaurantDescriptionTV.addShowMoreText("Baca");
        binding.restaurantDescriptionTV.setShowingLine(1);
        binding.restaurantDescriptionTV.setShowMoreColor(Color.rgb(248,200,79));
        binding.restaurantDescriptionTV.setShowLessTextColor(Color.rgb(248,200,79));

        //SET TOOLBAR
        Toolbar toolbar = findViewById(binding.restaurantToolbar.getId());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        Restaurant restaurant = RestaurantsAPI.getInstance().getSelectedRestaurant();

        if(restaurant != null){
            binding.restaurantNameTV.setText(restaurant.getName());
            binding.restaurantDescriptionTV.setText(restaurant.getDescription());
            binding.restaurantAddressTV.setText(restaurant.getAddress());

            Uri uri = Uri.parse(restaurant.getLogoUrl());
            Picasso.get().load(uri).placeholder(R.color.light_gray).fit().centerCrop().into(binding.restaurantLogoIV);
        }

        //Tab Layout

        View view1 = getLayoutInflater().inflate(R.layout.tab_icon_custom_view, null);
        view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.hamburger);

        View view2 = getLayoutInflater().inflate(R.layout.tab_icon_custom_view, null);
        view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.icon_commment);
        View view3 = getLayoutInflater().inflate(R.layout.tab_icon_custom_view, null);
        view3.findViewById(R.id.icon).setBackgroundResource(R.drawable.icon_account);



        binding.restaurantTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        RestaurantViewPagerAdapter restaurantViewPagerAdapter = new RestaurantViewPagerAdapter(getSupportFragmentManager());
        restaurantViewPagerAdapter.addFragment(RestaurantMenuListFragment.newInstance(),"Menu");
        restaurantViewPagerAdapter.addFragment(RestaurantCommentFragment.newInstance(),"Comment");
        restaurantViewPagerAdapter.addFragment(RestaurantAccountFragment.newInstance(),"Account");



        binding.restauarntPager.setAdapter(restaurantViewPagerAdapter);
        binding.restaurantTabLayout.setupWithViewPager(binding.restauarntPager);


        binding.restaurantTabLayout.getTabAt(0).setCustomView(view1);
        binding.restaurantTabLayout.getTabAt(1).setCustomView(view2);
        binding.restaurantTabLayout.getTabAt(2).setCustomView(view3);

        binding.restaurantTabLayout.getTabAt(0).getCustomView().findViewById(R.id.icon).setBackgroundTintList(ContextCompat.getColorStateList(RestaurantActivity.this, R.color.golden_bg));

        binding.restaurantTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {

                ImageView IV = tab.getCustomView().findViewById(R.id.icon);
                IV.setBackgroundTintList(ContextCompat.getColorStateList(RestaurantActivity.this, R.color.golden_bg));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                ImageView IV = tab.getCustomView().findViewById(R.id.icon);
                IV.setBackgroundTintList(ContextCompat.getColorStateList(RestaurantActivity.this, R.color.black));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
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



}