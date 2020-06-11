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

    public void send(Activity activity, String tag) {
        Intent sendIntent = getTaggedSendIntent(tag);

        HistoryPreference prefs = new HistoryPreference(activity);
        prefs.createHistoryItem(subject, text);

        activity.startActivity(sendIntent);
    }

    private Intent getTaggedSendIntent(String tag) {
        final Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtras(receiveIntent);
        sendIntent.setType(receiveIntent.getType());

        if (receiveIntent.getCategories() != null) {
            sendIntent.getCategories().addAll(receiveIntent.getCategories());
        }

        final AmazonTagger tagger = new AmazonTagger(tag);

        for (final String key : receiveIntent.getExtras().keySet()) {
            final Object extra = receiveIntent.getExtras().get(key);

            if (extra instanceof String) {
                final String string = (String) extra;
                final String tagged = tagger.tag(string);
                sendIntent.putExtra(key, tagged);
            }
        }

        return sendIntent;
    }
}
