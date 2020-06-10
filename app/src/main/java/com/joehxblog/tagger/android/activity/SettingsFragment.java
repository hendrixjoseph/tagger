package com.joehxblog.tagger.android.activity;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;

import com.joehxblog.tagger.R;
import com.joehxblog.tagger.android.AffiliatePreference;
import com.joehxblog.tagger.android.HistoryPreference;
import com.joehxblog.tagger.android.TaggerPreferences;

public class SettingsFragment extends PreferenceFragmentCompat {
    public static final int MAX_TAGS = 4;

    private TaggerPreferences preferences;

    @Override
    public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        preferences = new TaggerPreferences(this.getContext());

        final PreferenceCategory affiliateTags = findPreference("amazon-affiliate-tags");

        AffiliatePreference affiliatePreference = new AffiliatePreference(getContext(), affiliateTags, preferences);
        affiliatePreference.create();

//        for (int i = 0; i < MAX_TAGS; i++) {
//            final Preference pref = createAssociateTagPreference(i);
//            affiliateTags.addPreference(pref);
//        }
    }

    public void onStart() {
        super.onStart();

        final PreferenceCategory history = findPreference("sharing-history");

        HistoryPreference historyPreference = new HistoryPreference(getContext(), history, preferences);
        historyPreference.create();
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
