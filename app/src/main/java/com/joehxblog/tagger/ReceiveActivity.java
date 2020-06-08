package com.joehxblog.tagger;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static com.joehxblog.tagger.SettingsFragment.MAX_TAGS;

public class ReceiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        populateSpinner();

        final Intent receiveIntent = getIntent();

        final Intent sendIntent = tag(receiveIntent);

        final Button tagItButton = this.findViewById(R.id.tagItButton);

        tagItButton.setOnClickListener(v -> clickedTagIt(sendIntent));
    }

    private void populateSpinner() {
        final TaggerPreferences prefs = new TaggerPreferences(this);

        final List<String> tags = new ArrayList<>();

        for (int i = 0; i < MAX_TAGS; i++) {
            final String tag = prefs.getTag(i);

            if (!tag.isEmpty()) {
                tags.add(tag);
            }
        }

        final SpinnerAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tags);
        //SpinnerAdapter adapter = new ArrayAdapter(this, R.id.tag_spinner, tags);

        final Spinner spinner = this.findViewById(R.id.tag_spinner);
        spinner.setAdapter(adapter);
    }

    private void clickedTagIt(final Intent sendIntent) {
        final Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private Intent tag(final Intent receiveIntent) {
        final Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtras(receiveIntent);
        sendIntent.setType(receiveIntent.getType());

        if (receiveIntent.getCategories() != null) {
            sendIntent.getCategories().addAll(receiveIntent.getCategories());
        }

        final TaggerPreferences prefs = new TaggerPreferences(this);

        final String savedTag = prefs.getDefaultTag();

        final AmazonTagger tagger = new AmazonTagger(savedTag);

        for (final String key : receiveIntent.getExtras().keySet()) {
            final Object extra = receiveIntent.getExtras().get(key);

            if (extra instanceof String) {
                final String string = (String) extra;
                final String tag = tagger.tag(string);
                sendIntent.putExtra(key, tag);
            }
        }

        return sendIntent;
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
