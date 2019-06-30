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

public class ForgetPassword extends AppCompatActivity {

    @BindView(R.id.forgetPass_back_txtV_id)
    TextView back_txtV;
    @BindView(R.id.forgetPass_email_ed_id)
    EditText email_ed;
    @BindView(R.id.forgetPass_send_btn_id)
    Button send_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.forgetPass_back_txtV_id)
    public void goBack() {
        finish();
    }

    @OnClick(R.id.forgetPass_send_btn_id)
    public void sendEmail() {
        if (!FUtilsValidation.isEmpty(email_ed, getString(R.string.required))
                && FUtilsValidation.isValidEmail(email_ed, getString(R.string.enter_valid_email))
        ) {
            final String email = email_ed.getText().toString();

            ApiInterface apiServiceInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ForgetPassSendMailModel> call = apiServiceInterface.forgetPassword(email);
            call.enqueue(new Callback<ForgetPassSendMailModel>() {
                @Override
                public void onResponse(Call<ForgetPassSendMailModel> call, Response<ForgetPassSendMailModel> response) {
                    ForgetPassSendMailModel forgetPassSendMailModel = response.body();
                    if (forgetPassSendMailModel.getSuccess()) {
                        Toast.makeText(ForgetPassword.this, forgetPassSendMailModel.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgetPassword.this, SendCode.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ForgetPassword.this, forgetPassSendMailModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ForgetPassSendMailModel> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}
