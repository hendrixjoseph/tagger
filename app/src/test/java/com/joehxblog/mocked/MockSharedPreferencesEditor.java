package com.joehxblog.mocked;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MockSharedPreferencesEditor implements SharedPreferences.Editor {

    private final Map<String, Object> prefMap;
    private final Map<String, Object> tempMap = new HashMap<>();
    private final Set<String> keyRemovals = new HashSet<>();
    private boolean clear = false;

    public MockSharedPreferencesEditor(final Map<String, Object> prefMap) {
        this.prefMap = prefMap;
    }

    @Override
    public SharedPreferences.Editor putString(final String key, @Nullable final String value) {
        this.tempMap.put(key, value);
        return this;
    }

    @Override
    public SharedPreferences.Editor putStringSet(final String key, @Nullable final Set<String> values) {
        this.tempMap.put(key, values);
        return this;
    }

    @Override
    public SharedPreferences.Editor putInt(final String key, final int value) {
        this.tempMap.put(key, value);
        return this;
    }

    @Override
    public SharedPreferences.Editor putLong(final String key, final long value) {
        this.tempMap.put(key, value);
        return this;
    }

    @Override
    public SharedPreferences.Editor putFloat(final String key, final float value) {
        this.tempMap.put(key, value);
        return this;
    }

    @Override
    public SharedPreferences.Editor putBoolean(final String key, final boolean value) {
        this.tempMap.put(key, value);
        return this;
    }

    @Override
    public SharedPreferences.Editor remove(final String key) {
        this.keyRemovals.add(key);
        return this;
    }

    @Override
    public SharedPreferences.Editor clear() {
        this.clear = true;
        return this;
    }

    @Override
    public boolean commit() {
        if (this.clear) {
            this.prefMap.clear();
        }

        this.keyRemovals.forEach(this.prefMap::remove);
        this.keyRemovals.clear();

        this.prefMap.putAll(this.tempMap);
        this.tempMap.clear();

        return true;
    }

    @Override
    public void apply() {
        commit();
    }
}
