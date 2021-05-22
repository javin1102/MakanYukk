package com.example.makanyukk.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makanyukk.R;
import com.example.makanyukk.model.MenuCategory;

import java.util.List;

public class RestaurantMenuViewAdapter extends RecyclerView.Adapter<RestaurantMenuViewAdapter.ViewHolder> {
    private List<MenuCategory> menuCategoryList;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private Context context;
    public RestaurantMenuViewAdapter(List<MenuCategory> menuCategoryList,Context context){
        this.menuCategoryList = menuCategoryList;
        this.context = context;
        Log.d("BEKA", "RestaurantMenuViewAdapter: "+this.menuCategoryList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_menu_category_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.menuCategoryTV.setText(menuCategoryList.get(position).getCategory().getCategoryName());
        GridLayoutManager layoutManager = new GridLayoutManager(context,3);
        layoutManager.setInitialPrefetchItemCount(menuCategoryList.get(position).getMenuList().size());
        RestaurantMenuListAdapter adapter = new RestaurantMenuListAdapter(menuCategoryList.get(position).getMenuList());

        holder.menuRV.setLayoutManager(layoutManager);
        holder.menuRV.setAdapter(adapter);
        holder.menuRV.setHasFixedSize(true);

        holder.menuRV.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return menuCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView menuCategoryTV;
        public RecyclerView menuRV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuCategoryTV = itemView.findViewById(R.id.restaurant_menu_category_TV);
            menuRV = itemView.findViewById(R.id.restaurant_menu_RV);
            menuRV.setNestedScrollingEnabled(false);
        }
    }
}
