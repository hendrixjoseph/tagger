package com.joehxblog.tagger.android;

import android.content.Context;
import android.content.SharedPreferences;

import com.joehxblog.android.preference.Preference;
import com.joehxblog.tagger.History;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HistoryPreference extends Preference {
    private static final String HISTORY_KEY = "history";

    public HistoryPreference(final Context context) {
        super(context);
    }

    public HistoryPreference(final SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }

    public void createHistoryItem(final String title, final String url) {
        final History newItem = new History(title, url);
        add(HISTORY_KEY, newItem.toString());
    }

    public void clearHistory() {
        this.getSharedPreferences().edit().remove(HISTORY_KEY).commit();
    }

    public List<History> getHistory() {
        final Set<String> history = this.getSharedPreferences()
                .getStringSet(HISTORY_KEY, Collections.emptySet());

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
