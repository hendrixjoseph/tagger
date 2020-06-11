package com.joehxblog.android.preference;

import android.view.View;

import androidx.preference.PreferenceViewHolder;

public interface LongClickablePreference {

    default void onBindViewHolder(PreferenceViewHolder holder) {
        View itemView = holder.itemView;
        itemView.setOnLongClickListener(getLongClickListener()::onLongClick);
    }

    View.OnLongClickListener getLongClickListener();

    void setLongClickListener(View.OnLongClickListener longClickListener);


}
