package com.joehxblog.tagger;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {
    public static final int MAX_TAGS = 4;

    @Override
    public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        final PreferenceCategory affiliateTags = findPreference("amazon-affiliate-tags");

        for (int i = 0; i < MAX_TAGS; i++) {
            final Preference pref = createAssociateTagPreference(i);
            affiliateTags.addPreference(pref);
        }

        final PreferenceCategory history = findPreference("sharing-history");

        history.addPreference(createHistoryItem("Amazon Main Page", "https://www.amazon.com/", 0));
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
