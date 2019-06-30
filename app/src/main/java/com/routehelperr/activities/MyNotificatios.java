package com.routehelperr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.routehelperr.R;
import com.routehelperr.adapter.NotificationsAdapter;
import com.routehelperr.model.NotifationsModel;
import com.routehelperr.networking.ApiClient;
import com.routehelperr.networking.ApiInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyNotificatios extends AppCompatActivity {

    @BindView(R.id.notifications_back_txtV_id)
    TextView back_txtV;
    @BindView(R.id.notifications_recyclerV_id)
    RecyclerView recyclerView;
    @BindView(R.id.notification_progress_id)
    ProgressBar progressBar;
    @BindView(R.id.no_data_txtV_id)
    TextView no_data_txtV;

    private NotificationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notificatios);
        ButterKnife.bind(this);

        getNotifications();
    }


    @OnClick(R.id.notifications_back_txtV_id)
    public void goBack() {
        finish();
    }

    private void getNotifications() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<NotifationsModel> call = apiInterface.getUserNotifications(HomeActivity.userModel.getId());
        call.enqueue(new Callback<NotifationsModel>() {
            @Override
            public void onResponse(Call<NotifationsModel> call, Response<NotifationsModel> response) {
                NotifationsModel notifationsModel = response.body();
                if (notifationsModel.getSuccess()) {
                    List<NotifationsModel.messageData> list = response.body().getMessage();
                    recyclerView.setVisibility(View.VISIBLE);
                    no_data_txtV.setVisibility(View.GONE);
                    buildNotifationRecycler(list);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    no_data_txtV.setVisibility(View.VISIBLE);
                    Toast.makeText(MyNotificatios.this, notifationsModel.getData(), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<NotifationsModel> call, Throwable t) {
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void buildNotifationRecycler(List<NotifationsModel.messageData> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Set Adapter ...
        adapter = new NotificationsAdapter(MyNotificatios.this, list);
        recyclerView.setAdapter(adapter);
    }
}
