package com.alltranslator.voicetotext.languagetranslator.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alltranslator.voicetotext.languagetranslator.R;
import com.alltranslator.voicetotext.languagetranslator.databinding.ActivityZoomBinding;


public class ZoomActivity extends AppCompatActivity {

    ActivityZoomBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_zoom);
//        Utility.setFullScreen(ZoomActivity.this);

        String data = getIntent().getStringExtra("data");

        binding.tvData.setText(data);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
            finish();

    }
}