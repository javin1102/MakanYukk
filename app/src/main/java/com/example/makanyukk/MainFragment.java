package com.example.makanyukk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.makanyukk.adapter.CategoryViewAdapter;
import com.example.makanyukk.adapter.ExtraPoinCardAdapter;
import com.example.makanyukk.adapter.SliderViewPagerAdapter;
import com.example.makanyukk.model.Category;
import com.example.makanyukk.model.Image;
import com.example.makanyukk.util.Util;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private RecyclerView categoryRecyclerView;
    private RecyclerView extraPoinRecyclerView;
    private SliderViewPagerAdapter sliderViewPagerAdapter;
    private ExtraPoinCardAdapter extraPoinCardAdapter;


    private  List<Category> categories;
    private List<Image> sliderImages;

    private CategoryViewAdapter categoryViewAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection(Util.MAIN_CATEGORY_COLLECTION_REF);

    private StorageReference storageReference;
    private String TAG ="Fragment";

    private ViewPager2 slider;

    public MainFragment() {
        // Required empty public constructor
    }



    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories = new ArrayList<>();
        storageReference = FirebaseStorage.getInstance().getReference();
        sliderImages = new ArrayList<>();



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        slider = view.findViewById(R.id.slider);


        //Slider
        //GET DOWNLOAD URL AND DOWNLOAD WITH PICASSO
        storageReference.child("slider_images").listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {


                for(StorageReference ref : listResult.getItems()){

                    ref.getDownloadUrl().addOnSuccessListener(uri -> {

                        Image image = new Image();
                        image.setUrl(uri.toString());
                        sliderImages.add(image);
                        sliderViewPagerAdapter = new SliderViewPagerAdapter(sliderImages);
                        slider.setAdapter(sliderViewPagerAdapter);



                    }).addOnFailureListener(e -> {

                    });

                }
            }
            });

        //GET List of CATEGORY BALL NAME FROM COLLECTION REFERENCE
        collectionReference.get().addOnCompleteListener(task -> {
            //Display

            task.addOnSuccessListener(queryDocumentSnapshots -> {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                    Category category = new Category();
                    category.setCategoryName(snapshot.getString(Util.CATEGORY_NAME));
                    categories.add(category);
                    //Log.d(TAG, "onSuccess: "+snapshot.getString(Util.CATEGORY_NAME));
                }

                categoryViewAdapter = new CategoryViewAdapter(categories,getContext());
                categoryRecyclerView.setAdapter(categoryViewAdapter);

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        });

        extraPoinCardAdapter = new ExtraPoinCardAdapter();
        extraPoinRecyclerView.setAdapter(extraPoinCardAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        categoryRecyclerView = view.findViewById(R.id.category_rv);
        categoryRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);

        extraPoinRecyclerView = view.findViewById(R.id.extra_poin_rv);
        extraPoinRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        extraPoinRecyclerView.setLayoutManager(manager);

        // Inflate the layout for this fragment
        return view;
    }


}