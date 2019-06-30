package com.routehelperr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.routehelperr.R;
import com.routehelperr.adapter.ChattersAdapter;
import com.routehelperr.model.ChattersModel;
import com.routehelperr.networking.ApiClient;
import com.routehelperr.networking.ApiInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chatters extends AppCompatActivity {

    @BindView(R.id.chatters_back_txtV_id)
    TextView back_txtV;
    @BindView(R.id.chatters_recyclerV_id)
    RecyclerView chatters_recyclerV;
    @BindView(R.id.no_chatters_data_txt_id)
    TextView no_data_txtV;
    @BindView(R.id.chatters_progress_id)
    ProgressBar progressBar;

    ChattersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatters);
        ButterKnife.bind(this);

        if (getIntent().hasExtra("user_id")) {
            int user_id = getIntent().getExtras().getInt("user_id");
            getChattersData(user_id);
        }
    }

    private void getChattersData(int user_id) {
        progressBar.setVisibility(View.VISIBLE);
        Log.i("user_id: ", user_id + "");
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ChattersModel> call = apiInterface.getUserChatters(user_id);
        call.enqueue(new Callback<ChattersModel>() {
            @Override
            public void onResponse(Call<ChattersModel> call, Response<ChattersModel> response) {
                ChattersModel chattersModel = response.body();
                if (chattersModel.getSuccess()) {
                    List<ChattersModel.Message> list = chattersModel.getMessage();
                    Log.i("listSs:", list.size() + "");
                    if (list.size() > 0) {
                        no_data_txtV.setVisibility(View.GONE);
                        chatters_recyclerV.setVisibility(View.VISIBLE);
                        chatters_recyclerV.setLayoutManager(new LinearLayoutManager(Chatters.this));
                        chatters_recyclerV.setHasFixedSize(true);

                        adapter = new ChattersAdapter(Chatters.this, list);
                        chatters_recyclerV.setAdapter(adapter);
                    }
                } else {
                    chatters_recyclerV.setVisibility(View.GONE);
                    no_data_txtV.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ChattersModel> call, Throwable t) {
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.chatters_back_txtV_id)
    public void goBack() {
        finish();
    }
}
