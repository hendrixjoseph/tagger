package com.joehxblog.tagger.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.preference.DialogPreference;

import java.util.Optional;

public class OkCancelDialogPreference extends DialogPreference {
    private DialogInterface.OnClickListener positiveListener = (d, i) -> { };
    private DialogInterface.OnClickListener negativeListener = (d, i) -> { };

    public OkCancelDialogPreference(final Context context) {
        super(context);
    }

    public void setPositiveListener(DialogInterface.OnClickListener listener) {
        this.positiveListener = listener;
    }

    @Override
    public void onClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        CharSequence title = Optional.ofNullable(this.getDialogTitle()).orElse(this.getTitle());

        builder.setMessage(this.getDialogMessage())
                .setTitle(title)
                .setIcon(this.getIcon())
                .setPositiveButton(getPositiveButtonText(), positiveListener)
                .setNegativeButton(getNegativeButtonText(), negativeListener)
                .show();
    }
}
