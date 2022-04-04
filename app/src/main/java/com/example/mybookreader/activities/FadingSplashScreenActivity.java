package com.example.mybookreader.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.mybookreader.R;

public class FadingSplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splast_screen);
        getSupportActionBar().hide();
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        TextView tv_app_name = findViewById(R.id.spl_scr_app_name), tv_author = findViewById(R.id.spl_scr_author);
        Animation fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        tv_app_name.setAnimation(fade_in);
        tv_author.setAnimation(fade_in);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(FadingSplashScreenActivity.this, MainScreenActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.slide_out_right);
                finish();
            }
        }, 2000);
    }
}