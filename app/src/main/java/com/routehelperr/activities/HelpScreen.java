package com.routehelperr.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.routehelperr.utils.DialogUtil;
import com.routehelperr.R;
import com.routehelperr.adapter.HelpRecyclerAdapter;
import com.routehelperr.model.CallServicesDataModel;
import com.routehelperr.networking.ApiClient;
import com.routehelperr.networking.ApiInterface;
import com.routehelperr.networking.NetworkAvailable;
import com.routehelperr.utils.ContactUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpScreen extends AppCompatActivity {

    @BindView(R.id.help_back_txtV_id)
    TextView back_txtV;
    @BindView(R.id.help_recyclerV_id)
    RecyclerView help_recyclerV;
    @BindView(R.id.contact_face_icon_id)
    ImageView face_contact_icon;
    @BindView(R.id.contact_twetter_icon_id)
    ImageView twetter_contact_icon;
    @BindView(R.id.contact_instgram_icon_id)
    ImageView instgram_contact_icon;
    @BindView(R.id.contact_twitter_icon_id)
    ImageView contact_twitter_icon;
    @BindView(R.id.help_call_txtV_id)
    TextView call_txtV;

    List<CallServicesDataModel.Info> infoList;
    private final String TAG = this.getClass().getSimpleName();
    private NetworkAvailable networkAvailable;
    private DialogUtil dialogUtil;
    private ContactUtil contactUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);

        networkAvailable = new NetworkAvailable(this);
        dialogUtil = new DialogUtil();
        contactUtil = new ContactUtil(this);

        if (networkAvailable.isNetworkAvailable()) {
            getCallData();
        } else {
            Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
        }

    }

    private void buildHelpRecyclerV(List<CallServicesDataModel.CallServices> services_list) {
        help_recyclerV.setLayoutManager(new LinearLayoutManager(this));
        help_recyclerV.setHasFixedSize(false);

        // Set Adapter
        HelpRecyclerAdapter helpAdapter = new HelpRecyclerAdapter(HelpScreen.this, services_list);
        help_recyclerV.setAdapter(helpAdapter);
    }


    private void getCallData() {
        final ProgressDialog dialog = dialogUtil.showProgressDialog(HelpScreen.this, getString(R.string.loading), false);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CallServicesDataModel> call = apiInterface.getCallServicesData();
        call.enqueue(new Callback<CallServicesDataModel>() {
            @Override
            public void onResponse(Call<CallServicesDataModel> call, Response<CallServicesDataModel> response) {
                CallServicesDataModel callServicesDataModel = response.body();
                dialog.dismiss();
                if (callServicesDataModel.getSuccess()) {

                    List<CallServicesDataModel.CallServices> list = callServicesDataModel.getMessage().getCallservices();
                    infoList = callServicesDataModel.getMessage().getInfo();
                    buildHelpRecyclerV(list);
                } else {
                    Toast.makeText(HelpScreen.this, callServicesDataModel.getData(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CallServicesDataModel> call, Throwable t) {
                t.printStackTrace();
                dialog.dismiss();
                Toast.makeText(HelpScreen.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.help_back_txtV_id)
    public void go_back() {
        Log.i(TAG, "Back Icon");
        finish();
    }

    @OnClick(R.id.help_call_txtV_id)
    public void setCall_txtV() {
        callPhoneNumber();
    }

    @OnClick(R.id.contact_face_icon_id)
    public void faceIconClicked() {
//        try {
//            if (isAppInstalled(HelpScreen.this, "com.facebook.orca") || isAppInstalled(HelpScreen.this, "com.facebook.katana")
//                    || isAppInstalled(HelpScreen.this, "com.example.facebook") || isAppInstalled(HelpScreen.this, "com.facebook.android")) {
//
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(infoList.get(0).getFacebook())));
//            } else {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(infoList.get(0).getFacebook())));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        openWebPage(this, infoList.get(0).getFacebook());
    }

    @OnClick(R.id.contact_instgram_icon_id)
    public void instgramIconClicked() {
        Log.i(TAG, "instgram Icon");
//        try {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(infoList.get(0).getInstagram()));
//            intent.setPackage("com.instagram.android");
//            startActivity(intent);
//        } catch (ActivityNotFoundException anfe) {
//            startActivity(new Intent(Intent.ACTION_VIEW,
//                    Uri.parse(infoList.get(0).getInstagram())));
//        }
        openWebPage(this, "https://www.instagram.com/p/BykkZRfl-kS/?igshid=i59g55t8r6zb");
    }

    @OnClick(R.id.contact_twetter_icon_id)
    public void twetterIconClicked() {
        Log.i(TAG, "twetter Icon");
        openWebPage(this, infoList.get(0).getTwitter());
    }

    @OnClick(R.id.contact_twitter_icon_id)
    void gmailIconClicked() {
        openWebPage(this, "https://mobile.twitter.com/R24service");
    }


    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
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

    public static void openWebPage(Context context, String url) {
        try {
            if (!URLUtil.isValidUrl(url)) {
                Toast.makeText(context, " This is not a valid link", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, " You don't have any browser to open web page", Toast.LENGTH_LONG).show();
        }
    }
}
