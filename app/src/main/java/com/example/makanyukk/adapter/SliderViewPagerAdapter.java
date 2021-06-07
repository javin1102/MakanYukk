package com.example.makanyukk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makanyukk.R;
import com.example.makanyukk.model.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SliderViewPagerAdapter extends RecyclerView.Adapter<SliderViewPagerAdapter.ImageSlider> {
    private List<Image> imageList;

    public SliderViewPagerAdapter(){}
    public SliderViewPagerAdapter( List<Image> imageList) {
        this.imageList = imageList;

    }

    @NonNull
    @Override
    public ImageSlider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_card_view,parent,false);
        return new ImageSlider(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSlider holder, int position) {
        Picasso.get().load(imageList.get(position).getUrl()).fit().placeholder(R.color.gray).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }


    public class ImageSlider extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageSlider(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sliderView);
        }
    }
}
