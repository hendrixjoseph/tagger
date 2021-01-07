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
    }

    @Override
    public void onResume() {
        super.onResume();

        final PreferenceCategory affiliateTags = findPreference("amazon-affiliate-tags");

        final AffiliatePreferenceGroup affiliatePreferenceGroup = new AffiliatePreferenceGroup(getContext(), affiliateTags);
        affiliatePreferenceGroup.update();

        final PreferenceCategory history = findPreference("sharing-history");

        final HistoryPreferenceGroup historyPreferenceGroup = new HistoryPreferenceGroup(getContext(), history);
        historyPreferenceGroup.create();
    }
}
