package com.alltranslator.voicetotext.languagetranslator.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;


import com.alltranslator.voicetotext.languagetranslator.BuildConfig;
import com.alltranslator.voicetotext.languagetranslator.R;
import com.alltranslator.voicetotext.languagetranslator.databinding.ActivityMainBinding;
import com.alltranslator.voicetotext.languagetranslator.ads.FirebaseADHandlers.MyApplication;

import com.alltranslator.voicetotext.languagetranslator.models.LanguagesModel;
import com.google.cloud.translate.Language;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);





        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    protected void onResume() {
        super.onResume();


        binding.navView.bringToFront();

        binding.navView.setNavigationItemSelectedListener(item -> {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        setLanguages();

        binding.btnMenu.setOnClickListener(view -> drawerOpen());

        binding.btnTextTranslator.setOnClickListener(view -> {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class)
                        .putExtra("page", 0));


        });

        binding.btnVoiceTranslator.setOnClickListener(view -> {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class)
                        .putExtra("page", 1));

        });

        binding.btnPhotoTranslator.setOnClickListener(view -> {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class)
                        .putExtra("page", 2));

        });

        binding.btnDictionary.setOnClickListener(view -> {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class)
                        .putExtra("page", 3));

        });

        binding.btnMultiTranslator.setOnClickListener(view -> {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class)
                        .putExtra("page", 4));



        });

        binding.btnShare.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name) + " App Invitation");
            intent.putExtra(Intent.EXTRA_TEXT, "Say goodbye to voicetotext barriers! Communicate effortlessly in real-time by voice or text, with translations available in multiple languages.\n" +
                    "Enable seamless translation across multiple languages while also serving as your handy dictionary companion.\n" +
                    "Download the " + getString(R.string.app_name) + " app now!" +
                    "\nhttps://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
            intent.setType("text/plain");
            startActivity(intent);
        });
       /* if (isPaused) {
            AdUtils.showAdIfAvailable(MainActivity.this, new AppInterfaces.AppOpenADInterface() {
                @Override
                public void appOpenAdState(boolean state_load) {
                    isPaused = false;
                }
            });
        }*/


    }

    private void setLanguages() {
        try {
            int visitCount = MyApplication.getPreferences().getUpdate();
            if (visitCount == 0 || visitCount == 24) {
                MyApplication.getPreferences().removeLanguages();
                List<LanguagesModel> list = new ArrayList<>();
                AsyncTask.execute(() -> {
                    List<Language> lang = MyApplication.getTranslate().listSupportedLanguages();
                    for (Language language : lang)
                        list.add(new LanguagesModel(language.getName(), language.getCode(), false));
                    list.sort((t1, t2) -> t1.getLanguageName().toLowerCase().compareToIgnoreCase(t2.getLanguageName().toLowerCase()));
                    MyApplication.getPreferences().setLanguages(list);
                    MyApplication.getPreferences().setUpdate(1);
                });
            } else
                MyApplication.getPreferences().setUpdate(visitCount + 1);
        }catch (Exception e){

        }
    }


    public void drawerOpen() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        else
            binding.drawerLayout.openDrawer(GravityCompat.START);
    }

    public void closeDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        else {

            openCloseDialog();

        }
    }

    private void openCloseDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to close this app?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            finishAffinity();
            System.exit(0);
        });
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create().show();
    }
 /*   @Override
    public void onPause() {
        super.onPause();
        isPaused = true;
    }*/


}