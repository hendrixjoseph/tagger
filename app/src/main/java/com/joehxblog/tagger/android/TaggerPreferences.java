package com.joehxblog.tagger.android;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.joehxblog.tagger.History;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TaggerPreferences {
    private static final String HISTORY_KEY = "history";
    private static final String TAGS_KEY = "tags";

    private final SharedPreferences sharedPreferences;

    public TaggerPreferences(final Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public TaggerPreferences(final SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String getDefaultTag() {
        return this.sharedPreferences.getString("tag-1", "");
    }

    public String getTag(final int key) {
        return this.sharedPreferences.getString("tag-" + key, "");
    }

    public Set<String> getTags() {
        return this.sharedPreferences.getStringSet(TAGS_KEY, Collections.emptySet());
    }

    public void addTag(String tag) {
        add(TAGS_KEY, tag);
    }

    public void createHistoryItem(final String title, final String url) {
        final History newItem = new History(title, url);
        add(HISTORY_KEY, newItem.toString());
    }

    public void clearHistory() {
        this.sharedPreferences.edit().remove(HISTORY_KEY).commit();
    }

    public List<History> getHistory() {
        final Set<String> history = this.sharedPreferences.getStringSet(HISTORY_KEY, Collections.emptySet());

        return history.stream().map(thrown(History::new)).collect(Collectors.toList());
    }

    private void add(String key, String value) {
        final Set<String> set = this.sharedPreferences.getStringSet(key, Collections.emptySet());

        final Set<String> newSet = new HashSet<>(set);
        newSet.add(value);

        this.sharedPreferences.edit().putStringSet(key, newSet).commit();
    }

    private <T, R, E extends Exception> Function<T,R> thrown(final FunctionWithException<T,R,E> function) {
        return arg ->  {
            try {
                return function.apply(arg);
            } catch (final Exception e) {
                final RuntimeException re = new RuntimeException(e);
                re.printStackTrace();
                throw re;
            }
        };
    }

    interface FunctionWithException<T, R, E extends Exception> {

        R apply(T t) throws E;
    }
}
