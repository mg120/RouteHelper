package com.routehelperr.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.routehelperr.R;
import com.routehelperr.adapter.ChatAdapter;
import com.routehelperr.model.ChatMessageResponseModel;
import com.routehelperr.model.SendMessageResponseModel;
import com.routehelperr.networking.ApiClient;
import com.routehelperr.networking.ApiInterface;
import com.routehelperr.networking.NetworkAvailable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class Chat extends Activity {

    @BindView(R.id.chat_back_txtV_id)
    TextView back_txtV;
    @BindView(R.id.chat_recyclerV_id)
    RecyclerView chat_recycler;
    @BindView(R.id.et_message)
    EditText message_ed;
    @BindView(R.id.img_send)
    ImageView send_btn;
    @BindView(R.id.messages_chat_progress_id)
    ProgressBar progressBar;
    @BindView(R.id.chat_no_messages_txt_id)
    TextView no_data_txtV;
    @BindView(R.id.receiver_name_txtV_id)
    TextView receiver_name_txtV;

    int rooom_Id, receiver_id;
    String receiver_name;

    private List<ChatMessageResponseModel.Message> messages = new ArrayList<>();
    ChatAdapter adapter;
    NetworkAvailable networkAvailable;


    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // get message sent from broadcast
            ChatMessageResponseModel.Message message = intent.getParcelableExtra("msg");

            // check if message is null
            if (message != null) {
                // add message to messages list
                messages.add(message);
                // notify adapter that new message inserted to list in the last position (list size-1)
                adapter.notifyItemInserted(messages.size() - 1);
//                adapter.notifyDataSetChanged();
                // scroll to bottom of recycler show last message
                chat_recycler.scrollToPosition(messages.size() - 1);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        networkAvailable = new NetworkAvailable(this);

        if (getIntent().getExtras() != null) {
            receiver_id = getIntent().getExtras().getInt("receiver_Id");
            receiver_name = getIntent().getExtras().getString("receiver_name");
        }

        receiver_name_txtV.setText(receiver_name);

        // set Layout Manager to RecyclerView
        chat_recycler.setLayoutManager(new LinearLayoutManager(Chat.this));
        chat_recycler.setHasFixedSize(false);

        // create new adapter with these message
        adapter = new ChatAdapter(Chat.this, messages);
        // set adapter for recycler
        chat_recycler.setAdapter(adapter);

        // get messages
        if (networkAvailable.isNetworkAvailable()) {
            getMessages(HomeActivity.userModel.getId(), receiver_id);
        } else {
            Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.img_send)
    public void send_msg() {
        // if msg editText is empty return don't do any thing
        if (message_ed.getText().toString().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.enter_your_message), Toast.LENGTH_SHORT).show();
            return;
        }
        // get msg from edit text
        String msg = message_ed.getText().toString();

        DateFormat df = new SimpleDateFormat("HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

        // add message to messages list
        messages.add(new ChatMessageResponseModel.Message(msg, HomeActivity.userModel.getId() + "", receiver_id + "", date));

        adapter.notifyDataSetChanged();
        // sent message to server
        addMessage(msg, receiver_id + "");
        // notify adapter that there is new message in this position
        adapter.notifyItemInserted(messages.size());
//        adapter.notifyDataSetChanged();
        // scroll to last item in recycler
        chat_recycler.scrollToPosition(messages.size() - 1);
        // set message box empty
        message_ed.setText("");
    }


    @OnClick(R.id.chat_back_txtV_id)
    public void go_back() {
        finish();
    }


    private void addMessage(String msg, String receiver_id) {
        Call<SendMessageResponseModel> call = ApiClient.getClient().create(ApiInterface.class).sendMessage(HomeActivity.userModel.getId(), Integer.parseInt(receiver_id), msg, "en");
        call.enqueue(new Callback<SendMessageResponseModel>() {
            @Override
            public void onResponse(Call<SendMessageResponseModel> call, Response<SendMessageResponseModel> response) {
                SendMessageResponseModel responseModel = response.body();
                boolean success = responseModel.getSuccess();
                if (success) {
                    Toast.makeText(Chat.this, getResources().getString(R.string.send_success), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SendMessageResponseModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void getMessages(int sender_id, int receiver_id) {
        Log.d(TAG, "getMessages: " + sender_id);
        Log.d(TAG, "getMessages: " + receiver_id);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ChatMessageResponseModel> call = apiInterface.getChatMessages(receiver_id, sender_id);
        call.enqueue(new Callback<ChatMessageResponseModel>() {
            @Override
            public void onResponse(Call<ChatMessageResponseModel> call, Response<ChatMessageResponseModel> response) {
                progressBar.setVisibility(View.GONE);
                messages = response.body().getMessage();
                if (messages.size() > 0) {
                    no_data_txtV.setVisibility(View.GONE);
                    chat_recycler.setVisibility(View.VISIBLE);
                    adapter = new ChatAdapter(Chat.this, messages);
                    chat_recycler.setAdapter(adapter);
                    // scroll to bottom of screen
//                    chat_recycler.scrollToPosition(messages.size() - 1);
                } else {
                    chat_recycler.setVisibility(View.GONE);
                    no_data_txtV.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ChatMessageResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });
    }
}
