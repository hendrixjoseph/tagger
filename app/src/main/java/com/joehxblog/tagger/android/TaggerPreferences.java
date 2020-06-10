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

    public void createHistoryItem(final String title, final String url) {
        final Set<String> history = this.sharedPreferences.getStringSet("history", Collections.emptySet());

        final Set<String> newHistory = new HashSet<>(history);
        final History newItem = new History(title, url);
        newHistory.add(newItem.toString());

        this.sharedPreferences.edit().putStringSet(HISTORY_KEY, newHistory).commit();
    }

    public void clearHistory() {
        this.sharedPreferences.edit().remove(HISTORY_KEY).commit();
    }

    public List<History> getHistory() {
        final Set<String> history = this.sharedPreferences.getStringSet(HISTORY_KEY, Collections.emptySet());

        return history.stream().map(thrown(History::new)).collect(Collectors.toList());
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
