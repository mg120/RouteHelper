package com.routehelperr.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.isapanah.awesomespinner.AwesomeSpinner;
import com.routehelperr.R;
import com.routehelperr.model.InfoModalModel;
import com.routehelperr.model.RegisterResponseModel;
import com.routehelperr.model.User;
import com.routehelperr.networking.ApiClient;
import com.routehelperr.networking.ApiInterface;
import com.routehelperr.utils.DialogUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployerRegisteration extends AppCompatActivity {

    @BindView(R.id.fullName_ed_id)
    EditText fullName_ed;
    @BindView(R.id.register_email_ed_id)
    EditText email_ed;
    @BindView(R.id.phone_ed_id)
    EditText phone_ed;
    @BindView(R.id.identityNum_ed_id)
    EditText identityNum_ed;
    @BindView(R.id.polisaNum_ed_id)
    EditText polisaNum_ed;
    @BindView(R.id.register_password_ed_id)
    EditText password_ed;
    @BindView(R.id.carNum_ed_id)
    EditText carNum_ed;
    @BindView(R.id.carModel_ed_id)
    EditText carModel_ed;
    @BindView(R.id.carType_ed_id)
    EditText carType_ed;
    @BindView(R.id.discountCode_ed_id)
    EditText discountCode_ed;
    @BindView(R.id.register_btn_id)
    Button register_btn;
    @BindView(R.id.logIn_txtV_id)
    TextView logIn_txtV;
    @BindView(R.id.as_user_layout_data)
    LinearLayout asUser_layout_data;
    @BindView(R.id.baqqaBositives_txtV_id)
    TextView baqqaBositives_txtV;
    @BindView(R.id.detect_location_txt_id)
    TextView detect_location;
    @BindView(R.id.baqqaVal_txtV_id)
    TextView baqqaVal_txtV;
    @BindView(R.id.modal_spinner)
    AwesomeSpinner spinner;
    @BindView(R.id.countryCode_spinner)
    AwesomeSpinner countryCode_spinner;

    private int register_type = 1;
    private int baqqa_type = 1;
    private String selected_modal, selected_code;
    private User userModel;
    private int PLACE_PICKER_REQUEST = 10;
    private int baqqa_request_code = 110;
    double latitude, longtitude;
    String address, cityName, streetName;
    private DialogUtil dialogUtil;
    private String baqqa_cost;
    SharedPreferences.Editor user_data_edito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_registeration);
        ButterKnife.bind(this);

        dialogUtil = new DialogUtil();

        countryCode_spinner.setSpinnerHint(getString(R.string.country_code));
        countryCode_spinner.setHintTextColor(Color.WHITE);
        countryCode_spinner.setSelectedItemHintColor(Color.WHITE);
        final List<String> codeList = new ArrayList<String>();
        codeList.add("+965");
        codeList.add("+973");
        codeList.add("+966");
        codeList.add("+20");

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(EmployerRegisteration.this, android.R.layout.simple_spinner_item, codeList);
        countryCode_spinner.setAdapter(categoriesAdapter);
        countryCode_spinner.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                //TODO YOUR ACTIONS
                selected_code = itemAtPosition;
            }
        });

        getModelData();
        email_ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (FUtilsValidation.isValidEmail(email_ed, getString(R.string.enter_valid_email))) {
                        email_ed.setError(getString(R.string.enter_valid_email));
                    }
                }
            }
        });
    }

    private void getModelData() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<InfoModalModel> call = apiInterface.getModalInfo();
        call.enqueue(new Callback<InfoModalModel>() {
            @Override
            public void onResponse(Call<InfoModalModel> call, Response<InfoModalModel> response) {
                if (response.body().getSuccess()) {
                    List<InfoModalModel.ModalYear> modalsList = response.body().getMessage().getInfo();

                    spinner.setSpinnerHint(getString(R.string.select_modal));
                    spinner.setHintTextColor(Color.WHITE);
                    spinner.setSelectedItemHintColor(Color.WHITE);
                    List<String> list = new ArrayList<String>();
                    for (int i = 0; i < modalsList.size(); i++)
                        list.add(modalsList.get(i).getModalyear());

                    ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(EmployerRegisteration.this, android.R.layout.simple_spinner_item, list);
                    spinner.setAdapter(categoriesAdapter);
                    spinner.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
                        @Override
                        public void onItemSelected(int position, String itemAtPosition) {
                            //TODO YOUR ACTIONS
                            selected_modal = itemAtPosition;
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<InfoModalModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void onRadioBaqqaClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.silverBaqqa_btn_id:
                baqqa_type = 1;
                baqqaVal_txtV.setText(String.valueOf(250) + "ريال");
                break;
            case R.id.goldBaqqa_btn_id:
                baqqa_type = 2;
                baqqaVal_txtV.setText(String.valueOf(400) + "ريال");
                break;
        }
    }

    @OnClick(R.id.logIn_txtV_id)
    public void go_logIn() {
        Intent intent = new Intent(EmployerRegisteration.this, LogIn.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.baqqaBositives_txtV_id)
    public void setBaqqaBositives() {
        Intent intent = new Intent(EmployerRegisteration.this, SelectBaqqa.class);
        startActivityForResult(intent, baqqa_request_code);
    }

    @OnClick(R.id.register_btn_id)
    public void register() {
        if (!FUtilsValidation.isEmpty(fullName_ed, getString(R.string.required))
                && !FUtilsValidation.isEmpty(email_ed, getString(R.string.required))
                && FUtilsValidation.isValidEmail(email_ed, getString(R.string.enter_valid_email))
                && !FUtilsValidation.isEmpty(identityNum_ed, getString(R.string.required))
                && !FUtilsValidation.isEmpty(polisaNum_ed, getString(R.string.required))
                && !FUtilsValidation.isEmpty(phone_ed, getString(R.string.required))
                && !FUtilsValidation.isEmpty(password_ed, getString(R.string.required))
                && !FUtilsValidation.isEmpty(carNum_ed, getString(R.string.required))
                && !FUtilsValidation.isEmpty(carModel_ed, getString(R.string.required))
                && !FUtilsValidation.isEmpty(carType_ed, getString(R.string.required))
        ) {
//            Intent intent = new Intent(EmployerRegisteration.this, PaymentMethod.class);
//            intent.putExtra("cost", baqqaVal_txtV.getText().toString());
//            startActivity(intent);

            final ProgressDialog dialog = dialogUtil.showProgressDialog(this, getString(R.string.registering), false);
//            // get token from Firebase
            String token = FirebaseInstanceId.getInstance().getToken();

            String fullName = fullName_ed.getText().toString();
            String email = email_ed.getText().toString();
            String user_identity = identityNum_ed.getText().toString();
            String polisa_Num = polisaNum_ed.getText().toString();
            String phone_Num = phone_ed.getText().toString();
            String password = password_ed.getText().toString();
            String car_Num = carNum_ed.getText().toString();
            String car_Model = carModel_ed.getText().toString();
            String car_Type = carType_ed.getText().toString();
//
//            //  get choice Baqqa from RadioButtons
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<RegisterResponseModel> call = apiInterface.register(fullName, email, phone_Num, user_identity, password, car_Num, car_Model, car_Type, register_type, baqqa_type, "", latitude, longtitude, token, LogIn.selected_lang);
            call.enqueue(new Callback<RegisterResponseModel>() {
                @Override
                public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
                    userModel = new User();
                    RegisterResponseModel registerResponseModel = response.body();
                    dialog.dismiss();
                    if (registerResponseModel.getSuccess()) {
                        RegisterResponseModel.Messages messages = registerResponseModel.getMessage();
                        // Success
                        userModel.setId(messages.getId());
                        userModel.setUsername(messages.getUsername());
                        userModel.setEmail(messages.getEmail());
                        userModel.setPhone(messages.getPhone());
                        userModel.setCarnumber(messages.getCarnumber());
                        userModel.setCarmodal(messages.getCarmodal());
                        userModel.setCartype(messages.getCartype());
                        userModel.setIdnumber(messages.getIdnumber());
                        userModel.setLat(Double.parseDouble(messages.getLat()));
                        userModel.setLng(Double.parseDouble(messages.getIdnumber()));

//                        // Convert User Data to Gon OBJECT ...
                        Gson gson = new Gson();
                        String user_data = gson.toJson(userModel);
                        user_data_edito = getSharedPreferences(LogIn.MY_PREFS_NAME, MODE_PRIVATE).edit();
                        user_data_edito.putString("user_data", user_data);
                        user_data_edito.commit();
                        user_data_edito.apply();

                        Intent intent = new Intent(EmployerRegisteration.this, HomeActivity.class);
                        intent.putExtra("user_data", userModel);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(EmployerRegisteration.this, getResources().getString(R.string.data_used_before), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                    t.printStackTrace();
                    dialog.dismiss();
                }
            });
        }
    }

    @OnClick(R.id.detect_location_txt_id)
    public void setDetect_location() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(EmployerRegisteration.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                user_Image();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                address = String.format("%s", place.getAddress());
                latitude = place.getLatLng().latitude;
                longtitude = place.getLatLng().longitude;

                Log.e("lat", latitude + "");
                Log.e("lan", longtitude + "");
                Log.e("address", address);
                Geocoder gcd = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = gcd.getFromLocation(latitude, longtitude, 1);

                    if (addresses.size() > 0 && addresses != null) {

                        cityName = addresses.get(0).getSubAdminArea();

                        int maxAddressLine = addresses.get(0).getMaxAddressLineIndex();
                        streetName = addresses.get(0).getFeatureName();
//                        locality = addresses.get(0).getSubLocality();
                        String admin_adrea = addresses.get(0).getAdminArea();

                        detect_location.setText(getString(R.string.get_location_success));
                    } else {
                        // do your stuff
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == baqqa_request_code) {
            if (resultCode == RESULT_OK) {
                // Get String data from Intent
                baqqa_cost = data.getStringExtra("baqqa_cost");
                baqqaVal_txtV.setText(baqqa_cost + " ريال ");
            }
        }
    }

}
