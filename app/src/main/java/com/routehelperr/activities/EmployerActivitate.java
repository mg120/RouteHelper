package com.routehelperr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.routehelperr.R;
import com.routehelperr.model.LogInResponseModel;
import com.routehelperr.networking.ApiClient;
import com.routehelperr.networking.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployerActivitate extends AppCompatActivity {

    @BindView(R.id.employerActiviate_send_btn_id)
    Button employerActiviate_send_btn;
    @BindView(R.id.employerActiviate_ed_id)
    EditText employerActiviate_ed;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_activitate);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            email = getIntent().getExtras().getString("email");
        }

    }

    @OnClick(R.id.employerActiviate_send_btn_id)
    public void employerActivate() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LogInResponseModel> call = apiInterface.employerActivate(employerActiviate_ed.getText().toString(), email, "en");
        call.enqueue(new Callback<LogInResponseModel>() {
            @Override
            public void onResponse(Call<LogInResponseModel> call, Response<LogInResponseModel> response) {
                LogInResponseModel logInResponseModel = response.body();
                if (logInResponseModel.getSuccess()) {
                    Toast.makeText(EmployerActivitate.this, logInResponseModel.getData(), Toast.LENGTH_SHORT).show();
//                    LogInResponseModel.userData message = logInResponseModel.getMessage();
//                    userModel.setId(message.getId());
//                    userModel.setUsername(message.getUsername());
//                    userModel.setEmail(message.getEmail());
//                    userModel.setPhone(message.getPhone());
//                    userModel.setCarnumber(message.getCarnumber());
//                    userModel.setCarmodal(message.getCarmodal());
//                    userModel.setCartype(message.getCartype());
//                    userModel.setIdnumber(message.getIdnumber());
//
//                    // Convert User Data to Gon OBJECT ...
////                        Gson gson = new Gson();
////                        String user_data = gson.toJson(userModel);
////                        user_data_edito = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
////                        user_data_edito.putString("user_data", user_data);
////                        user_data_edito.commit();
////                        user_data_edito.apply();
//
//                    Intent intent = new Intent(LogIn.this, HomeActivity.class);
//                    intent.putExtra("user_data", userModel);
//                    startActivity(intent);
//                    finish();

                } else {
                    Toast.makeText(EmployerActivitate.this, logInResponseModel.getData(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LogInResponseModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
