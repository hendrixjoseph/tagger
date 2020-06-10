package com.joehxblog.mocked;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MockSharedPreferences implements SharedPreferences {

    private final Map<String, Object> preferenceMap = new HashMap<>();

    @Override
    public Map<String, ?> getAll() {
        return this.preferenceMap;
    }

    @Nullable
    @Override
    public String getString(final String key, @Nullable final String defValue) {
        return (String) this.preferenceMap.getOrDefault(key, defValue);
    }

    @Nullable
    @Override
    public Set<String> getStringSet(final String key, @Nullable final Set<String> defValues) {
        return (Set<String>) this.preferenceMap.getOrDefault(key, defValues);
    }

    @Override
    public int getInt(final String key, final int defValue) {
        return (int) this.preferenceMap.getOrDefault(key, defValue);
    }

    @Override
    public long getLong(final String key, final long defValue) {
        return (long) this.preferenceMap.getOrDefault(key, defValue);
    }

    @Override
    public float getFloat(final String key, final float defValue) {
        return (float) this.preferenceMap.getOrDefault(key, defValue);
    }

    @Override
    public boolean getBoolean(final String key, final boolean defValue) {
        return (boolean) this.preferenceMap.getOrDefault(key, defValue);
    }

    @Override
    public boolean contains(final String key) {
        return this.preferenceMap.containsKey(key);
    }

    @Override
    public Editor edit() {
        return new MockSharedPreferencesEditor(this.preferenceMap);
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(final OnSharedPreferenceChangeListener listener) {

    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(final OnSharedPreferenceChangeListener listener) {

    }
}
