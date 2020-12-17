package com.joehxblog.tagger.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.joehxblog.android.preference.Preference;
import com.joehxblog.android.text.JoeTextUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AffiliatePreference extends Preference {
    private static final String DEFAULT_TAG_KEY = "default-tag";
    private static final String TAGS_KEY = "tags";

    public AffiliatePreference(final Context context) {
        super(context);
    }

    public AffiliatePreference(final SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }

    public boolean isDefaultTag(final String tag) {
        final String defaultTag = getDefaultTag();

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

    public void setDefaultTag(final String tag) {
        if (!JoeTextUtils.isTrimmedEmpty(tag)) {
            if (!getTags().contains(tag)) {
                addTag(tag);
            }

            this.getSharedPreferences().edit().putString(DEFAULT_TAG_KEY, tag).apply();
        }
    }

    public void replaceTag(final String oldTag, final String newTag) {
        if(!JoeTextUtils.isTrimmedEmpty(newTag) && !JoeTextUtils.isTrimmedEmpty(oldTag)) {
            final List<String> newSet = getTags().stream()
                    .map(tag -> oldTag.equals(tag) ? newTag : tag)
                    .collect(Collectors.toList());

            putStringSet(newSet);
        }
    }

    public void removeTag(final String tag) {
        final List<String> newSet = getTags();
        newSet.remove(tag);
        putStringSet(newSet);
    }

    public List<String> getTags() {
        return this.getSharedPreferences().getStringSet(TAGS_KEY, Collections.emptySet())
                .stream()
                .sorted()
                .map(s -> s.replaceAll("^\\d+ ",""))
                .collect(Collectors.toList());
    }

    public void addTag(final String tag) {
        if(!JoeTextUtils.isTrimmedEmpty(tag)) {
            add(TAGS_KEY, createNumberedName(size(), tag));

            if (getDefaultTag().isEmpty()) {
                setDefaultTag(tag);
            }
        }
    }

    private int size() {
        return this.getSharedPreferences().getStringSet(TAGS_KEY, Collections.emptySet()).size();
    }

    private String createNumberedName(final int number, final String tag) {
        return String.format("%02d %s", number, tag);
    }

    private void putStringSet(final List<String> newSet) {
        final Set<String> numberedSet = IntStream.range(0, newSet.size())
                                           .mapToObj(i -> createNumberedName(i, newSet.get(i)))
                                           .collect(Collectors.toSet());
        this.getSharedPreferences().edit().putStringSet(TAGS_KEY, numberedSet).apply();
    }
}
