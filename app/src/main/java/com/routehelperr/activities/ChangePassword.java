package com.routehelperr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;
import com.routehelperr.R;
import com.routehelperr.model.ChangePassModel;
import com.routehelperr.networking.ApiClient;
import com.routehelperr.networking.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

    @BindView(R.id.changePass_back_txtV_id)
    TextView back_btn;
    @BindView(R.id.changePass_password_ed_id)
    EditText password_ed;
    @BindView(R.id.changePass_confrimPass_ed_id)
    EditText confirmPass_ed;
    @BindView(R.id.changePass_change_btn_id)
    Button change_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.changePass_back_txtV_id)
    public void go_back() {
        finish();
    }

    @OnClick(R.id.changePass_change_btn_id)
    public void changePassword() {
        if (!FUtilsValidation.isEmpty(password_ed, getString(R.string.required))
                && !FUtilsValidation.isEmpty(confirmPass_ed, getString(R.string.required))) {

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ChangePassModel> call = apiInterface.new_Pass(password_ed.getText().toString(), confirmPass_ed.getText().toString(), LogIn.selected_lang);
            call.enqueue(new Callback<ChangePassModel>() {
                @Override
                public void onResponse(Call<ChangePassModel> call, Response<ChangePassModel> response) {
                    ChangePassModel changePassModel = response.body();
                    if (changePassModel.getSuccess()) {
                        Toast.makeText(ChangePassword.this, changePassModel.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChangePassword.this, changePassModel.getData(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ChangePassModel> call, Throwable t) {

                }
            });
        }
    }
}
