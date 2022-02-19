package fr.adbonnin.kumoko.web.html;

public class HtmlUtils {

    public static final String LINE_BREAK_TAG = "br";

    public static String getUrlAttribute(String nodeName) {

        if (nodeName == null) {
            return null;
        }

        switch (nodeName) {
            case "a":
                return "href";
            case "img":
                return "src";
            default:
                return null;
        }
    }

    private HtmlUtils() { /* Cannot be instantiated */ }
}
