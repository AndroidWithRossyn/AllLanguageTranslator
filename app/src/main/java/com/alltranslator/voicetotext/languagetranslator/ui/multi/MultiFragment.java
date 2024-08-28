package com.alltranslator.voicetotext.languagetranslator.ui.multi;

import static com.alltranslator.voicetotext.languagetranslator.utils.Const.INPUT;
import static com.alltranslator.voicetotext.languagetranslator.utils.Const.IN_SELECTED_LANGUAGE;
import static com.alltranslator.voicetotext.languagetranslator.utils.Const.languagesModelList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.alltranslator.voicetotext.languagetranslator.R;
import com.alltranslator.voicetotext.languagetranslator.databinding.FragmentMultiBinding;
import com.alltranslator.voicetotext.languagetranslator.adapter.LanguageEditAdapter;

import com.alltranslator.voicetotext.languagetranslator.ui.activity.DashboardActivity;
import com.alltranslator.voicetotext.languagetranslator.ui.fragment.LanguageFragment;

public class MultiFragment extends Fragment {

    FragmentMultiBinding binding;
    LanguageEditAdapter adapter;
    DashboardActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_multi, container, false);

        activity = (DashboardActivity) requireActivity();


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();



        binding.btnInput.setText(IN_SELECTED_LANGUAGE.getLanguageName());

        binding.btnInput.setOnClickListener(view -> {
                activity.openFragment(new LanguageFragment(new MultiFragment(), INPUT));

        });

        adapter = new LanguageEditAdapter(activity);
        binding.langRecyclerView.setAdapter(adapter);
        adapter.submitList(languagesModelList);

        binding.btnBack.setOnClickListener(view -> activity.openFirstFragment());

        activity.getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                    activity.finish();

            }
        });


        binding.btnSettings.setOnClickListener(view -> {
                activity.drawerOpen();

        });
        binding.btnStart.setOnClickListener(view -> {
            activity.openFragment(new MultiResultFragment(new MultiFragment(), IN_SELECTED_LANGUAGE));
        });

    }


}