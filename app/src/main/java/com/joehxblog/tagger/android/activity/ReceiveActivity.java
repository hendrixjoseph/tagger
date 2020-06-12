package com.joehxblog.tagger.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.joehxblog.tagger.R;
import com.joehxblog.tagger.android.AffiliatePreference;
import com.joehxblog.tagger.android.IntentTagger;
import com.joehxblog.tagger.databinding.ReceiveActivityBinding;

public class ReceiveActivity extends AppCompatActivity {

    private AffiliatePreference prefs;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReceiveActivityBinding receiveBinding = DataBindingUtil.setContentView(this, R.layout.receive_activity);

        prefs = new AffiliatePreference(this);

        final Intent receiveIntent = getIntent();

        IntentTagger intentTagger = new IntentTagger(receiveIntent);

        receiveBinding.setTagged(intentTagger);

        final Button tagItButton = this.findViewById(R.id.tagItButton);

        tagItButton.setOnClickListener(v -> intentTagger.send(this, this.getTag()));
    }

    private String getTag() {
        return prefs.getDefaultTag();
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
