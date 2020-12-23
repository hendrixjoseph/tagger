package com.joehxblog.tagger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Objects;

public class History implements Comparable<History> {
    private static final String DATETIME_KEY = "datetime";
    private static final String URL_KEY = "url";
    private static final String TITLE_KEY = "title";

    private final long datetime;
    private final String title;
    private final String url;

    public History(final String title, final String url) {
        this.title = Objects.requireNonNull(title);
        this.url = Objects.requireNonNull(url);
        this.datetime = new Date().getTime();
    }

    public History(final String json) throws JSONException {
        final JSONObject object = new JSONObject(json);

        this.title = object.getString(TITLE_KEY);
        this.url = object.getString(URL_KEY);
        this.datetime = object.getLong(DATETIME_KEY);
    }
    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    @Override
    public String toString() {
        final JSONObject object = new JSONObject();

        try {
            object.put(TITLE_KEY, this.title);
            object.put(URL_KEY, this.url);
            object.put(DATETIME_KEY, this.datetime);
        } catch (final JSONException e) {
            e.printStackTrace();
        }

        return object.toString();
    }

    @Override
    public int compareTo(final History o) {
        if (o == null) {
            return -1;
        } else {
            return (int) (o.datetime - this.datetime);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final History history = (History) o;
        return this.datetime == history.datetime && this.title.equals(history.title) && this.url.equals(history.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.datetime, this.title, this.url);
    }
}
