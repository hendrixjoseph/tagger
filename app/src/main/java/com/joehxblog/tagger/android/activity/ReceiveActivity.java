package com.joehxblog.tagger.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.joehxblog.tagger.R;
import com.joehxblog.tagger.android.AffiliatePreference;
import com.joehxblog.tagger.android.IntentTagger;
import com.joehxblog.tagger.databinding.ReceiveActivityBinding;

public class ReceiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final IntentTagger intentTagger = initIntentTagger();
        final Spinner spinner = initSpinner();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intentTagger.setTag(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Button tagItButton = findViewById(R.id.tagItButton);
        tagItButton.setOnClickListener(v -> intentTagger.send(this, spinner.getSelectedItem().toString()));
    }

    private IntentTagger initIntentTagger() {
        final ReceiveActivityBinding receiveBinding = DataBindingUtil.setContentView(this, R.layout.receive_activity);
        receiveBinding.setLifecycleOwner(this);


        final Intent receiveIntent = getIntent();
        final IntentTagger intentTagger = new IntentTagger(receiveIntent);
        receiveBinding.setTagged(intentTagger);

        return intentTagger;
    }

    private Spinner initSpinner() {
        final AffiliatePreference prefs = new AffiliatePreference(this);

        final Spinner spinner = findViewById(R.id.tag_spinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, prefs.getTags());
        adapter.add("Add new tag.");
        spinner.setAdapter(adapter);

        final int defaultPosition = adapter.getPosition(prefs.getDefaultTag());
        spinner.setSelection(defaultPosition);

        return spinner;
    }
}
