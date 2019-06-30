package com.routehelperr.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;
import com.routehelperr.model.User;
import com.routehelperr.utils.DialogUtil;
import com.routehelperr.R;
import com.routehelperr.activities.HomeActivity;
import com.routehelperr.model.RegisterResponseModel;
import com.routehelperr.networking.ApiClient;
import com.routehelperr.networking.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    EditText userName_ed, email_ed, phone_ed, IdNumber_ed, password_ed;
    Button update_btn;
    DialogUtil dialogUtil;

    private User userData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userData = getArguments().getParcelable("user_data");
            Log.i("name: ", userData.getUsername());
            Log.i("email: ", userData.getUsername());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        userName_ed = view.findViewById(R.id.profile_userName_ed_id);
        email_ed = view.findViewById(R.id.profile_register_email_ed_id);
        phone_ed = view.findViewById(R.id.profile_phone_ed_id);
        IdNumber_ed = view.findViewById(R.id.profile_identityNum_ed_id);
        password_ed = view.findViewById(R.id.profile_pass_ed_id);
        update_btn = view.findViewById(R.id.profile_update_btn_id);

        userName_ed.setText(userData.getUsername());
        email_ed.setText(userData.getEmail());
        phone_ed.setText(userData.getPhone());
        IdNumber_ed.setText(userData.getIdnumber());

        update_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (!FUtilsValidation.isEmpty(userName_ed, getString(R.string.required))
                && !FUtilsValidation.isEmpty(email_ed, getString(R.string.required))
                && FUtilsValidation.isValidEmail(email_ed, getString(R.string.enter_valid_email))
                && !FUtilsValidation.isEmpty(IdNumber_ed, getString(R.string.required))
                && !FUtilsValidation.isEmpty(phone_ed, getString(R.string.required))
                && !FUtilsValidation.isEmpty(password_ed, getString(R.string.required))
        ) {
            final ProgressDialog dialog = showProgressDialog(getContext(), getString(R.string.updating), false);
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<RegisterResponseModel> call = apiInterface.updateProfile(HomeActivity.userModel.getId(), userName_ed.getText().toString(), email_ed.getText().toString(), phone_ed.getText().toString(), IdNumber_ed.getText().toString(), password_ed.getText().toString());
            call.enqueue(new Callback<RegisterResponseModel>() {
                @Override
                public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
                    dialog.dismiss();
                    if (response.body().getSuccess()) {
                        RegisterResponseModel.Messages messages = response.body().getMessage();
                        HomeActivity.userModel.setUsername(messages.getUsername());
                        HomeActivity.userModel.setEmail(messages.getEmail());
                        HomeActivity.userModel.setPhone(messages.getPhone());
                        HomeActivity.userModel.setIdnumber(messages.getIdnumber());

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
