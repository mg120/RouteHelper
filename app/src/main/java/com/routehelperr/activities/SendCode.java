package com.routehelperr.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;
import com.routehelperr.R;
import com.routehelperr.model.ForgetPassSendMailModel;
import com.routehelperr.networking.ApiClient;
import com.routehelperr.networking.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendCode extends AppCompatActivity {

    @BindView(R.id.sendCode_back_txtV_id)
    TextView back_txtV;
    @BindView(R.id.code_ed_id)
    EditText code_ed;
    @BindView(R.id.sendCode_send_btn_id)
    Button sendCode_btn;
    @BindView(R.id.resendCode_txtV_id)
    TextView resendCode_txtV;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_code);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            email = getIntent().getExtras().getString("email");
        }

    }

    @OnClick(R.id.sendCode_back_txtV_id)
    public void goBack() {
        finish();
    }

    @OnClick(R.id.sendCode_send_btn_id)
    public void sendCode() {
        if (!FUtilsValidation.isEmpty(code_ed, getString(R.string.required))
        ) {
            final String code = code_ed.getText().toString();

            ApiInterface apiServiceInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ForgetPassSendMailModel> call = apiServiceInterface.sendCode(code, email, LogIn.selected_lang);
            call.enqueue(new Callback<ForgetPassSendMailModel>() {
                @Override
                public void onResponse(Call<ForgetPassSendMailModel> call, Response<ForgetPassSendMailModel> response) {
                    ForgetPassSendMailModel forgetPassSendMailModel = response.body();
                    if (forgetPassSendMailModel.getSuccess()) {
                        Intent intent = new Intent(SendCode.this, ChangePassword.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SendCode.this, forgetPassSendMailModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ForgetPassSendMailModel> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    @OnClick(R.id.resendCode_txtV_id)
    public void resendCode() {

    }
}
