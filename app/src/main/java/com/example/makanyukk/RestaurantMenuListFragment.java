package com.example.makanyukk;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RestaurantMenuListFragment extends Fragment {


    public RestaurantMenuListFragment() {
        // Required empty public constructor
    }

    public static RestaurantMenuListFragment newInstance() {
        RestaurantMenuListFragment fragment = new RestaurantMenuListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_menulist, container, false);
    }


}