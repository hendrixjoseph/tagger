package com.joehxblog.tagger.android;

import android.content.Context;
import android.text.TextUtils;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.joehxblog.tagger.R;

public class AffiliatePreference {
    private final TaggerPreferences preferences;
    private final Context context;
    private final PreferenceCategory affiliate;

    public AffiliatePreference(final Context context, final PreferenceCategory affiliate, final TaggerPreferences preferences) {
        this.context = context;
        this.affiliate = affiliate;
        this.preferences = preferences;
    }

    public void create() {
        this.preferences.getTags()
                .stream()
                .map(this::createAssociateTagPreference)
                .forEach(this.affiliate::addPreference);

        this.affiliate.addPreference(createAssociateTagPreference(null));
    }

    private Preference createAssociateTagPreference(final String tag) {
        final EditTextPreference pref = new EditTextPreference(this.context);
        pref.setTitle(R.string.tracking_id);
        pref.setDialogTitle(R.string.set_tracking_id);
        pref.setSummary(tag);

        pref.setSummaryProvider((Preference.SummaryProvider<EditTextPreference>) preference -> {
            final String text = preference.getText();
            if (TextUtils.isEmpty(text)){
                return "Not set";
            }

            return "?tag=" + text;
        });

        return pref;
    }
}
