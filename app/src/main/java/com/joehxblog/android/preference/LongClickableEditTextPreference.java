package com.joehxblog.android.preference;

import android.content.Context;
import android.view.View;

import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceViewHolder;

public class LongClickableEditTextPreference extends EditTextPreference implements LongClickablePreference {

    private View.OnLongClickListener longClickListener = v -> true;

    public LongClickableEditTextPreference(final Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(final PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        LongClickablePreference.super.onBindViewHolder(holder);
    }

    @Override
    public View.OnLongClickListener getLongClickListener() {
        return this.longClickListener;
    }

    @Override
    public void setLongClickListener(final View.OnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
}
