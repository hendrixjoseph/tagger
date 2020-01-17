package com.joehxblog.tagger;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class TaggerPreferences {
    SharedPreferences sharedPreferences;

    public TaggerPreferences(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getDefaultTag() {
        return sharedPreferences.getString("tag-1", "");
    }

    public String getTag(int key) {
        return sharedPreferences.getString("tag-" + key, "");
    }
}
