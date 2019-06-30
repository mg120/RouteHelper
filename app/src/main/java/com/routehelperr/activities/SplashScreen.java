package com.routehelperr.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.routehelperr.R;
import com.wang.avi.AVLoadingIndicatorView;

public class SplashScreen extends AppCompatActivity {

    Runnable runnable;
    Handler handler;
    TextView logo_txtV;

    AVLoadingIndicatorView loadingIndicatorView;
    private Typeface custom_font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loadingIndicatorView = findViewById(R.id.splash_loading_progress);
        logo_txtV = findViewById(R.id.logo_txtV_id);
        custom_font = Typeface.createFromAsset(this.getAssets(), "fonts/DIN Next LT Arabic Bold.ttf");
        logo_txtV.setTypeface(custom_font);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, ChangeLang.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
            }
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        loadingIndicatorView.hide();
    }
}
