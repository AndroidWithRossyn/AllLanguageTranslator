package com.alltranslator.voicetotext.languagetranslator.ui.multi;

import static com.alltranslator.voicetotext.languagetranslator.utils.Utility.getMultiTranslate;
import static com.alltranslator.voicetotext.languagetranslator.utils.Utility.pasteFromClipboard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.alltranslator.voicetotext.languagetranslator.R;
import com.alltranslator.voicetotext.languagetranslator.databinding.FragmentMultiResultBinding;
import com.alltranslator.voicetotext.languagetranslator.adapter.TranslateResultAdapter;

import com.alltranslator.voicetotext.languagetranslator.models.LanguagesModel;
import com.alltranslator.voicetotext.languagetranslator.ui.activity.DashboardActivity;

public class MultiResultFragment extends Fragment {

    FragmentMultiResultBinding binding;
    TranslateResultAdapter adapter;
    DashboardActivity activity;
    Fragment fragment;
    LanguagesModel input;
    boolean isPaused = false;

    public MultiResultFragment(Fragment fragment, LanguagesModel inSelectedLanguage) {
        this.fragment = fragment;
        input = inSelectedLanguage;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_multi_result, container, false);

        activity = (DashboardActivity) requireActivity();


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();


        binding.btnBack.setOnClickListener(view -> {
                activity.openFragment(fragment);

        });
        activity.getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                    activity.openFragment(fragment);


            }
        });

        binding.btnSettings.setOnClickListener(view -> activity.drawerOpen());

        binding.tvFromLanguage.setText(input.getLanguageName());

        binding.etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    binding.btnTranslate.setBackgroundResource(R.drawable.btn_background_enabled);
                } else
                    binding.btnTranslate.setBackgroundResource(R.drawable.btn_background_disabled);
            }
        });

        binding.btnTranslate.setOnClickListener(view -> {
            String text = binding.etText.getText().toString().trim();

            if (TextUtils.isEmpty(text)){
                Toast.makeText(activity, getString(R.string.please_type_something_to_translate), Toast.LENGTH_SHORT).show();
                return;
            }

            binding.btnTranslate.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
            setAdapter(text);

        });

        binding.btnPaste.setOnClickListener(view -> {
            String text = pasteFromClipboard(activity);
            binding.etText.setText(text);
        });

    }

    private void setAdapter(String text) {
        adapter = new TranslateResultAdapter();
        binding.resultRecyclerView.setAdapter(adapter);
        binding.resultRecyclerView.setVisibility(View.VISIBLE);
        getMultiTranslate(input, text).observe(getViewLifecycleOwner(), data->{
            if (data.size() > 0){
                adapter.submitList(data);
                binding.btnTranslate.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            }
        });

    }


}