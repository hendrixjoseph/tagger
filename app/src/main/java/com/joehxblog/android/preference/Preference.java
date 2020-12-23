package com.joehxblog.android.preference;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Preference<E> {
    private final SharedPreferences sharedPreferences;
    private final String key;

    public Preference(final Context context, String key) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.key = key;
    }

    public Preference(final SharedPreferences sharedPreferences, String key) {
        this.sharedPreferences = sharedPreferences;
        this.key = key;
    }

    protected SharedPreferences getSharedPreferences() {
        return this.sharedPreferences;
    }

    protected void add(final E value) {
        final Set<String> set = this.sharedPreferences.getStringSet(key, Collections.emptySet());

        final Set<String> newSet = new HashSet<>(set);
        newSet.add(value.toString());

        this.sharedPreferences.edit().putStringSet(key, newSet).commit();
    }

    protected void delete(final E value) {
        final Set<String> set = this.sharedPreferences.getStringSet(key, Collections.emptySet());

        final Set<String> newSet = new HashSet<>(set);
        newSet.remove(value.toString());

        this.sharedPreferences.edit().putStringSet(key, newSet).commit();
    }


}
