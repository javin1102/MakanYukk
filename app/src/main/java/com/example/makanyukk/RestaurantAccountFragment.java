package com.example.makanyukk;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantAccountFragment extends Fragment {



    public RestaurantAccountFragment() {
        // Required empty public constructor
    }


    public static RestaurantAccountFragment newInstance() {
        RestaurantAccountFragment fragment = new RestaurantAccountFragment();

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
        return inflater.inflate(R.layout.fragment_restaurant_account, container, false);
    }
}