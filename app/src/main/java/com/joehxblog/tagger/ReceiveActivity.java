package com.joehxblog.tagger;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.joehxblog.tagger.SettingsFragment.MAX_TAGS;

public class ReceiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        populateSpinner();

        Intent receiveIntent = getIntent();

        final Intent sendIntent = tag(receiveIntent);

        Button tagItButton = this.<Button>findViewById(R.id.tagItButton);

        tagItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedTagIt(sendIntent);
            }
        });
    }

    private void populateSpinner() {
        TaggerPreferences prefs = new TaggerPreferences(this);

        List<String> tags = new ArrayList<>();

        for (int i = 0; i < MAX_TAGS; i++) {
            String tag = prefs.getTag(i);

            if (!tag.isEmpty()) {
                tags.add(tag);
            }
        }

        //SpinnerAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tags);
        SpinnerAdapter adapter = new ArrayAdapter(this, R.id.tag_spinner, tags);

        Spinner spinner = this.<Spinner>findViewById(R.id.tag_spinner);
        spinner.setAdapter(adapter);
    }



    private void clickedTagIt(Intent sendIntent) {
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private Intent tag(Intent receiveIntent) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtras(receiveIntent);
        sendIntent.setType(receiveIntent.getType());

        if (receiveIntent.getCategories() != null) {
            sendIntent.getCategories().addAll(receiveIntent.getCategories());
        }

        TaggerPreferences prefs = new TaggerPreferences(this);

        String savedTag = prefs.getDefaultTag();

        AmazonTagger tagger = new AmazonTagger(savedTag);

        for (String key : receiveIntent.getExtras().keySet()) {
            Object extra = receiveIntent.getExtras().get(key);

            if (extra instanceof String) {
                String string = (String) extra;
                String tag = tagger.tag(string);
                sendIntent.putExtra(key, tag);
            }
        }

        return sendIntent;
    }

    private void setUrlTextView(Intent receiveIntent) {
        String subject = receiveIntent.getStringExtra(Intent.EXTRA_SUBJECT);
        String text = receiveIntent.getStringExtra(Intent.EXTRA_TEXT);

        setUrlTextView(subject, text);
    }

    private void setUrlTextView(String subject, String text) {
        StringBuilder sb = new StringBuilder();

        if (subject != null && !subject.isEmpty()) {
            sb.append("<h1>").append(subject).append("</h1>");
        }

        if (text != null && !text.isEmpty()) {
            sb.append("<p>").append(text).append("</p>");
        }

        TextView textView = this.<TextView>findViewById(R.id.urlTextView);
        textView.setText(Html.fromHtml(sb.toString()));
    }
}
