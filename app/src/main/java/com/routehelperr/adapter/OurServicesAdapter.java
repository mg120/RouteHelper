package com.routehelperr.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.routehelperr.R;
import com.routehelperr.interfaces.RecyclerOnItemClickListner;
import com.routehelperr.model.ServicesModel;
import com.routehelperr.networking.Urls;

import java.util.List;

public class OurServicesAdapter extends RecyclerView.Adapter<OurServicesAdapter.ViewHolder> {

    private Context mcontext;
    private List<ServicesModel.ServiceDataModel> list;
    private RecyclerOnItemClickListner itemClickListner;

    public OurServicesAdapter(Context mcontext, List<ServicesModel.ServiceDataModel> list) {
        this.mcontext = mcontext;
        this.list = list;
    }

    public void setOnItemClickListener(RecyclerOnItemClickListner listener) {
        itemClickListner = listener;
    }

    @NonNull
    @Override
    public OurServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.services_row_item, viewGroup, false);
        return new ViewHolder(view, itemClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull OurServicesAdapter.ViewHolder viewHolder, int position) {
        viewHolder.service_txtV.setText(list.get(position).getTitle());
        Glide.with(mcontext)
                .load(Urls.imagesBase_Url + list.get(position).getImage())
                .placeholder(R.drawable.logo)
                .centerCrop()
                .into(viewHolder.service_imagev);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView service_imagev;
        private TextView service_txtV;
        Typeface custom_font = Typeface.createFromAsset(mcontext.getAssets(), "fonts/DIN Next LT Arabic Medium.ttf");

        public ViewHolder(@NonNull View itemView, final RecyclerOnItemClickListner listner) {
            super(itemView);
            service_imagev = itemView.findViewById(R.id.serviceItem_imageV_id);
            service_txtV = itemView.findViewById(R.id.serviceItem_title_txtV_id);
            service_txtV.setTypeface(custom_font);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listner.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
