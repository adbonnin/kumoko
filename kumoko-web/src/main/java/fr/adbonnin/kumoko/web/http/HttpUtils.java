package fr.adbonnin.kumoko.web.http;

import fr.adbonnin.kumoko.io.FileUtils;

import java.net.URI;

public class HttpUtils {

    public static final char PATH_SEPARATOR = '/';

    public static String toResourceName(URI uri) {
        return toResourceName(uri.getPath());
    }

    public static String toResourceName(String path) {
        final int index = path.lastIndexOf(PATH_SEPARATOR);
        return path.substring(index + 1);
    }

    public static String toFilename(URI uri, String emptyFilename) {
        return toFilename(uri.getPath(), emptyFilename);
    }

    public static String toFilename(String path, String emptyFilename) {
        final String uriFilename = toResourceName(path);
        return FileUtils.cleanFilename(uriFilename, emptyFilename);
    }

    private HttpUtils() { /* Cannot be instantiated */ }
}
