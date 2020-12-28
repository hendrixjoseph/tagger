package com.joehxblog.tagger.android;

import android.content.Context;
import android.text.TextUtils;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.joehxblog.android.preference.LongClickableEditTextPreference;
import com.joehxblog.android.text.JoeTextUtils;
import com.joehxblog.tagger.R;

import java.util.Optional;

public class AffiliatePreferenceGroup {
    private final AffiliatePreference preferences;
    private final Context context;
    private final PreferenceCategory affiliate;

    public AffiliatePreferenceGroup(final Context context, final PreferenceCategory affiliate) {
        this.context = context;
        this.affiliate = affiliate;
        this.preferences = new AffiliatePreference(context);
    }

    public void create() {
        this.preferences.getTags()
                .stream()
                .map(this::createAssociateTagPreference)
                .forEach(this.affiliate::addPreference);

        this.affiliate.addPreference(createAssociateTagPreference(null));
    }

    private Preference createAssociateTagPreference(final String tag) {
        final LongClickableEditTextPreference pref = new LongClickableEditTextPreference(this.context);
        pref.setPersistent(false);
        pref.setTitle(R.string.tracking_id);

        if (tag == null) {
            pref.setDialogTitle(R.string.add_tracking_id);
        } else {
            pref.setDialogTitle(R.string.set_tracking_id);
        }

        pref.setSummary(tag);
        pref.setDefaultValue(tag);
        pref.setKey(Optional.ofNullable(tag).orElse("new-tag"));

        if (this.preferences.isDefaultTag(tag)) {
            pref.setIcon(R.drawable.default_tag);
        }

        pref.setLongClickListener(v -> setDefaultTag(tag));

        pref.setSummaryProvider((Preference.SummaryProvider<EditTextPreference>) this::getSummaryText);

        pref.setOnPreferenceChangeListener((p, n) -> onPreferenceChange(tag, n.toString()));

        return pref;
    }

    private boolean setDefaultTag(final String tag) {
        this.preferences.setDefaultTag(tag);
        refresh();

        return true;
    }

    private String getSummaryText(final EditTextPreference pref) {
        final String text = ((EditTextPreference)pref).getText();

        if (TextUtils.isEmpty(text)) {

            return this.context.getString(R.string.add_new_tag);
        }

        return "?tag=" + text;
    }

    private boolean onPreferenceChange(final String oldTag, final String newTag) {
        if (JoeTextUtils.isTrimmedEmpty(oldTag)) {
            this.preferences.addTag(newTag);
        } else if (JoeTextUtils.isTrimmedEmpty(newTag)){
            this.preferences.removeTag(oldTag);
        } else {
            this.preferences.replaceTag(oldTag, newTag);
        }

        refresh();

        return false;
    }

    private void refresh() {
        this.affiliate.removeAll();
        create();
    }
}
