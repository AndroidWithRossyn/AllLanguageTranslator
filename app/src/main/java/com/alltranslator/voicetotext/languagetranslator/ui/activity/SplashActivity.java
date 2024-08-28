package com.alltranslator.voicetotext.languagetranslator.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alltranslator.voicetotext.languagetranslator.R;
import com.alltranslator.voicetotext.languagetranslator.databinding.ActivitySplashBinding;
import com.alltranslator.voicetotext.languagetranslator.ads.FirebaseADHandlers.MyApplication;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);


        openActivity();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void openActivity() {

            new Handler().postDelayed(() -> {
                if (MyApplication.getPreferences().isFirstRun()) {
                    startActivity(new Intent(getApplicationContext(), OnBoardingActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

            }, 1600);


    }

}