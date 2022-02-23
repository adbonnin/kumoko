package fr.adbonnin.kumoko.base;

public class StringUtils {

    public static final String EMPTY = "";

    public static final int INDEX_NOT_FOUND = -1;

    public static final char NO_BREAK_SPACE = '\u00A0';

    public static String removeBefore(String str, String searchStr) {
        return removeBefore(str, searchStr, false);
    }

    public static String removeBefore(String str, String searchStr, boolean ignoreCase) {
        return removeBefore(str, searchStr, ignoreCase, 0);
    }

    public static String removeBefore(String str, String searchStr, boolean ignoreCase, int fromIndex) {
        return removeBefore(str, searchStr, ignoreCase, fromIndex, false);
    }

    public static String removeBefore(String str, String searchStr, boolean ignoreCase, int fromIndex, boolean include) {

        final int index = indexOf(str, searchStr, ignoreCase, fromIndex);
        if (index == INDEX_NOT_FOUND) {
            return str;
        }

        return str.substring(index + (include ? 0 : searchStr.length()));
    }

    public static String removeBeforeLast(String str, String searchStr) {
        return removeBeforeLast(str, searchStr, false);
    }

    public static String removeBeforeLast(String str, String searchStr, boolean ignoreCase) {

        if (str == null || searchStr == null) {
            return str;
        }

        return removeBeforeLast(str, searchStr, ignoreCase, str.length() - searchStr.length());
    }

    public static String removeBeforeLast(String str, String searchStr, boolean ignoreCase, int fromIndex) {
        return removeBeforeLast(str, searchStr, ignoreCase, fromIndex, false);
    }

    public static String removeBeforeLast(String str, String searchStr, boolean ignoreCase, int fromIndex, boolean include) {

        final int index = lastIndexOf(str, searchStr, ignoreCase, fromIndex);
        if (index == INDEX_NOT_FOUND) {
            return str;
        }

        return str.substring(index + (include ? 0 : searchStr.length()));
    }

    public static String removeAfter(String str, String searchStr) {
        return removeAfter(str, searchStr, false);
    }

    public static String removeAfter(String str, String searchStr, boolean ignoreCase) {
        return removeAfter(str, searchStr, ignoreCase, 0);
    }

    public static String removeAfter(String str, String searchStr, boolean ignoreCase, int fromIndex) {
        return removeAfter(str, searchStr, ignoreCase, fromIndex, false);
    }

    public static String removeAfter(String str, String searchStr, boolean ignoreCase, int fromIndex, boolean include) {

        final int index = indexOf(str, searchStr, ignoreCase, fromIndex);
        if (index == INDEX_NOT_FOUND) {
            return str;
        }

        return str.substring(0, index + (include ? searchStr.length() : 0));
    }

    public static String removeAfterLast(String str, String searchStr) {
        return removeAfterLast(str, searchStr, false);
    }

    public static String removeAfterLast(String str, String searchStr, boolean ignoreCase) {

        if (str == null || searchStr == null) {
            return str;
        }

        return removeAfterLast(str, searchStr, ignoreCase, str.length() - searchStr.length());
    }

    public static String removeAfterLast(String str, String searchStr, boolean ignoreCase, int fromIndex) {
        return removeAfterLast(str, searchStr, ignoreCase, fromIndex, false);
    }

    public static String removeAfterLast(String str, String searchStr, boolean ignoreCase, int fromIndex, boolean include) {

        final int index = lastIndexOf(str, searchStr, ignoreCase, fromIndex);
        if (index == INDEX_NOT_FOUND) {
            return str;
        }

        return str.substring(0, index + (include ? searchStr.length() : 0));
    }

    public static int indexOfIgnoreCase(String str, String searchStr) {
        return indexOf(str, searchStr, true);
    }

    public static int indexOfIgnoreCase(String str, String searchStr, int fromIndex) {
        return indexOf(str, searchStr, true, fromIndex);
    }

    public static int indexOf(String str, String searchStr, boolean ignoreCase) {
        return indexOf(str, searchStr, ignoreCase, 0);
    }

    public static int indexOf(String str, String searchStr, boolean ignoreCase, int fromIndex) {

        if (str == null || searchStr == null) {
            return INDEX_NOT_FOUND;
        }

        if (fromIndex < 0) {
            fromIndex = 0;
        }

        final int endLimit = str.length() - searchStr.length() + 1;
        if (fromIndex > endLimit) {
            return INDEX_NOT_FOUND;
        }

        if (searchStr.length() == 0) {
            return fromIndex;
        }

        for (int i = fromIndex; i < endLimit; i++) {
            if (str.regionMatches(ignoreCase, i, searchStr, 0, searchStr.length())) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOfIgnoreCase(String str, String searchStr) {
        return lastIndexOf(str, searchStr, true);
    }

    public static int lastIndexOfIgnoreCase(String str, String searchStr, int fromIndex) {
        return lastIndexOf(str, searchStr, true, fromIndex);
    }

    public static int lastIndexOf(String str, String searchStr, boolean ignoreCase) {

        if (str == null || searchStr == null) {
            return -1;
        }

        return lastIndexOf(str, searchStr, ignoreCase, str.length() - searchStr.length());
    }

    public static int lastIndexOf(String str, String searchStr, boolean ignoreCase, int fromIndex) {

        if (str == null || searchStr == null) {
            return INDEX_NOT_FOUND;
        }

        final int searchStrLength = searchStr.length();
        final int strLength = str.length();
        if (fromIndex > strLength - searchStrLength) {
            fromIndex = strLength - searchStrLength;
        }

        if (fromIndex < 0) {
            return INDEX_NOT_FOUND;
        }

        if (searchStrLength == 0) {
            return fromIndex;
        }

        for (int i = fromIndex; i >= 0; i--) {
            if (str.regionMatches(ignoreCase, i, searchStr, 0, searchStrLength)) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }

    private StringUtils() { /* Cannot be instantiated */ }
}
