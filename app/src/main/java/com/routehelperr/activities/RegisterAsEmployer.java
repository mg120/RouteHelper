package com.routehelperr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.routehelperr.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterAsEmployer extends AppCompatActivity {

    @BindView(R.id.employerRegister_back_txtV_id)
    TextView back_txtV;
    @BindView(R.id.employerRegister_code_ed_id)
    EditText code_ed;
    @BindView(R.id.employerRegister_activate_btn_id)
    Button activate_btn;
    @BindView(R.id.employerRegister_email_us_txtV_id)
    TextView email_us_txtV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as_employer);
        ButterKnife.bind(this);

    }
}
