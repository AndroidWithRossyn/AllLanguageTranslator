package com.alltranslator.voicetotext.languagetranslator.ui.activity;

import static com.alltranslator.voicetotext.languagetranslator.utils.Const.IN_SELECTED_LANGUAGE;
import static com.alltranslator.voicetotext.languagetranslator.utils.Const.OUT_SELECTED_LANGUAGE;
import static com.alltranslator.voicetotext.languagetranslator.utils.Const.englishModel;
import static com.alltranslator.voicetotext.languagetranslator.utils.Const.hindiModel;
import static com.alltranslator.voicetotext.languagetranslator.utils.Utility.addLanguages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.alltranslator.voicetotext.languagetranslator.R;
import com.alltranslator.voicetotext.languagetranslator.databinding.ActivityDashboardBinding;
import com.alltranslator.voicetotext.languagetranslator.ui.camera.CameraFragment;
import com.alltranslator.voicetotext.languagetranslator.ui.dictionary.DictionaryFragment;
import com.alltranslator.voicetotext.languagetranslator.ui.multi.MultiFragment;
import com.alltranslator.voicetotext.languagetranslator.ui.text.TextFragment;
import com.alltranslator.voicetotext.languagetranslator.ui.voice.VoiceFragment;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;
    ActionBarDrawerToggle toggle;
    int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        page = getIntent().getIntExtra("page", 0);

        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.bottomNav.getMenu().getItem(page).setChecked(true);

        if (page == 0) {
            openFirstFragment();
        }
        if (page == 1) {
            openFragment(new VoiceFragment());
        }
        if (page == 2) {
            openFragment(new CameraFragment());
        }
        if (page == 3) {
            openFragment(new DictionaryFragment());
        }
        if (page == 4) {
            openFragment(new MultiFragment());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


        addLanguages(hindiModel);

        binding.navView.bringToFront();

        binding.navView.setNavigationItemSelectedListener(item -> {
            binding.drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        });

        IN_SELECTED_LANGUAGE = englishModel;
        OUT_SELECTED_LANGUAGE = hindiModel;

        binding.bottomNav.setOnItemSelectedListener(item -> {

            IN_SELECTED_LANGUAGE = englishModel;
            OUT_SELECTED_LANGUAGE = hindiModel;

            switch (item.getItemId()) {
                case R.id.nav_text:
                        openFragment(new TextFragment());

                    break;


                case R.id.nav_voice:
                        openFragment(new VoiceFragment());


                    break;

                case R.id.nav_camera:
                        openFragment(new CameraFragment());

                    break;

                case R.id.nav_dictionary:
                        openFragment(new DictionaryFragment());


                    break;

                case R.id.nav_multi:
                        openFragment(new MultiFragment());


                    break;

            }
            return true;
        });

    }


    public void hideBottom() {
        binding.bottomNav.setVisibility(View.GONE);
    }

    public void visibleBottom() {
        binding.bottomNav.setVisibility(View.VISIBLE);
    }

    public void openFragment(Fragment fragment) {
//        binding.viewContainer.removeViewAt(0);
//        AdUtils.showInterstitialAd(this, state_load -> {
        getSupportFragmentManager().beginTransaction().replace(R.id.view_container, fragment).commit();
//        });
    }

    public void drawerOpen() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.END))
            binding.drawerLayout.closeDrawer(GravityCompat.END);
        else
            binding.drawerLayout.openDrawer(GravityCompat.END);
    }

    public void closeDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.END))
            binding.drawerLayout.closeDrawer(GravityCompat.END);
    }

    @Override
    public void onBackPressed() {


                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
    }

    public void openFirstFragment() {
//        AdUtils.showInterstitialAd(this, state_load -> {
        getSupportFragmentManager().beginTransaction().replace(R.id.view_container, new TextFragment()).commit();
        binding.bottomNav.getMenu().getItem(0).setChecked(true);
//        });
    }
}