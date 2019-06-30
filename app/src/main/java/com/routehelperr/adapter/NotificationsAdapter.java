package com.routehelperr.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.routehelperr.R;
import com.routehelperr.model.NotifationsModel;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private Context mContext;
    private List<NotifationsModel.messageData> list;

    public NotificationsAdapter(Context mContext, List<NotifationsModel.messageData> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.notification_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.ViewHolder viewHolder, int position) {
        viewHolder.notification_txtV.setText(list.get(position).getNotification());
        viewHolder.date_txtV.setText(list.get(position).getCreated_at());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView notification_txtV, date_txtV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notification_txtV = itemView.findViewById(R.id.notification_txtV_id);
            date_txtV = itemView.findViewById(R.id.dat_txtV_id);
        }
    }
}
