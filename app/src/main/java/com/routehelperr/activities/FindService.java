package com.routehelperr.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.routehelperr.R;
import com.routehelperr.utils.DialogUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindService extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.map_view)
    MapView mapView;
    GoogleMap googleMap;
    GpsTracker gpsTracker;
    private DialogUtil dialogUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_service);
        ButterKnife.bind(this);

        dialogUtil = new DialogUtil();
        gpsTracker = new GpsTracker(this);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap1) {
        final ProgressDialog progressDialog = dialogUtil.showProgressDialog(this, getString(R.string.loading), false);
        MapsInitializer.initialize(getApplicationContext());
        googleMap = googleMap1;
        googleMap1.setMinZoomPreference(7);
        Location location = gpsTracker.getLocation();
        LatLng ny = new LatLng(location.getLatitude(), location.getLongitude());
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines
//        googleMap1.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap1.addMarker(new MarkerOptions().position(ny).title(address));
        CameraPosition cameraPosition = CameraPosition.builder().target(ny).zoom(16).bearing(0).build();
        googleMap1.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap1.setMyLocationEnabled(true);
        progressDialog.dismiss();
    }

    @OnClick(R.id.findService_back_txtV_id)
    void goBack() {
        finish();
    }

    @OnClick(R.id.findService_call_txtV_id)
    void makeCall() {
        callPhoneNumber();
    }

    @OnClick(R.id.instagram_imageV_icon)
    void instagramClicked() {
        openWebPage(FindService.this, "https://www.instagram.com/p/BykkZRfl-kS/?igshid=i59g55t8r6zb");
    }

    @OnClick(R.id.twitter_imageV_icon)
    void twitterClicked() {
        openWebPage(FindService.this, "https://mobile.twitter.com/R24service");
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

}
