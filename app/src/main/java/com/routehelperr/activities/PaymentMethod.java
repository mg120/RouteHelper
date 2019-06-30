package com.routehelperr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.routehelperr.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentMethod extends AppCompatActivity {

    @BindView(R.id.visaPayment_back_txtV_id)
    TextView visaPayment_back_txtV;
    @BindView(R.id.total_price_txtV)
    TextView total_price_txtV;

    private String cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        ButterKnife.bind(this);

        if (getIntent().hasExtra("cost")) {
            cost = getIntent().getStringExtra("cost");
            total_price_txtV.setText(cost);
        }
    }

    @OnClick(R.id.visaPayment_back_txtV_id)
    void back_clicked() {
        finish();
    }

    @OnClick(R.id.masterCard_imageV_id)
    void masterCard_Clicked() {
        Toast.makeText(this, getString(R.string.masterCard_clicked), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.visa_imageV_id)
    void visaCard_Clicked() {
        Toast.makeText(this, getString(R.string.visaCard_clicked), Toast.LENGTH_SHORT).show();
    }
}
