package com.joehxblog.tagger.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.joehxblog.android.preference.LongClickablePlainOldPreference;
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

        pref.setPositiveButtonText(R.string.yes);
        pref.setNegativeButtonText(R.string.no);

        pref.setPositiveListener((d, i) ->  {
            this.preferences.clearHistory();
            history.removeAll();
            history.addPreference(createEmptyHistoryItem());
        });

        return pref;
    }

    private boolean askToDeleteHistoryItem(History history) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);

        Spanned message = Html.fromHtml("<b>" + history.getTitle() + "</b><br /><br />" + history.getUrl(), 0);

        builder.setMessage(message)
                .setTitle(R.string.confirm_delete_history_item)
                .setPositiveButton(R.string.yes, (d, i) -> deleteHistoryItem(history))
                .setNegativeButton(R.string.no, (d, i) -> {})
                .show();

        return true;
    }

    private void deleteHistoryItem(History history) {
        preferences.deleteHistoryItem(history);
        create();
    }

    private Preference createEmptyHistoryItem() {
        final Preference pref = new Preference(this.context);
        pref.setTitle(R.string.no_history_yet);
        pref.setSummary(R.string.no_history_yet);

        return pref;
    }

    private Preference createHistoryItem(final History history) {
        final LongClickablePlainOldPreference pref = new LongClickablePlainOldPreference(this.context);
        pref.setTitle(history.getTitle());
        pref.setSummary(history.getUrl());

        final Intent intent = new Intent(this.context, ReceiveActivity.class);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, history.getTitle());
        intent.putExtra(Intent.EXTRA_TEXT, history.getUrl());
        intent.putExtra(ReceiveActivity.SAVE, false);

        pref.setIntent(intent);

        pref.setLongClickListener(v -> askToDeleteHistoryItem(history));

        return pref;
    }
}
