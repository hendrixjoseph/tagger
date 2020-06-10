package com.joehxblog.tagger;

import org.json.JSONException;
import org.json.JSONObject;

public class History {
    private final String title;
    private final String url;

    public History(final String title, final String url) {
        this.title = title;
        this.url = url;
    }

    public History(final String json) throws JSONException {
        final JSONObject object = new JSONObject(json);

        this.title = object.getString("title");
        this.url = object.getString("url");
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
            object.put("title", this.title);
            object.put("url", this.url);
        } catch (final JSONException e) {
            e.printStackTrace();
        }

        return object.toString();
    }
}
