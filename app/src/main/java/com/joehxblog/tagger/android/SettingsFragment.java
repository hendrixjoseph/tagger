package com.joehxblog.tagger.android;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;

import com.joehxblog.tagger.History;
import com.joehxblog.tagger.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    public static final int MAX_TAGS = 4;

    private TaggerPreferences preferences;

    @Override
    public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        preferences = new TaggerPreferences(this.getContext());

        final PreferenceCategory affiliateTags = findPreference("amazon-affiliate-tags");

        for (int i = 0; i < MAX_TAGS; i++) {
            final Preference pref = createAssociateTagPreference(i);
            affiliateTags.addPreference(pref);
        }
    }

    public void onStart() {
        super.onStart();

        final PreferenceCategory history = findPreference("sharing-history");
        history.removeAll();

        if (preferences.getHistory().isEmpty()) {
            final Preference pref = new Preference(getContext());
            pref.setTitle("No history yet.");
            pref.setSummary("No history yet.");
            //pref.setKey("history-" + key);

            history.addPreference(pref);

        } else {
            preferences.getHistory()
                    .stream()
                    .map(this::createHistoryItem)
                    .forEach(history::addPreference);
        }
    }

    private Preference createHistoryItem(History history) {
        final Preference pref = new Preference(getContext());
        pref.setTitle(history.getTitle());
        pref.setSummary(history.getUrl());
        //pref.setKey("history-" + key);

        final Intent intent = new Intent(getContext(), ReceiveActivity.class);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, history.getTitle());
        intent.putExtra(Intent.EXTRA_TEXT, history.getUrl());

        pref.setIntent(intent);

        return pref;
    }

    private Preference createHistoryItem(final String title, final String url, final int key) {
        final Preference pref = new Preference(getContext());
        pref.setTitle(title);
        pref.setSummary(url);
        pref.setKey("history-" + key);

        final Intent intent = new Intent(getContext(), ReceiveActivity.class);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, url);

        pref.setIntent(intent);

        return pref;
    }

    private Preference createAssociateTagPreference(final int key) {
        final EditTextPreference pref = new EditTextPreference(getContext());
        pref.setTitle(R.string.tracking_id);
        pref.setDialogTitle(R.string.set_tracking_id);
        pref.setKey("tag-" + key );

        pref.setSummaryProvider((Preference.SummaryProvider<EditTextPreference>) preference -> {
            final String tag = preference.getText();
            if (TextUtils.isEmpty(tag)){
                return "Not set";
            }
            return "?tag=" + tag;
        });

        return pref;
    }
}
