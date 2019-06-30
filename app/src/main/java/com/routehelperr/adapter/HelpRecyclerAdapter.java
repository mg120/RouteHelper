package com.routehelperr.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.routehelperr.R;
import com.routehelperr.model.CallServicesDataModel;

import java.util.List;

public class HelpRecyclerAdapter extends RecyclerView.Adapter<HelpRecyclerAdapter.ViewHolder> {

    private Context mcontext;
    private List<CallServicesDataModel.CallServices> list;

    public HelpRecyclerAdapter(Context mcontext, List<CallServicesDataModel.CallServices> list) {
        this.mcontext = mcontext;
        this.list = list;
    }

    @NonNull
    @Override
    public HelpRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.help_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpRecyclerAdapter.ViewHolder viewHolder, int position) {

        CallServicesDataModel.CallServices callServices_item = list.get(position);
//        viewHolder.center_imageV.setImageResource(callServices_item.getCenter_image());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewHolder.center_name.setText(Html.fromHtml(callServices_item.getCountry(), Html.FROM_HTML_MODE_COMPACT));
            viewHolder.center_country.setText(Html.fromHtml(callServices_item.getTitle(), Html.FROM_HTML_MODE_COMPACT));
            viewHolder.center_number.setText(Html.fromHtml(callServices_item.getPhonenumbers(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            viewHolder.center_name.setText(Html.fromHtml(callServices_item.getCountry()));
            viewHolder.center_country.setText(Html.fromHtml(callServices_item.getTitle()));
            viewHolder.center_number.setText(Html.fromHtml(callServices_item.getPhonenumbers()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView center_imageV, phone_icon_imageV;
        TextView center_name, center_number, center_country;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            center_imageV = itemView.findViewById(R.id.call_center_image_id);
            center_name = itemView.findViewById(R.id.call_canter_name_txtV_id);
            center_number = itemView.findViewById(R.id.call_center_num_txtV_id);
            center_country = itemView.findViewById(R.id.help_item_title_txtV_id);
            phone_icon_imageV = itemView.findViewById(R.id.phone_icon_imageV);

            phone_icon_imageV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callPhoneNumber(list.get(getAdapterPosition()).getPhonenumbers());
                }
            });
        }
    }

    private void callPhoneNumber(String phonenumbers) {
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(mcontext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions((Activity) mcontext, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phonenumbers));
                mcontext.startActivity(callIntent);

            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phonenumbers));
                mcontext.startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
