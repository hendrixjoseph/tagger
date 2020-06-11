package com.joehxblog.tagger.android;

import android.content.Context;
import android.content.SharedPreferences;

import com.joehxblog.android.preference.Preference;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AffiliatePreference extends Preference {
    private static final String TAGS_KEY = "tags";

    public AffiliatePreference(final Context context) {
        super(context);
    }

    public AffiliatePreference(final SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }

    public boolean isDefaultTag(String tag) {
        String defaultTag = getDefaultTag();

        if (defaultTag.isEmpty()) {
            setDefaultTag(tag);
            return true;
        } else {
            return defaultTag.equals(tag);
        }
    }

    public String getDefaultTag() {
        return this.getSharedPreferences().getString("default-tag", "");
    }

    public void setDefaultTag(String tag) {
        this.getSharedPreferences().edit().putString("default-tag", tag).commit();
    }

    public void replaceTag(final String oldTag, final String newTag) {
        final Set<String> newSet = getTags()
                .stream()
                .map(tag -> oldTag.equals(tag) ? newTag : tag)
                .collect(Collectors.toSet());

        this.getSharedPreferences().edit().putStringSet(TAGS_KEY, newSet).commit();
    }

    public void removeTag(final String tag) {
        final Set<String> newSet = new HashSet<>(getTags());
        newSet.remove(tag);
        this.getSharedPreferences().edit().putStringSet(TAGS_KEY, newSet).commit();
    }

    public String getTag(final int key) {
        return this.getSharedPreferences().getString("tag-" + key, "");
    }

    public Set<String> getTags() {
        return this.getSharedPreferences().getStringSet(TAGS_KEY, Collections.emptySet());
    }

    public void addTag(final String tag) {
        add(TAGS_KEY, tag);

        if (getDefaultTag().isEmpty()) {
            setDefaultTag(tag);
        }
    }
}
