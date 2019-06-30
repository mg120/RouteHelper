package com.routehelperr.activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karan.churi.PermissionManager.PermissionManager;
import com.routehelperr.networking.NetworkAvailable;
import com.routehelperr.utils.DialogUtil;
import com.routehelperr.R;
import com.routehelperr.model.MakeOrderModel;
import com.routehelperr.model.MapEmployModel;
import com.routehelperr.model.SettingInfoModel;
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

public class OrderServiceActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.orderService_back_txtV_id)
    TextView back_txtV;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.orderService_call_txtV_id)
    TextView call_tctV;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    //vars
    List<MapEmployModel.Messages> mapEmpList;
    private final String TAG = this.getClass().getSimpleName();
    private DialogUtil dialogUtil;
    private ContactUtil contactUtil;
    private NetworkAvailable networkAvailable;

    private double lat = 0.0;
    private double lng = 0.0;
    private int emp_id;
    SettingInfoModel.ServiceModel serviceModel;
    private GoogleMap gmap;
    PermissionManager permissionManager;
    private EditText policyNum_ed, enterProblem_ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_service);
        ButterKnife.bind(this);
        dialogUtil = new DialogUtil();
        contactUtil = new ContactUtil(this);
        networkAvailable = new NetworkAvailable(this);

        permissionManager = new PermissionManager() {
        };
        permissionManager.checkAndRequestPermissions(this);

        if (getIntent().getExtras() != null) {
            serviceModel = getIntent().getExtras().getParcelable("service_data");
//            getEmaployMaps();
        }

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    public void getEmaployMaps() {
        final ProgressDialog progressDialog = dialogUtil.showProgressDialog(this, getString(R.string.loading), false);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MapEmployModel> call = apiInterface.getEmployMaps();
        call.enqueue(new Callback<MapEmployModel>() {
            @Override
            public void onResponse(Call<MapEmployModel> call, Response<MapEmployModel> response) {
                MapEmployModel mapEmployModel = response.body();
                if (mapEmployModel.getSuccess()) {
                    mapEmpList = mapEmployModel.getMessage();
                    Log.i(TAG, mapEmpList.size() + "");
                    Log.i("EmpList: ", mapEmpList.size() + "");
                    if (mapEmpList.size() > 0) {
                        for (int i = 0; i < mapEmpList.size(); i++) {
                            if (!mapEmpList.get(i).getLat().equals("0")) {
                                LatLng latLng1 = new LatLng(Double.parseDouble(mapEmpList.get(i).getLat()), Double.parseDouble(mapEmpList.get(i).getLng()));
                                Marker market_marker = gmap.addMarker(new MarkerOptions().position(latLng1).title(mapEmpList.get(i).getUsername()).icon(BitmapDescriptorFactory.fromResource(R.drawable.car2)));
                            }
                        }
                    }
                    gmap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Log.i("poss: ", marker.getId() + "");
//                            lat = Double.parseDouble(mapEmpList.get(pos).getLat());
//                            lng = Double.parseDouble(mapEmpList.get(pos).getLng());
//                            emp_id = mapEmpList.get(pos).getId();
//                            Log.i("emp_id: ", emp_id + "");
                        }
                    });
                } else {
                    Toast.makeText(OrderServiceActivity.this, mapEmployModel.getData(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
                progressDialog.cancel();
            }

            @Override
            public void onFailure(Call<MapEmployModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });

    }

    @OnClick(R.id.orderService_back_txtV_id)
    public void go_back() {
        finish();
    }

    @OnClick(R.id.orderService_call_txtV_id)
    public void setCall_tctV() {
        callPhoneNumber();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(7);
//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(OrderServiceActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        int height = 48;
        int width = 48;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.car2);
        Bitmap b = bitmapdraw.getBitmap();
        final Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        final ProgressDialog progressDialog = dialogUtil.showProgressDialog(this, getString(R.string.loading), false);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MapEmployModel> call = apiInterface.getEmployMaps();
        call.enqueue(new Callback<MapEmployModel>() {
            @Override
            public void onResponse(Call<MapEmployModel> call, Response<MapEmployModel> response) {
                final MapEmployModel mapEmployModel = response.body();
                if (mapEmployModel.getSuccess()) {
                    mapEmpList = mapEmployModel.getMessage();
                    Log.i(TAG, mapEmpList.size() + "");
                    if (mapEmpList.size() > 0) {
                        for (int i = 0; i < mapEmpList.size(); i++) {
                            if (!mapEmpList.get(i).getLat().equals("0")) {
                                LatLng latLng1 = new LatLng(Double.parseDouble(mapEmpList.get(i).getLat()), Double.parseDouble(mapEmpList.get(i).getLng()));
                                Marker market_marker = googleMap.addMarker(new MarkerOptions().position(latLng1).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                            }
                        }
                    }

                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            final int pos = Integer.parseInt((marker.getId()).replaceAll("\\D+", ""));
                            final Dialog pass_Dialog = new Dialog(OrderServiceActivity.this);
                            pass_Dialog.setContentView(R.layout.pop_up_layout);
                            pass_Dialog.setCancelable(true);

                            Window window = pass_Dialog.getWindow();
                            WindowManager.LayoutParams wlp = window.getAttributes();
                            wlp.gravity = Gravity.BOTTOM;
                            wlp.flags &= ~WindowManager.LayoutParams.MATCH_PARENT;
                            window.setAttributes(wlp);
                            LinearLayout driver_contact_layout = pass_Dialog.findViewById(R.id.driver_contact_layout_id);
                            TextView title_txt = pass_Dialog.findViewById(R.id.title_txt_id);
                            TextView email_txt = pass_Dialog.findViewById(R.id.email_txtV_id);
                            enterProblem_ed = pass_Dialog.findViewById(R.id.enterProblem_ed_id);
                            policyNum_ed = pass_Dialog.findViewById(R.id.policyNum_ed_id);
                            Button chat_driver_btn = pass_Dialog.findViewById(R.id.driver_chat_btn_id);
                            Button book_trip_btn = pass_Dialog.findViewById(R.id.book_trip_btn_id);

                            pass_Dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            pass_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            pass_Dialog.show();

                            title_txt.setText(mapEmpList.get(pos).getUsername());
                            email_txt.setText(mapEmpList.get(pos).getEmail());
                            emp_id = mapEmpList.get(pos).getId();
                            lat = Double.parseDouble(mapEmpList.get(pos).getLat());
                            lng = Double.parseDouble(mapEmpList.get(pos).getLng());

                            chat_driver_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    pass_Dialog.dismiss();
                                    Intent intent = new Intent(OrderServiceActivity.this, Chat.class);
                                    intent.putExtra("receiver_Id", mapEmpList.get(pos).getId());
                                    intent.putExtra("receiver_name", mapEmpList.get(pos).getUsername());
                                    startActivity(intent);
                                }
                            });

                            book_trip_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (!FUtilsValidation.isEmpty(enterProblem_ed, getString(R.string.required))
                                            && !FUtilsValidation.isEmpty(policyNum_ed, getString(R.string.required))) {
                                        Log.i("user_id: ", HomeActivity.userModel.getId() + "");
                                        Log.i("emp_id", emp_id + "");
                                        Log.i("lat", lat + "");
                                        Log.i("lng", lng + "");
                                        Log.i("user_lat", HomeActivity.userModel.getLat() + "");
                                        Log.i("user_lng", HomeActivity.userModel.getLng() + "");
                                        Log.i("problem", enterProblem_ed.getText().toString());
                                        Log.i("policy", policyNum_ed.getText().toString());
                                        Log.i("serivce", serviceModel.getId() + "");
                                        pass_Dialog.dismiss();
                                        final ProgressDialog dialog = dialogUtil.showProgressDialog(OrderServiceActivity.this, getString(R.string.sending), false);

                                        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                                        Call<MakeOrderModel> call = apiInterface.orderService(HomeActivity.userModel.getId(), HomeActivity.userModel.getLat(), HomeActivity.userModel.getLng(), emp_id, lat, lng, enterProblem_ed.getText().toString(), policyNum_ed.getText().toString(), serviceModel.getId());
                                        call.enqueue(new Callback<MakeOrderModel>() {
                                            @Override
                                            public void onResponse(Call<MakeOrderModel> call, Response<MakeOrderModel> response) {
                                                dialog.dismiss();
                                                if (response.body().getSuccess()) {
                                                    Toast.makeText(OrderServiceActivity.this, response.body().getData(), Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(OrderServiceActivity.this, response.body().getData(), Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<MakeOrderModel> call, Throwable t) {
                                                t.printStackTrace();
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                }
                            });
                            return true;
                        }
                    });
                } else {
                    Toast.makeText(OrderServiceActivity.this, mapEmployModel.getData(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MapEmployModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
        GpsTracker gpsTracker = new GpsTracker(this);
        Location location = gpsTracker.getLocation();
        LatLng ny = new LatLng(location.getLatitude(), location.getLongitude());
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
        gmap.setMyLocationEnabled(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

//    @OnClick(R.id.orderService_send_btn_id)
//    public void orderService() {
//        if (!FUtilsValidation.isEmpty(problem_ed, getString(R.string.required))
//                && !FUtilsValidation.isEmpty(policy_Num, getString(R.string.required))) {
//            if (lat == 0.0 || lng == 0.0) {
//                Toast.makeText(this, getString(R.string.choose_employer_fisrt), Toast.LENGTH_SHORT).show();
//                return;
//            }
//            final ProgressDialog dialog = dialogUtil.showProgressDialog(this, getString(R.string.sending), false);
//            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//            Call<MakeOrderModel> call = apiInterface.orderService(HomeActivity.userModel.getId(), HomeActivity.userModel.getLat(), HomeActivity.userModel.getLng(), emp_id, lat, lng, problem_ed.getText().toString(), policy_Num.getText().toString(), serviceModel.getId(), "en");
//            call.enqueue(new Callback<MakeOrderModel>() {
//                @Override
//                public void onResponse(Call<MakeOrderModel> call, Response<MakeOrderModel> response) {
//                    dialog.dismiss();
//                    if (response.body().getSuccess()) {
//                        Toast.makeText(OrderServiceActivity.this, response.body().getData(), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(OrderServiceActivity.this, response.body().getData(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<MakeOrderModel> call, Throwable t) {
//                    t.printStackTrace();
//                    dialog.dismiss();
//                }
//            });
//        }
//    }

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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 101) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                callPhoneNumber();
//            } else {
//                Log.e(TAG, "Permission not Granted");
//            }
//        }
    }
}

