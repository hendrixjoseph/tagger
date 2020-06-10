package com.joehxblog.tagger.android;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceViewHolder;

import java.util.function.Supplier;

public class MyEditTextPreference extends EditTextPreference {

    private View.OnLongClickListener longClickListener = v -> true;
    private Supplier<Boolean> swipeRightListener = () -> true;
    private final GestureDetector gestureDetector;

    public MyEditTextPreference(Context context) {
        super(context);
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        View itemView = holder.itemView;
        itemView.setOnLongClickListener(longClickListener::onLongClick);
        itemView.setOnTouchListener((v,e) -> gestureDetector.onTouchEvent(e));


    }

    public void setLongClickListener(View.OnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public void setSwipeRightListener(Supplier<Boolean> swipeRightListener) {
        this.swipeRightListener = swipeRightListener;
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            float diffX = e2.getX() - e1.getX();

            if (diffX > 0) {
                return swipeRightListener.get();
            }

            return false;
        }
    }
}
