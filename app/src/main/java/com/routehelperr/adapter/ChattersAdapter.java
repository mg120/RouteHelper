package com.routehelperr.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.routehelperr.R;
import com.routehelperr.activities.Chat;
import com.routehelperr.model.ChattersModel;

import java.util.List;

/**
 * Created by Ma7MouD on 11/18/2018.
 */

public class ChattersAdapter extends RecyclerView.Adapter<ChattersAdapter.ViewHolder> {

    Context context;
    List<ChattersModel.Message> list;

    public ChattersAdapter(Context context, List<ChattersModel.Message> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ChattersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chatters_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChattersAdapter.ViewHolder holder, int position) {
        holder.userName_txt.setText(list.get(position).getName());
        holder.lastMsg_txt.setText(list.get(position).getLastMessage());
        holder.constraintLayout.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userName_txt, lastMsg_txt;
        ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            userName_txt = itemView.findViewById(R.id.title_txtV);
            lastMsg_txt = itemView.findViewById(R.id.desc_txtV);
            constraintLayout = itemView.findViewById(R.id.chatter_item_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            String receiver_Id = list.get(pos).getId();
            Intent intent = new Intent(context, Chat.class);
            Log.i("id: ", receiver_Id);
            intent.putExtra("receiver_Id", Integer.parseInt(receiver_Id));
            intent.putExtra("receiver_name", list.get(pos).getName());
            context.startActivity(intent);
        }
    }
}