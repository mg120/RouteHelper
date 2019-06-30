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
import android.widget.ImageView;
import android.widget.TextView;

import com.routehelperr.R;
import com.routehelperr.utils.ContactUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutUs extends AppCompatActivity {

    @BindView(R.id.aboutUs_back_txtV_id)
    TextView back;
    @BindView(R.id.aboutUs_txtV_id)
    TextView aboutUs_txtV;
    @BindView(R.id.aboutUs_call_txtV_id)
    TextView call_txtV;
    @BindView(R.id.site_txtV_id)
    TextView site_txtV;

    private ContactUtil contactUtil;
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        contactUtil = new ContactUtil(this);

        if (getIntent() != null) {
            String about = getIntent().getExtras().getString("about");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                aboutUs_txtV.setText(Html.fromHtml(about, Html.FROM_HTML_MODE_COMPACT));
            } else {
                aboutUs_txtV.setText(Html.fromHtml(about));
            }
        }
    }

    @OnClick(R.id.aboutUs_back_txtV_id)
    void go_back() {
        finish();
    }

    @OnClick(R.id.aboutUs_call_txtV_id)
    void setCall_txtV() {
        callPhoneNumber();
    }

    @OnClick(R.id.aboutUs_txtV_id)
    void go_Site() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://rsg-est.com/"));
        startActivity(browserIntent);
    }

    void callPhoneNumber() {
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
                Log.e(TAG, "Permission not Granted");
            }
        }
    }
}
