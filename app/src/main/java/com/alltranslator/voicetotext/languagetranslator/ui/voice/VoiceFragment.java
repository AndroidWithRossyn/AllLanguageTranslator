package com.alltranslator.voicetotext.languagetranslator.ui.voice;

import static com.alltranslator.voicetotext.languagetranslator.utils.Const.INPUT;
import static com.alltranslator.voicetotext.languagetranslator.utils.Const.IN_SELECTED_LANGUAGE;
import static com.alltranslator.voicetotext.languagetranslator.utils.Const.OUTPUT;
import static com.alltranslator.voicetotext.languagetranslator.utils.Const.OUT_SELECTED_LANGUAGE;
import static com.alltranslator.voicetotext.languagetranslator.utils.Const.SELECTED_LANGUAGE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.alltranslator.voicetotext.languagetranslator.R;
import com.alltranslator.voicetotext.languagetranslator.databinding.FragmentVoiceBinding;
import com.alltranslator.voicetotext.languagetranslator.ui.activity.DashboardActivity;
import com.alltranslator.voicetotext.languagetranslator.ui.fragment.LanguageFragment;

public class VoiceFragment extends Fragment {

    FragmentVoiceBinding binding;
    DashboardActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_voice, container, false);

        activity = (DashboardActivity) requireActivity();


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.btnInput.setText(IN_SELECTED_LANGUAGE.getLanguageName());
        binding.btnOutput.setText(OUT_SELECTED_LANGUAGE.getLanguageName());

        binding.btnSwap.setOnClickListener(view -> {
            SELECTED_LANGUAGE = IN_SELECTED_LANGUAGE;
            IN_SELECTED_LANGUAGE = OUT_SELECTED_LANGUAGE;
            OUT_SELECTED_LANGUAGE = SELECTED_LANGUAGE;

            binding.btnInput.setText(IN_SELECTED_LANGUAGE.getLanguageName());
            binding.btnOutput.setText(OUT_SELECTED_LANGUAGE.getLanguageName());
            SELECTED_LANGUAGE = null;
        });

        binding.btnBack.setOnClickListener(view -> activity.openFirstFragment());

        activity.getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                    activity.finish();

//                activity.openFirstFragment();
            }
        });


        binding.btnSettings.setOnClickListener(view -> {

                activity.drawerOpen();


        });

        binding.btnInput.setOnClickListener(view -> {
                activity.openFragment(new LanguageFragment(new VoiceFragment(), INPUT));

        });

        binding.btnOutput.setOnClickListener(view -> {
                activity.openFragment(new LanguageFragment(new VoiceFragment(), OUTPUT));


        });

        binding.btnStart.setOnClickListener(view -> {
                activity.openFragment(new RecordVoiceFragment(new VoiceFragment(), IN_SELECTED_LANGUAGE, OUT_SELECTED_LANGUAGE));

        });

    }
}