package com.routehelperr.activities;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.routehelperr.R;
import com.routehelperr.adapter.SelectBaqqaAdapter;
import com.routehelperr.model.PackagesModel;
import com.routehelperr.networking.ApiClient;
import com.routehelperr.networking.ApiInterface;
import com.routehelperr.networking.NetworkAvailable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectBaqqa extends AppCompatActivity {

    @BindView(R.id.baqqa_back_txtV_id)
    TextView back_txtV;
    @BindView(R.id.baqqa_tabLayout_id)
    TabLayout tabLayout;
    TabItem goldBaqqa_tab;
    TabItem silverBaqqa_tab;
    @BindView(R.id.baqqa_viewPager_id)
    ViewPager baqqa_ViewPager;

    SelectBaqqaAdapter baqqaAdapter;
    String gold_Baqqa_desc;
    String silver_Baqqa_desc;
    private final String TAG = this.getClass().getSimpleName();
    private NetworkAvailable networkAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_baqqa);
        ButterKnife.bind(this);
        networkAvailable = new NetworkAvailable(this);
        goldBaqqa_tab = findViewById(R.id.goldBaqqa_tab_id);
        silverBaqqa_tab = findViewById(R.id.silverBaqqa_tab_id);

        if (networkAvailable.isNetworkAvailable()) {
            ApiInterface apiServiceInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<PackagesModel> call = apiServiceInterface.getPackagesInfo();
            call.enqueue(new Callback<PackagesModel>() {
                @Override
                public void onResponse(Call<PackagesModel> call, Response<PackagesModel> response) {
                    PackagesModel packagesModel = response.body();
                    if (packagesModel.getSuccess()) {
                        List<PackagesModel.Packages> packagesList = packagesModel.getMessagePackages().getPackages();
                        Log.i(TAG, packagesList.size() + "");
                        baqqaAdapter = new SelectBaqqaAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), packagesList.get(0).getDesc(), packagesList.get(1).getDesc());
                        baqqa_ViewPager.setAdapter(baqqaAdapter);
                        baqqa_ViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                baqqa_ViewPager.setCurrentItem(tab.getPosition());
                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<PackagesModel> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                }
            });
        } else
            Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.baqqa_back_txtV_id)
    public void go_back() {
        finish();
    }
}
