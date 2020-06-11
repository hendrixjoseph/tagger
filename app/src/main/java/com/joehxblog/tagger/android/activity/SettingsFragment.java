package com.joehxblog.tagger.android.activity;

import android.os.Bundle;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;

import com.joehxblog.tagger.R;
import com.joehxblog.tagger.android.AffiliatePreferenceGroup;
import com.joehxblog.tagger.android.HistoryPreferenceGroup;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        final PreferenceCategory affiliateTags = findPreference("amazon-affiliate-tags");

        AffiliatePreferenceGroup affiliatePreferenceGroup = new AffiliatePreferenceGroup(getContext(), affiliateTags);
        affiliatePreferenceGroup.create();
    }

    public void onStart() {
        super.onStart();

        final PreferenceCategory history = findPreference("sharing-history");

        HistoryPreferenceGroup historyPreferenceGroup = new HistoryPreferenceGroup(getContext(), history);
        historyPreferenceGroup.create();
    }
}
