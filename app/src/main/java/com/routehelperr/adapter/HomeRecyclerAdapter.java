package com.routehelperr.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.routehelperr.R;
import com.routehelperr.model.HomeItemModel;

import java.util.ArrayList;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

    Context mcontext;
    ArrayList<HomeItemModel> list;

    public HomeRecyclerAdapter(Context mcontext, ArrayList<HomeItemModel> list) {
        this.mcontext = mcontext;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.services_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerAdapter.ViewHolder viewHolder, int position) {

        HomeItemModel homeItemModel = list.get(position);
        Glide.with(mcontext)
                .load(homeItemModel.getItem_image())
                .centerCrop()
                .placeholder(R.drawable.logo)
                .into(viewHolder.item_imageV);
        viewHolder.item_txtV.setText(homeItemModel.getItem_title());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_imageV;
        TextView item_txtV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_imageV = itemView.findViewById(R.id.serviceItem_imageV_id);
            item_txtV = itemView.findViewById(R.id.serviceItem_title_txtV_id);
        }
    }
}
