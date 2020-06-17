package com.joehxblog.tagger.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.joehxblog.tagger.R;
import com.joehxblog.tagger.android.AffiliatePreference;
import com.joehxblog.tagger.android.IntentTagger;
import com.joehxblog.tagger.databinding.ReceiveActivityBinding;

import java.util.ArrayList;
import java.util.List;

public class ReceiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReceiveActivityBinding receiveBinding = DataBindingUtil.setContentView(this, R.layout.receive_activity);

        final Intent receiveIntent = getIntent();

        IntentTagger intentTagger = new IntentTagger(receiveIntent);

        receiveBinding.setTagged(intentTagger);

        AffiliatePreference prefs = new AffiliatePreference(this);

        final Button tagItButton = findViewById(R.id.tagItButton);

        tagItButton.setOnClickListener(v -> intentTagger.send(this, prefs.getDefaultTag()));

        Spinner spinner = findViewById(R.id.tag_spinner);
        List<String> tags = new ArrayList<>(prefs.getTags());
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tags);
        spinner.setAdapter(adapter);
        spinner.getSelectedItem().toString();
        int defaultPosition = adapter.getPosition(prefs.getDefaultTag());
        spinner.setSelection(defaultPosition);
    }
}
