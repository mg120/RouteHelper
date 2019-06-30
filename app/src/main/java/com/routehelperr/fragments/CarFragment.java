package com.routehelperr.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;
import com.routehelperr.R;
import com.routehelperr.activities.HomeActivity;
import com.routehelperr.model.RegisterResponseModel;
import com.routehelperr.model.User;
import com.routehelperr.networking.ApiClient;
import com.routehelperr.networking.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CarFragment extends Fragment implements View.OnClickListener {

    EditText car_Number, car_Model, car_Type;
    Button update_btn;
    private User userModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userModel = getArguments().getParcelable("user_data");
            Log.i("car: ", userModel.getCartype());
            Log.i("car: ", userModel.getCarmodal());
            Log.i("car: ", userModel.getCarnumber());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car, container, false);
        car_Number = view.findViewById(R.id.update_carNum_ed_id);
        car_Model = view.findViewById(R.id.update_carModel_ed_id);
        car_Type = view.findViewById(R.id.update_carType_ed_id);
        update_btn = view.findViewById(R.id.car_update_btn_id);

        car_Number.setText(userModel.getCarnumber());
        car_Model.setText(userModel.getCarmodal());
        car_Type.setText(userModel.getCartype());
        update_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        if (!FUtilsValidation.isEmpty(car_Number, getString(R.string.required))
                && !FUtilsValidation.isEmpty(car_Model, getString(R.string.required))
                && !FUtilsValidation.isEmpty(car_Type, getString(R.string.required))
        ) {
            final ProgressDialog dialog = showProgressDialog(getContext(), getString(R.string.updating), false);
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<RegisterResponseModel> call = apiInterface.updateCar(HomeActivity.userModel.getId(), car_Number.getText().toString(), car_Model.getText().toString(), car_Type.getText().toString());
            call.enqueue(new Callback<RegisterResponseModel>() {
                @Override
                public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
                    dialog.dismiss();
                    if (response.body().getSuccess()) {
                        RegisterResponseModel.Messages messages = response.body().getMessage();
                        HomeActivity.userModel.setCarnumber(messages.getCarnumber());
                        HomeActivity.userModel.setCarmodal(messages.getCarmodal());
                        HomeActivity.userModel.setCartype(messages.getCartype());
                        Toast.makeText(getActivity(), getResources().getString(R.string.updatted_successfully), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.error_happened), Toast.LENGTH_SHORT).show();
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

    public static ProgressDialog showProgressDialog(Context context, String message, boolean cancelable) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.setCancelable(cancelable);
        dialog.show();
        return dialog;
    }
}
