package com.joehxblog.android.preference;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceViewHolder;

import java.util.function.Supplier;

public class MyEditTextPreference extends EditTextPreference implements LongClickablePreference {

    private View.OnLongClickListener longClickListener = v -> true;
    private Supplier<Boolean> swipeRightListener = () -> true;
    private final GestureDetector gestureDetector;

    public MyEditTextPreference(final Context context) {
        super(context);
        this.gestureDetector = new GestureDetector(context, new GestureListener());
    }

    @Override
    public void onBindViewHolder(final PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        LongClickablePreference.super.onBindViewHolder(holder);

//        final View itemView = holder.itemView;
//        itemView.setOnTouchListener((v,e) -> this.gestureDetector.onTouchEvent(e));
    }

    @Override
    public View.OnLongClickListener getLongClickListener() {
        return this.longClickListener;
    }

    @Override
    public void setLongClickListener(final View.OnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public void setSwipeRightListener(final Supplier<Boolean> swipeRightListener) {
        this.swipeRightListener = swipeRightListener;
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(final MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(final MotionEvent e1, final MotionEvent e2, final float velocityX, final float velocityY) {

            final float diffX = e2.getX() - e1.getX();

            if (diffX > 0) {
                return MyEditTextPreference.this.swipeRightListener.get();
            }

            return false;
        }
    }
}
