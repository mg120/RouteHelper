package com.routehelperr.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.routehelperr.R;
import com.routehelperr.model.User;
import com.routehelperr.utils.DialogUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeLang extends AppCompatActivity {

    @BindView(R.id.english_image_id)
    ImageView english_imageV;
    @BindView(R.id.arabic_image_id)
    ImageView arabic_imageV;
    @BindView(R.id.yourCar_deserve_txtV_id)
    TextView logo_txtV;
    @BindView(R.id.changeLang_loading_progress)
    AVLoadingIndicatorView indicatorView;

    Intent intent;
    SharedPreferences prefs;
    String user_data;
    private Typeface custom_font;
    private DialogUtil dialogUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_lang);
        ButterKnife.bind(this);

        dialogUtil = new DialogUtil();
        custom_font = Typeface.createFromAsset(this.getAssets(), "fonts/DIN Next LT Arabic Bold.ttf");
        logo_txtV.setTypeface(custom_font);
        prefs = getSharedPreferences(LogIn.MY_PREFS_NAME, MODE_PRIVATE);
        user_data = prefs.getString("user_data", "");
    }

    @OnClick(R.id.english_image_id)
    public void setEnglishLang() {
        final ProgressDialog dialog = dialogUtil.showProgressDialog(this, getString(R.string.loading), false);
        setConfig("en", ChangeLang.this);

        // Retrive Gson Object from Shared Prefernces ....
        Gson gson = new Gson();
        User userModel = gson.fromJson(user_data, User.class);
        Intent intent = new Intent(ChangeLang.this, HomeActivity.class);
        intent.putExtra("user_data", userModel);
        intent.putExtra("lang", "en");
        dialog.dismiss();
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        finish();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
    }

    @OnClick(R.id.arabic_image_id)
    public void setArabicLang() {
        final ProgressDialog dialog = dialogUtil.showProgressDialog(this, getString(R.string.loading), false);
        setConfig("ar", ChangeLang.this);
        // Retrive Gson Object from Shared Prefernces ....
        Gson gson = new Gson();
        User userModel = gson.fromJson(user_data, User.class);
        Intent intent = new Intent(ChangeLang.this, HomeActivity.class);
        intent.putExtra("user_data", userModel);
        intent.putExtra("lang", "ar");
        dialog.dismiss();
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        finish();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
    }


    public void setConfig(String language, Context context) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }
}
