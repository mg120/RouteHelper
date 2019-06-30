package com.routehelperr.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.routehelperr.model.User;
import com.routehelperr.utils.DialogUtil;
import com.routehelperr.R;
import com.routehelperr.fragments.CarFragment;
import com.routehelperr.fragments.ProfileFragment;
import com.routehelperr.model.UpdateLocationModel;
import com.routehelperr.networking.ApiClient;
import com.routehelperr.networking.ApiInterface;
import com.routehelperr.utils.ContactUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccount extends AppCompatActivity {

    @BindView(R.id.profile_tab_id)
    LinearLayout profile_tab;
    @BindView(R.id.car_tab_id)
    LinearLayout car_tab;
    @BindView(R.id.location_tab_id)
    LinearLayout location_tab;
    @BindView(R.id.myAccount_frame_id)
    FrameLayout frameLayout;
    @BindView(R.id.myAccount_call_txtV_id)
    TextView myAccount_call;

    boolean profile_clicked;
    boolean location_clicked;
    boolean car_clicked;
    private GpsTracker gpsTracker;
    private ContactUtil contactUtil;
    private User userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        ButterKnife.bind(this);
        contactUtil = new ContactUtil(this);

        if (getIntent().hasExtra("user_data")) {
            userModel = getIntent().getParcelableExtra("user_data");
        }
        // Set Products is clicked By Default
        profile_clicked = true;
        if (profile_clicked) {
            profile_tab.setBackgroundResource(R.drawable.account_tab_background);
            car_tab.setBackgroundColor(Color.TRANSPARENT);
            location_tab.setBackgroundColor(Color.TRANSPARENT);
            final ProfileFragment fragment = new ProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("user_data", userModel);
            fragment.setArguments(bundle);
            displaySelectedFragment(fragment);
            profile_clicked = false;
            location_clicked = true;
            car_clicked = true;
        }
    }

    @OnClick(R.id.profile_tab_id)
    public void getProfile() {
        if (profile_clicked) {
            profile_tab.setBackgroundResource(R.drawable.account_tab_background);
            car_tab.setBackgroundColor(Color.TRANSPARENT);
            location_tab.setBackgroundColor(Color.TRANSPARENT);
            final ProfileFragment fragment = new ProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("user_data", userModel);
            fragment.setArguments(bundle);
            displaySelectedFragment(fragment);
            profile_clicked = false;
            location_clicked = true;
            car_clicked = true;
        }
    }

    @OnClick(R.id.car_tab_id)
    public void getCar() {
        if (car_clicked) {
            car_tab.setBackgroundResource(R.drawable.account_tab_background);
            profile_tab.setBackgroundColor(Color.TRANSPARENT);
            location_tab.setBackgroundColor(Color.TRANSPARENT);

            CarFragment carFragment = new CarFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("user_data", userModel);
            carFragment.setArguments(bundle);
            displaySelectedFragment(carFragment);
            car_clicked = false;
            profile_clicked = true;
            location_clicked = true;
        }
    }

    @OnClick(R.id.location_tab_id)
    public void getLocation() {
        if (location_clicked) {
            location_tab.setBackgroundResource(R.drawable.account_tab_background);
            profile_tab.setBackgroundColor(Color.TRANSPARENT);
            car_tab.setBackgroundColor(Color.TRANSPARENT);

            gpsTracker = new GpsTracker(this);
            if (gpsTracker.canGetLocation) {
                Location location = gpsTracker.getLocation();
                double lat = location.getLatitude();
                double lng = location.getLongitude();

                final ProgressDialog dialog = DialogUtil.showProgressDialog(MyAccount.this, getString(R.string.updating), false);
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<UpdateLocationModel> call = apiInterface.updateLocation(HomeActivity.userModel.getId(), lat, lng);
                call.enqueue(new Callback<UpdateLocationModel>() {
                    @Override
                    public void onResponse(Call<UpdateLocationModel> call, Response<UpdateLocationModel> response) {
                        if (response.body().getSuccess()) {
                            List<UpdateLocationModel.updateData> updateData = response.body().getMessage();
                            dialog.dismiss();
                            HomeActivity.userModel.setLat(Double.parseDouble(updateData.get(0).getLat()));
                            HomeActivity.userModel.setLng(Double.parseDouble(updateData.get(0).getLng()));
                            Toast.makeText(MyAccount.this, getResources().getString(R.string.get_location_success), Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(MyAccount.this, response.body().getData(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateLocationModel> call, Throwable t) {
                        dialog.dismiss();
                        t.printStackTrace();
                    }
                });
            }
            car_clicked = false;
            profile_clicked = true;
            location_clicked = true;
        }
    }

    @OnClick(R.id.myAccount_call_txtV_id)
    public void accountCall() {
        callPhoneNumber();
    }

    @OnClick(R.id.myAccount_back_txtV_id)
    public void goBack() {
        finish();
    }


    private void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.myAccount_frame_id, fragment);
        fragmentTransaction.commit();
    }

    public void callPhoneNumber() {
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "920029550"));
                startActivity(callIntent);

            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "920029550"));
                startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber();
            } else {
                Log.e("permissions: ", "Permission not Granted");
            }
        }
    }
}
