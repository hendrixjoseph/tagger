package com.joehxblog.tagger.android;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.joehxblog.tagger.R;

import java.util.ArrayList;
import java.util.List;

import static com.joehxblog.tagger.android.SettingsFragment.MAX_TAGS;

public class ReceiveActivity extends AppCompatActivity {

    private TaggerPreferences prefs;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        prefs = new TaggerPreferences(this);

        populateSpinner();

        final Intent receiveIntent = getIntent();

        IntentTagger intentTagger = new IntentTagger(receiveIntent);

        final Button tagItButton = this.findViewById(R.id.tagItButton);

        tagItButton.setOnClickListener(v -> intentTagger.send(this, this.getTag()));
    }

    private String getTag() {
        return prefs.getDefaultTag();
    }

    private void populateSpinner() {

        final List<String> tags = new ArrayList<>();

        for (int i = 0; i < MAX_TAGS; i++) {
            final String tag = prefs.getTag(i);

            if (!tag.isEmpty()) {
                tags.add(tag);
            }
        }

        final SpinnerAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tags);

        final Spinner spinner = this.findViewById(R.id.tag_spinner);
        spinner.setAdapter(adapter);
    }

    private void clickedTagIt(final Intent sendIntent) {
        final Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private void setUrlTextView(final Intent receiveIntent) {
        final String subject = receiveIntent.getStringExtra(Intent.EXTRA_SUBJECT);
        final String text = receiveIntent.getStringExtra(Intent.EXTRA_TEXT);

        setUrlTextView(subject, text);
    }

    private void setUrlTextView(final String subject, final String text) {
        final StringBuilder sb = new StringBuilder();

        if (subject != null && !subject.isEmpty()) {
            sb.append("<h1>").append(subject).append("</h1>");
        }

        if (text != null && !text.isEmpty()) {
            sb.append("<p>").append(text).append("</p>");
        }

        final TextView textView = this.<TextView>findViewById(R.id.urlTextView);
        textView.setText(Html.fromHtml(sb.toString()));
    }
}
