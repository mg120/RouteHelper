package com.routehelperr.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.routehelperr.R;
import com.routehelperr.model.SettingInfoModel;
import com.routehelperr.networking.Urls;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RouteServiceDetails extends AppCompatActivity {

    @BindView(R.id.service_title_txtV_id)
    TextView service_title_txtV;
    @BindView(R.id.service_details_imageV_id)
    ImageView service_imageV;
    @BindView(R.id.vehicle_transport_txtV_id)
    TextView service_desc_txtV;
    @BindView(R.id.detailsService_back_txtV_id)
    TextView back_txtV;
    @BindView(R.id.detailsService_call_txtV_id)
    TextView call_txtV;
    @BindView(R.id.request_service_btn_id)
    Button request_service_btn;

    SettingInfoModel.ServiceModel service_data;
    String user_data;

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_service_details);
        ButterKnife.bind(this);

        if (getIntent().hasExtra("service_data")) {
            service_data = getIntent().getExtras().getParcelable("service_data");

            Log.i("image", Urls.imagesBase_Url + service_data.getImage());
            service_title_txtV.setText(service_data.getTitle());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                service_desc_txtV.setText(Html.fromHtml(service_data.getTitle(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                service_desc_txtV.setText(Html.fromHtml(service_data.getTitle()));
            }
            Glide.with(this)
                    .load(Urls.imagesBase_Url + service_data.getImage())
                    .centerCrop()
                    .into(service_imageV);
        }

    }

    @OnClick(R.id.detailsService_back_txtV_id)
    public void details_back() {
        finish();
    }

    @OnClick(R.id.request_service_btn_id)
    public void requestService() {
        Log.i(TAG, "Button Clicked");


        if (HomeActivity.userModel != null && !HomeActivity.userModel.getUsername().equals("")) {
//             Retrive Gson Object from Shared Prefernces ....
            Intent intent = new Intent(RouteServiceDetails.this, OrderServiceActivity.class);
            intent.putExtra("service_data", service_data);
            startActivity(intent);
//            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
        } else {
            Toast.makeText(RouteServiceDetails.this, getString(R.string.login_first), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RouteServiceDetails.this, LogIn.class);
//            intent.putExtra("lang", "en");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
//                startActivity(intent);
//            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
        }
    }

    @OnClick(R.id.detailsService_call_txtV_id)
    public void setCall_txtV() {
        callPhoneNumber();
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
                callIntent.setData(Uri.parse("tel:" + "0567760260"));
                startActivity(callIntent);

            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "0567760260"));
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
                Log.e(TAG, "Permission not Granted");
            }
        }
    }
}
