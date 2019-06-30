package com.routehelperr.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.routehelperr.R;
import com.routehelperr.activities.FindService;
import com.routehelperr.activities.HomeActivity;
import com.routehelperr.activities.Register;
import com.routehelperr.activities.HelpScreen;
import com.routehelperr.activities.UserLocation;
import com.routehelperr.model.ServicesModel;
import com.routehelperr.model.SettingInfoModel;
import com.routehelperr.networking.ApiClient;
import com.routehelperr.networking.ApiInterface;
import com.routehelperr.networking.NetworkAvailable;
import com.routehelperr.utils.ContactUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainHomeFragment extends Fragment implements View.OnClickListener {

    CardView ourServices_layout;
    CardView subscription_layout;
    CardView emergencyCase_layout;
    CardView forHelp_layout;
    CardView salesNumber_layout;
    TextView services_txtV, subscription_txtV, emergency_txtV, help_txtV, salesNum_txtV;
    NetworkAvailable networkAvailable;

    private SettingInfoModel settingInfoModel;
    private ContactUtil contactUtil;
    private String TAG = this.getClass().getSimpleName();
    private List<ServicesModel.ServiceDataModel> servicesList;
    private Typeface custom_font;
    private FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        networkAvailable = new NetworkAvailable(getActivity());
        contactUtil = new ContactUtil(getActivity());
        custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT Arabic Medium.ttf");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);
        ourServices_layout = view.findViewById(R.id.ourServices_layoutItem_id);
        subscription_layout = view.findViewById(R.id.subscription_layoutItem_id);
        emergencyCase_layout = view.findViewById(R.id.emergencyCase_layoutItem_id);
        forHelp_layout = view.findViewById(R.id.forHelp_layoutItem_id);
        salesNumber_layout = view.findViewById(R.id.sales_number_layoutItem_id);
        services_txtV = view.findViewById(R.id.services_txtV_id);
        subscription_txtV = view.findViewById(R.id.subscription_txtV_id);
        emergency_txtV = view.findViewById(R.id.emergency_txtV_id);
        help_txtV = view.findViewById(R.id.help_txtV_id);
        salesNum_txtV = view.findViewById(R.id.salesNum_txtV);
        frameLayout = view.findViewById(R.id.mainFragment_id);

        if (networkAvailable.isNetworkAvailable()) {
            getSettingInfo();
        } else {
            Toast.makeText(getActivity(), R.string.error_connection, Toast.LENGTH_LONG).show();
        }
        services_txtV.setTypeface(custom_font);
        subscription_txtV.setTypeface(custom_font);
        emergency_txtV.setTypeface(custom_font);
        help_txtV.setTypeface(custom_font);
        salesNum_txtV.setTypeface(custom_font);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ourServices_layout.setOnClickListener(this);
        subscription_layout.setOnClickListener(this);
        emergencyCase_layout.setOnClickListener(this);
        forHelp_layout.setOnClickListener(this);
        salesNumber_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = null;
        switch (id) {
            case R.id.ourServices_layoutItem_id:
                if (networkAvailable.isNetworkAvailable()) {
                    Log.i("servicesList: ", servicesList.size() + "");
                    HomeActivity.toolBarTitle.setText(getString(R.string.our_services));
                    Fragment fragment = new HomeServicesFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("our_services", (ArrayList<? extends Parcelable>) servicesList);
                    fragment.setArguments(bundle);
                    displaySelectedFragment(fragment);
                } else
                    Toast.makeText(getActivity(), getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
                break;
            case R.id.subscription_layoutItem_id:
                intent = new Intent(getActivity(), Register.class);
                startActivity(intent);
                break;
            case R.id.emergencyCase_layoutItem_id:
                intent = new Intent(getActivity(), FindService.class);
                startActivity(intent);
                break;
            case R.id.forHelp_layoutItem_id:
                if (networkAvailable.isNetworkAvailable()) {
                    intent = new Intent(getActivity(), HelpScreen.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sales_number_layoutItem_id:
                callPhoneNumber("920029550");
                break;
        }
    }

    public void openWhatsApp() {
        try {
            String toNumber = "0567760260"; // Replace with mobile phone number without +Sign or leading zeros, but with country code.
            //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + "" + toNumber));
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "it may be you dont have whats app", Toast.LENGTH_LONG).show();

        }
    }

    private void getSettingInfo() {
        ApiInterface apiServiceInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ServicesModel> call = apiServiceInterface.getServicesInfo();
        call.enqueue(new Callback<ServicesModel>() {
            @Override
            public void onResponse(Call<ServicesModel> call, Response<ServicesModel> response) {
                ServicesModel servicesModel = response.body();
                if (servicesModel.getSuccess()) {
                    servicesList = servicesModel.getMessage().getServices();
                }
            }

            @Override
            public void onFailure(Call<ServicesModel> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

    private void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_id, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static ProgressDialog showProgressDialog(Context context, boolean cancelable) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(cancelable);
        dialog.show();
        return dialog;
    }

    public void callPhoneNumber(String phone_number) {
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone_number));
                startActivity(callIntent);

            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone_number));
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
                callPhoneNumber("0567760260");
            } else {
                Log.e(TAG, "Permission not Granted");
            }
        }
    }

}
