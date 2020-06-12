package com.joehxblog.tagger.android;

import android.content.Context;
import android.content.Intent;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.joehxblog.android.preference.OkCancelDialogPreference;
import com.joehxblog.tagger.History;
import com.joehxblog.tagger.R;
import com.joehxblog.tagger.android.activity.ReceiveActivity;

public class HistoryPreferenceGroup {

    private final PreferenceCategory history;
    private final HistoryPreference preferences;
    private final Context context;

    public HistoryPreferenceGroup(final Context context, final PreferenceCategory history) {
        this.context = context;
        this.history = history;
        this.preferences = new HistoryPreference(context);
    }

    public void create() {
        this.history.removeAll();

        if (this.preferences.getHistory().isEmpty()) {
            this.history.addPreference(createEmptyHistoryItem());
        } else {
            this.history.addPreference(createClearHistoryItem(this.history));
            this.preferences.getHistory()
                    .stream()
                    .sorted()
                    .map(this::createHistoryItem)
                    .forEach(this.history::addPreference);
        }
    }

    private Preference createClearHistoryItem(final PreferenceCategory history) {
        final OkCancelDialogPreference pref = new OkCancelDialogPreference(this.context);
        pref.setTitle(R.string.clear_history);
        pref.setDialogTitle(R.string.confirm_clear_history);
        pref.setSummary(R.string.clear_history);

        pref.setPositiveListener((d, i) ->  {
            this.preferences.clearHistory();
            history.removeAll();
            history.addPreference(createEmptyHistoryItem());
        });

        return pref;
    }

    private Preference createEmptyHistoryItem() {
        final Preference pref = new Preference(this.context);
        pref.setTitle(R.string.no_history_yet);
        pref.setSummary(R.string.no_history_yet);

        return pref;
    }

    private Preference createHistoryItem(final History history) {
        final Preference pref = new Preference(this.context);
        pref.setTitle(history.getTitle());
        pref.setSummary(history.getUrl());

        final Intent intent = new Intent(this.context, ReceiveActivity.class);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, history.getTitle());
        intent.putExtra(Intent.EXTRA_TEXT, history.getUrl());

        pref.setIntent(intent);

        return pref;
    }
}