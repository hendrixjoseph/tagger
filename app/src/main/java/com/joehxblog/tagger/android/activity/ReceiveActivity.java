package com.joehxblog.tagger.android.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.joehxblog.tagger.R;
import com.joehxblog.tagger.android.AffiliatePreference;
import com.joehxblog.tagger.android.IntentTagger;
import com.joehxblog.tagger.databinding.ReceiveActivityBinding;

public class ReceiveActivity extends AppCompatActivity {

    public static final String SAVE = "com.joehxblog.save";

    private IntentTagger intentTagger;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.intentTagger = initIntentTagger();
        this.spinner = initSpinner();

        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                final String addNewTag = getResources().getString(R.string.add_new_tag);
                final String item = parent.getSelectedItem().toString();

                if (addNewTag.equals(item)) {
                    addNewTag();
                } else {
                    ReceiveActivity.this.intentTagger.setTag(item);
                }
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {

            }
        });

        final Button tagItButton = findViewById(R.id.tagItButton);
        tagItButton.setOnClickListener(v -> this.intentTagger.send(this));
    }

    private void addNewTag() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setFocusableInTouchMode(true);
        input.setFocusable(true);

        final AlertDialog dialog = builder.setView(input)
                .setTitle(R.string.add_tracking_id)
                .setPositiveButton(R.string.ok, (d, i) -> addNewTag(input.getText().toString()))
                .setNegativeButton(R.string.cancel, (d, i) -> setTag(this.intentTagger.getTag()))
                .create();

        dialog.setOnShowListener(d -> {
            final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
        });

        dialog.show();
    }

    private void addNewTag(final String tag) {
        final AffiliatePreference pref = new AffiliatePreference(this);
        pref.addTag(tag);

        this.adapter.add(tag);
        this.adapter.notifyDataSetChanged();
        setTag(tag);
    }

    private void setTag(final String tag) {
        final int newPosition = this.adapter.getPosition(tag);
        this.spinner.setSelection(newPosition);
        this.intentTagger.setTag(tag);
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
        this.adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, prefs.getTags());
        this.adapter.add(this.getResources().getString(R.string.add_new_tag));
        spinner.setAdapter(this.adapter);

        final int defaultPosition = this.adapter.getPosition(prefs.getDefaultTag());
        spinner.setSelection(defaultPosition);

        return spinner;
    }
}
