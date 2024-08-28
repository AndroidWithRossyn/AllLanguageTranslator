package com.alltranslator.voicetotext.languagetranslator.adapter;

import static com.alltranslator.voicetotext.languagetranslator.utils.Utility.copyToClipboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.alltranslator.voicetotext.languagetranslator.databinding.ViewTranslateListBinding;
import com.alltranslator.voicetotext.languagetranslator.models.LanguagesModel;
import com.alltranslator.voicetotext.languagetranslator.utils.TTS;

import java.util.Objects;

public class TranslateResultAdapter extends ListAdapter<LanguagesModel, TranslateResultAdapter.ViewHolder> {

    Context context;
    boolean isZoomed = false;
    TTS tts;

    public TranslateResultAdapter() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewTranslateListBinding binding = ViewTranslateListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LanguagesModel model = getItem(position);

        tts = new TTS(context, model.getLanguageCode());

        holder.binding.tvToLanguage.setText(model.getLanguageName());
        holder.binding.tvResult.setText(model.getTranslateResult());

            holder.binding.adsView.setVisibility(View.GONE);


        holder.binding.btnCopy.setOnClickListener(view -> {
            String textToCopy = holder.binding.tvResult.getText().toString().trim();
            copyToClipboard(context, textToCopy);
        });

        holder.binding.btnZoom.setOnClickListener(view -> {
            if (isZoomed) {
                holder.binding.tvResult.setTextSize(14);
            } else {
                holder.binding.tvResult.setTextSize(24);
            }
            isZoomed = !isZoomed;
        });

        holder.binding.btnShare.setOnClickListener(view -> {
            String textToShare = holder.binding.tvResult.getText().toString().trim();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_TEXT, textToShare);
            intent.setType("text/plain");
            context.startActivity(intent);
        });

        holder.binding.btnSpeaker.setOnClickListener(view -> {
            String textToSpeak = holder.binding.tvResult.getText().toString().trim();
            tts.speakText(textToSpeak);
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewTranslateListBinding binding;

        public ViewHolder(@NonNull ViewTranslateListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    static DiffUtil.ItemCallback<LanguagesModel> diffCallback = new DiffUtil.ItemCallback<LanguagesModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull LanguagesModel oldItem, @NonNull LanguagesModel newItem) {
            return Objects.equals(oldItem.getLanguageName(), newItem.getLanguageName()) || Objects.equals(oldItem.getTranslateResult(), newItem.getTranslateResult());
        }

        @Override
        public boolean areContentsTheSame(@NonNull LanguagesModel oldItem, @NonNull LanguagesModel newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };

}
