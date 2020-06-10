package com.joehxblog.tagger.android;

import android.content.Context;
import android.text.TextUtils;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.joehxblog.tagger.R;

import java.util.Optional;

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
        pref.setPersistent(false);
        pref.setTitle(R.string.tracking_id);
        pref.setDialogTitle(R.string.set_tracking_id);
        pref.setSummary(tag);
        pref.setDefaultValue(tag);
        pref.setKey(Optional.ofNullable(tag).orElse("new-tag"));

        if (preferences.isDefaultTag(tag)) {
            pref.setIcon(R.drawable.ic_launcher_background);
        }

        pref.setSummaryProvider((Preference.SummaryProvider<EditTextPreference>) this::getSummaryText);

        pref.setOnPreferenceChangeListener((p, n) -> onPreferenceChange(tag, n.toString()));

        return pref;
    }

    private String getSummaryText(final EditTextPreference pref) {
        final String text = ((EditTextPreference)pref).getText();

        if (TextUtils.isEmpty(text)) {
            return "Not set";
        }

        return "?tag=" + text;
    }

    private boolean onPreferenceChange(final String oldTag, final String newTag) {
        if (oldTag == null) {
            this.preferences.addTag(newTag);
        } else if (TextUtils.isEmpty(newTag)){
            this.preferences.removeTag(oldTag);
        } else {
            this.preferences.replaceTag(oldTag, newTag);
        }

        this.affiliate.removeAll();
        create();

        return false;
    }
}
