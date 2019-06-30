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
import android.widget.TextView;

import com.routehelperr.R;
import com.routehelperr.utils.ContactUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrivacyPolicy extends AppCompatActivity {

    @BindView(R.id.privacy_txtV_id)
    TextView privacy_txtV;
    @BindView(R.id.privacy_back_txtV_id)
    TextView back;
    @BindView(R.id.privacy_call_txtV_id)
    TextView privacy_call_txtV;

    private ContactUtil contactUtil;
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        ButterKnife.bind(this);

        if (getIntent().hasExtra("conditions")) {
            String terms = getIntent().getExtras().getString("conditions");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                privacy_txtV.setText(Html.fromHtml(terms, Html.FROM_HTML_MODE_COMPACT));
            } else {
                privacy_txtV.setText(Html.fromHtml(terms));
            }
        }

        contactUtil = new ContactUtil(this);
    }

    @OnClick(R.id.privacy_back_txtV_id)
    public void go_back() {
        finish();
    }

    @OnClick(R.id.privacy_call_txtV_id)
    public void setPrivacy_call_txtV() {
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
