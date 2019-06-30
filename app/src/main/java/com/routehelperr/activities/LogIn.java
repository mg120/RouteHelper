package com.routehelperr.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.routehelperr.utils.DialogUtil;
import com.routehelperr.R;
import com.routehelperr.model.LogInResponseModel;
import com.routehelperr.model.User;
import com.routehelperr.networking.ApiClient;
import com.routehelperr.networking.ApiInterface;
import com.routehelperr.networking.NetworkAvailable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends AppCompatActivity {

    @BindView(R.id.email_ed_id)
    EditText email_ed;
    @BindView(R.id.password_ed_id)
    EditText password_ed;
    @BindView(R.id.login_btn_id)
    Button login_btn;
    @BindView(R.id.signUp_txtV_id)
    TextView register_txtV;
    @BindView(R.id.forget_pass_txtV_id)
    TextView forgetPass_txtV;

    private User userModel;
    private DialogUtil dialogUtil;
    // put data to shared preferences ...
    SharedPreferences.Editor user_data_edito;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    public static final String MY_PREFS_NAME = "all_user_data";
    private NetworkAvailable networkAvailable;

    public static String selected_lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            selected_lang = getIntent().getExtras().getString("lang");
        }
        networkAvailable = new NetworkAvailable(this);
        dialogUtil = new DialogUtil();
    }

    @OnClick(R.id.login_btn_id)
    public void login() {
        if (networkAvailable.isNetworkAvailable()) {
            if (!FUtilsValidation.isEmpty(email_ed, getString(R.string.required))
                    && FUtilsValidation.isValidEmail(email_ed, getString(R.string.enter_valid_email))
                    && !FUtilsValidation.isEmpty(password_ed, getString(R.string.required))) {

                // get token from Firebase
                String token = FirebaseInstanceId.getInstance().getToken();

                final ProgressDialog dialog = dialogUtil.showProgressDialog(this, getString(R.string.loginning), false);
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<LogInResponseModel> call = apiInterface.logIn(email_ed.getText().toString(), password_ed.getText().toString(), token, selected_lang);
                call.enqueue(new Callback<LogInResponseModel>() {
                    @Override
                    public void onResponse(Call<LogInResponseModel> call, Response<LogInResponseModel> response) {
                        userModel = new User();
                        dialog.dismiss();
                        LogInResponseModel logInResponseModel = response.body();
                        if (logInResponseModel.getSuccess()) {
                            LogInResponseModel.userData message = logInResponseModel.getMessage();
                            userModel.setId(message.getId());
                            userModel.setUsername(message.getUsername());
                            userModel.setEmail(message.getEmail());
                            userModel.setPhone(message.getPhone());
                            userModel.setCarnumber(message.getCarnumber());
                            userModel.setCarmodal(message.getCarmodal());
                            userModel.setCartype(message.getCartype());
                            userModel.setIdnumber(message.getIdnumber());
                            userModel.setLat(Double.parseDouble(message.getLat()));
                            userModel.setLng(Double.parseDouble(message.getLng()));

                            // Convert User Data to Gon OBJECT ...
                            Gson gson = new Gson();
                            String user_data = gson.toJson(userModel);
                            user_data_edito = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            user_data_edito.putString("user_data", user_data);
                            user_data_edito.commit();
                            user_data_edito.apply();

                            Intent intent = new Intent(LogIn.this, HomeActivity.class);
                            intent.putExtra("user_data", userModel);
                            intent.putExtra("lang", selected_lang);
                            startActivity(intent);
                            finish();

                        } else {
//                        if (logInResponseModel.getData().equals("End subscription Please renew your subscription to enjoy our services")) {
//                            Toast.makeText(LogIn.this, logInResponseModel.getData(), Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(LogIn.this, EmployerActivitate.class);
//                            intent.putExtra("email", email_ed.getText().toString());
//                            startActivity(intent);
//                        } else {
                            Toast.makeText(LogIn.this, logInResponseModel.getData(), Toast.LENGTH_SHORT).show();
//                        }
                        }
                    }

                    @Override
                    public void onFailure(Call<LogInResponseModel> call, Throwable t) {
                        t.printStackTrace();
                        dialog.dismiss();
                    }
                });
            }
        } else {
            Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.signUp_txtV_id)
    public void go_signUp() {
        Intent intent = new Intent(LogIn.this, Register.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_right);
    }

    @OnClick(R.id.forget_pass_txtV_id)
    public void forgetPass() {
        Intent intent = new Intent(LogIn.this, ForgetPassword.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
    }
}
