package com.example.makanyukk.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makanyukk.R;
import com.example.makanyukk.model.Menu;
import com.example.makanyukk.model.MenuCategory;

import java.util.List;

public class MenuAndaViewAdapter extends RecyclerView.Adapter<MenuAndaViewAdapter.ViewHolder> {
    private List<MenuCategory> menuCategoryList;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public MenuAndaViewAdapter(List<MenuCategory> menuCategoryList){
        this.menuCategoryList = menuCategoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_anda_layout_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.categoryTV.setText(menuCategoryList.get(position).getCategory().getCategoryName());
        LinearLayoutManager layoutManager =  new LinearLayoutManager(holder.menuListRV.getContext(),LinearLayoutManager.VERTICAL,false);
        layoutManager.setInitialPrefetchItemCount(menuCategoryList.get(position).getMenuList().size());
        MenuAndaListAdapter menuAndaListAdapter = new MenuAndaListAdapter(menuCategoryList.get(position).getMenuList());
        holder.menuListRV.setLayoutManager(layoutManager);
        holder.menuListRV.setAdapter(menuAndaListAdapter);

        holder.menuListRV.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return menuCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryTV;
        private RecyclerView menuListRV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTV = itemView.findViewById(R.id.menuAnda_category);
            menuListRV = itemView.findViewById(R.id.menuAnda_List_RV);
        }
    }




}
