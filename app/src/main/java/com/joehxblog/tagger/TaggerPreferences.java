package com.joehxblog.tagger;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class TaggerPreferences {
    private final SharedPreferences sharedPreferences;

    public TaggerPreferences(final Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getDefaultTag() {
        return this.sharedPreferences.getString("tag-1", "");
    }

    public String getTag(final int key) {
        return this.sharedPreferences.getString("tag-" + key, "");
    }
}
