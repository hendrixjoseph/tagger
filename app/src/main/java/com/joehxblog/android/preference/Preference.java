package com.joehxblog.android.preference;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Preference {
    private final SharedPreferences sharedPreferences;

    public Preference(final Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Preference(final SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    protected SharedPreferences getSharedPreferences() {
        return this.sharedPreferences;
    }

    protected void add(final String key, final String value) {
        final Set<String> set = this.sharedPreferences.getStringSet(key, Collections.emptySet());

        final Set<String> newSet = new HashSet<>(set);
        newSet.add(value);

        this.sharedPreferences.edit().putStringSet(key, newSet).commit();
    }
}
