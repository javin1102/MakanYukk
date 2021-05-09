package com.example.makanyukk;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantCommentFragment extends Fragment {



    public RestaurantCommentFragment() {
        // Required empty public constructor
    }


    public static RestaurantCommentFragment newInstance() {
        RestaurantCommentFragment fragment = new RestaurantCommentFragment();

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
        return inflater.inflate(R.layout.fragment_restaurant_comment, container, false);
    }
}