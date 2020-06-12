package com.joehxblog.tagger.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.joehxblog.android.preference.Preference;
import com.joehxblog.android.text.JoeTextUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AffiliatePreference extends Preference {
    private static final String DEFAULT_TAG_KEY = "default-tag";
    private static final String TAGS_KEY = "tags";

    public AffiliatePreference(final Context context) {
        super(context);
    }

    public AffiliatePreference(final SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }

    public boolean isDefaultTag(String tag) {
        String defaultTag = getDefaultTag();

        if (TextUtils.isEmpty(tag)) {
            return false;
        } else if (defaultTag.isEmpty() || !getTags().contains(defaultTag)) {
            setDefaultTag(tag);
            return true;
        } else {
            return defaultTag.equals(tag);
        }
    }

    public String getDefaultTag() {
        return this.getSharedPreferences().getString(DEFAULT_TAG_KEY, "");
    }

    public void setDefaultTag(String tag) {
        if (!JoeTextUtils.isTrimmedEmpty(tag)) {
            if (!getTags().contains(tag)) {
                addTag(tag);
            }

            this.getSharedPreferences().edit().putString(DEFAULT_TAG_KEY, tag).commit();
        }
    }

    public void replaceTag(final String oldTag, final String newTag) {
        if(!JoeTextUtils.isTrimmedEmpty(newTag) && !JoeTextUtils.isTrimmedEmpty(oldTag)) {
            final Set<String> newSet = getTags().stream()
                    .map(tag -> oldTag.equals(tag) ? newTag : tag)
                    .collect(Collectors.toSet());

            this.getSharedPreferences().edit().putStringSet(TAGS_KEY, newSet).commit();
        }
    }

    public void removeTag(final String tag) {
        final Set<String> newSet = new HashSet<>(getTags());
        newSet.remove(tag);
        this.getSharedPreferences().edit().putStringSet(TAGS_KEY, newSet).commit();
    }

    public Set<String> getTags() {
        return this.getSharedPreferences().getStringSet(TAGS_KEY, Collections.emptySet());
    }

    public void addTag(final String tag) {
        if(!JoeTextUtils.isTrimmedEmpty(tag)) {
            add(TAGS_KEY, tag);

            if (getDefaultTag().isEmpty()) {
                setDefaultTag(tag);
            }
        }
    }
}
