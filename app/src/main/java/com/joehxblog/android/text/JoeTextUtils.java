package com.joehxblog.android.text;

import android.text.TextUtils;

public class JoeTextUtils {
    public static boolean isTrimmedEmpty(String string) {
        return TextUtils.isEmpty(string) || string.trim().isEmpty();
    }
}
