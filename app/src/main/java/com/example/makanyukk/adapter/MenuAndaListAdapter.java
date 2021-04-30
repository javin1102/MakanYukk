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

public class MenuAndaListAdapter extends RecyclerView.Adapter<MenuAndaListAdapter.ViewHolder> {
    List<Menu> menuList;
    public MenuAndaListAdapter (List<Menu> menuList){
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_anda_menulist_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri uri = Uri.parse(menuList.get(position).getMenuImageURL());
        Picasso.get().load(uri).fit().centerCrop().into(holder.menuIV);
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

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            menuIV = itemView.findViewById(R.id.menulist_imageview);
            menuNameTV = itemView.findViewById(R.id.menulist_menuName_TV);
            menuPriceTV = itemView.findViewById(R.id.menulist_harga_TV);
        }
    }
}
