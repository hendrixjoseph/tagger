package com.joehxblog.tagger.android;

import android.app.Activity;
import android.content.Intent;

import com.joehxblog.tagger.AmazonTagger;

public class IntentTagger {

    private final String subject;
    private final String text;
    private final Intent receiveIntent;

    public IntentTagger(final Intent receiveIntent) {
        this.subject = receiveIntent.getStringExtra(Intent.EXTRA_SUBJECT);
        this.text = receiveIntent.getStringExtra(Intent.EXTRA_TEXT);
        this.receiveIntent = receiveIntent;
    }

    public void send(final Activity activity, final String tag) {
        final Intent sendIntent = getTaggedSendIntent(tag);

        final HistoryPreference prefs = new HistoryPreference(activity);
        prefs.createHistoryItem(this.subject, this.text);

        activity.startActivity(sendIntent);
    }

    public String getSubject() {
        return this.subject;
    }

    public String getText() {
        return this.text;
    }

    private Intent getTaggedSendIntent(final String tag) {
        final Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtras(this.receiveIntent);
        sendIntent.setType(this.receiveIntent.getType());

        if (this.receiveIntent.getCategories() != null) {
            sendIntent.getCategories().addAll(this.receiveIntent.getCategories());
        }

        final AmazonTagger tagger = new AmazonTagger(tag);

        for (final String key : this.receiveIntent.getExtras().keySet()) {
            final Object extra = this.receiveIntent.getExtras().get(key);

            if (extra instanceof String) {
                final String string = (String) extra;
                final String tagged = tagger.tag(string);
                sendIntent.putExtra(key, tagged);
            }
        }

        return sendIntent;
    }
}
