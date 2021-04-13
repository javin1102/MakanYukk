package com.example.makanyukk.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makanyukk.R;
import com.example.makanyukk.model.Category;
import com.example.makanyukk.model.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ExploreListViewAdapter extends RecyclerView.Adapter<ExploreListViewAdapter.ViewHolder> {
    public List<Restaurant> restaurantList;
    public ExploreListViewAdapter(){}
    public ExploreListViewAdapter(List<Restaurant> restaurantList){
        this.restaurantList = restaurantList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_list_recyclerview,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.resName.setText(restaurantList.get(position).getName());
        Uri uri = Uri.parse(restaurantList.get(position).getLogoUrl());
        Picasso.get().load(uri).fit().centerCrop().into(holder.resLogo);
        holder.resLocation.setText(restaurantList.get(position).getAddress());
        StringBuilder stringBuilder = new StringBuilder();
        int len = restaurantList.get(position).getCategories().size();
        for(int i = 0 ; i < len ;i++){
            Category category = restaurantList.get(position).getCategories().get(i);
            stringBuilder.append(category.getCategoryName());
            if(i != len-1){
                stringBuilder.append(", ");
            }
        }
        holder.resCategory.setText(stringBuilder);

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView resLogo;
        public TextView resName,resCategory,resLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            resLogo = itemView.findViewById(R.id.res_logo);
            resName = itemView.findViewById(R.id.res_name);
            resCategory = itemView.findViewById(R.id.res_category);
            resLocation = itemView.findViewById(R.id.res_location);
        }
    }
}
