package com.devanshi.tambola.coinpicker.adapters;

import android.content.*;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.recyclerview.widget.*;

import com.devanshi.tambola.coinpicker.*;
import com.devanshi.tambola.coinpicker.interfaces.*;
import com.devanshi.tambola.coinpicker.models.Menu;

import java.util.*;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{

    private ArrayList<Menu> menuArrayList;
    private ItemClickListener itemClickListener;
    private Context context;

    public MenuAdapter(ArrayList<Menu> menuArrayList, ItemClickListener itemClickListener, Context context) {

        this.menuArrayList = menuArrayList;
        this.itemClickListener = itemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          Menu menu=menuArrayList.get(position);
          holder.txtMenuName.setText(menu.getMenu_name());
          holder.imgMenu.setImageResource(menu.getIcon_id());
          String active_icon_path = menu.getIcon_path().concat("_active");
          String inactive_icon_path = menu.getIcon_path().concat("_inactive");
          int activeResource = context.getResources().getIdentifier(active_icon_path,"drawable",context.getPackageName());
          int inactiveResource = context.getResources().getIdentifier(inactive_icon_path,"drawable",context.getPackageName());

          holder.imgMenu.setImageResource(inactiveResource);
          holder.layMainItem.setOnClickListener(v -> itemClickListener.onItemClick(menu.getMenu_name()));

          if (menu.isSelected()){
              holder.imgMenu.setImageResource(activeResource);
              holder.imgMenu.setElevation(6.0f);
          }else {
              holder.imgMenu.setElevation(0.0f);
              holder.imgMenu.setImageResource(inactiveResource);
          }


    }

    @Override
    public int getItemCount() {
        return menuArrayList.size();
    }

    public void refreshList(ArrayList<Menu> menuArrayList) {

        this.menuArrayList = menuArrayList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMenuName;
        private ImageView imgMenu;
        private LinearLayout layMainItem;

        ViewHolder(View itemView) {
            super(itemView);
            this.txtMenuName = itemView.findViewById(R.id.txtMenuName);
            this.imgMenu = itemView.findViewById(R.id.imgMenu);
            this.layMainItem = itemView.findViewById(R.id.layMainItem);
        }
    }

}
