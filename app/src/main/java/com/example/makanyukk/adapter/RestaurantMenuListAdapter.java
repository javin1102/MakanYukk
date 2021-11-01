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
import com.example.makanyukk.model.Menu;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantMenuListAdapter extends RecyclerView.Adapter<RestaurantMenuListAdapter.ViewHolder> {
    List<Menu> menuList;

    public RestaurantMenuListAdapter(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_menu_list_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri uri = Uri.parse(menuList.get(position).getMenuImageURL());
        Picasso.get().load(uri).placeholder(R.color.light_gray).centerCrop().fit().into(holder.menuIV);
        holder.menuNameTV.setText(menuList.get(position).getMenuName());
        holder.menuPriceTV.setText(String.valueOf(menuList.get(position).getMenuPrice()));
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView menuIV;
        public TextView menuNameTV;
        public TextView menuPriceTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuIV = itemView.findViewById(R.id.restaurant_menu_IV);
            menuNameTV = itemView.findViewById(R.id.restaurant_menu_name_TV);
            menuPriceTV = itemView.findViewById(R.id.restaurant_menu_price_TV);
        }
    }
}
