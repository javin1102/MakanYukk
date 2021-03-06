package com.example.makanyukk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.makanyukk.adapter.ExploreListViewAdapter;
import com.example.makanyukk.databinding.FragmentExploreBinding;
import com.example.makanyukk.interfaces.ExploreCategoryClickListener;
import com.example.makanyukk.interfaces.ExploreRestaurantClickListener;
import com.example.makanyukk.model.Category;
import com.example.makanyukk.model.Restaurant;
import com.example.makanyukk.util.RestaurantsAPI;
import com.example.makanyukk.util.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ExploreFragment extends Fragment implements ExploreRestaurantClickListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference categoryReference = db.collection(Util.EXPLORE_CATEGORY_COLLECTION_REF);
    private CollectionReference restaurantReference = db.collection(Util.RESTAURANT_COLLECTION_REF);

    private ExploreListViewAdapter exploreListViewAdapter;

    private List<Category> categoryList;
    private FragmentExploreBinding binding;
    private List<Restaurant> restaurantList;
    private final String TAG ="Explore";
    private final int REQEUST_CODE = 10;



    public ExploreFragment() {
        // Required empty public constructor
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    // TODO: Rename and change types and number of parameters
    public static ExploreFragment newInstance() {
        ExploreFragment fragment = new ExploreFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryList = new ArrayList<>();
        restaurantList = new ArrayList<>();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Initial Radio Button State
        binding.RadioGroup.check(R.id.radiobutton1);
        changeColorClicked(binding.radiobutton1);
        //Log.d("1123", "onViewCreated: "+binding.RadioGroup.getChildCount());
        //SETTING RADIO GROUP
        binding.RadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            changeColorDefault(binding.radiobutton1);
            changeColorDefault(binding.radiobutton2);
            changeColorDefault(binding.radiobutton3);
            changeColorDefault(binding.radiobutton4);
            changeColorDefault(binding.radiobutton5);

            //Change Radio Button
            switch (checkedId){

                case R.id.radiobutton1:
                    changeColorClicked(binding.radiobutton1);
                    break;
                case R.id.radiobutton2:
                    changeColorClicked(binding.radiobutton2);

                    break;
                case R.id.radiobutton3:
                    changeColorClicked(binding.radiobutton3);

                    break;
                case R.id.radiobutton4:
                    changeColorClicked(binding.radiobutton4);

                    break;
                case R.id.radiobutton5:
                    changeColorClicked(binding.radiobutton5);
                    break;
            }
        });



        //GET EXPLORE CATEGORY FROM DB FIRESTORE
        categoryReference.get().addOnCompleteListener(task -> {
           task.addOnSuccessListener(queryDocumentSnapshots -> {
               for(QueryDocumentSnapshot snapshot: queryDocumentSnapshots){

                   Category category = new Category();
                   category.setCategoryName(snapshot.get(Util.CATEGORY_NAME).toString());
                   categoryList.add(category);
               }
               for(int i = 0 ; i < binding.RadioGroup.getChildCount() ;i++){
                   RadioButton radioButton = (RadioButton) binding.RadioGroup.getChildAt(i);
                   radioButton.setText(categoryList.get(i).getCategoryName());
               }


           }).addOnFailureListener(e -> {

           });
        });

        restaurantReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                    Restaurant restaurant = queryDocumentSnapshot.toObject(Restaurant.class);
                    restaurantList.add(restaurant);
                    Log.d("1102", "onSuccess: "+restaurant.getName());
                }

                //SETTING EXPLORE LIST
                exploreListViewAdapter = new ExploreListViewAdapter(restaurantList,ExploreFragment.this);
                binding.exploreListRv.setAdapter(exploreListViewAdapter);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.exploreListRv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.exploreListRv.setLayoutManager(linearLayoutManager);
        Log.d(TAG, "onCreateView: "+restaurantList.size());

        return view;
    }

    @SuppressLint({"UseCompatLoadingForColorStateLists", "ResourceType"})
    private void changeColorClicked(RadioButton textView){
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundTintList(getContext().getResources().getColorStateList(R.drawable.explore_category_state));
    }


    @SuppressLint({"UseCompatLoadingForColorStateLists", "ResourceType"})
    private void changeColorDefault(RadioButton textView){
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundTintList(getContext().getResources().getColorStateList(R.drawable.explore_category_state));
    }


    @Override
    public void onRestaurantClicked(Restaurant restaurant) {
        Log.d(TAG, "onRestaurantClicked: "+restaurant.getName());
        Intent intent = new Intent(getActivity(),RestaurantActivity.class);
        RestaurantsAPI.getInstance().setSelectedRestaurant(restaurant);
        getActivity().startActivity(intent);
    }
}