package android.text;

public class TextUtils {
    public static boolean isEmpty(final CharSequence str) {
        return str == null || str.length() == 0;
    }
}