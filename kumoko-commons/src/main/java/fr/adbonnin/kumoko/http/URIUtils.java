package fr.adbonnin.kumoko.http;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URIUtils {

    public static final byte ESCAPE_CHAR = '%';

    private static final String SCHEME_PATTERN = "([^:/?#]+):";

    private static final String USERINFO_PATTERN = "([^@/]*)";

    private static final String HOST_PATTERN = "([^/?#:]*)";

    private static final String PORT_PATTERN = "(\\d*)";

    private static final String PATH_PATTERN = "([^?#]*)";

    private static final String QUERY_PATTERN = "([^#]*)";

    private static final String LAST_PATTERN = "(.*)";

    // Regex patterns that matches URIs. See RFC 3986, appendix B
    private static final Pattern URI_PATTERN = Pattern.compile("" +
            "^(" + SCHEME_PATTERN + ")?" +
            "(//(" + USERINFO_PATTERN + "@)?" + HOST_PATTERN + "(:" + PORT_PATTERN + ")?" + ")?" +
            PATH_PATTERN +
            "(\\?" + QUERY_PATTERN + ")?" +
            "(#" + LAST_PATTERN + ")?");

    public static String encodeUri(String uri) {
        return encodeUri(uri, StandardCharsets.UTF_8);
    }

    public static String encodeUri(String uri, Charset charset) {

        final Matcher matcher = URI_PATTERN.matcher(uri);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("[" + uri + "] is not a valid URI");
        }

        final String scheme = matcher.group(2);
        final String authority = matcher.group(3);
        final String userInfo = matcher.group(5);
        final String host = matcher.group(6);
        final String port = matcher.group(8);
        final String path = matcher.group(9);
        final String query = matcher.group(11);
        final String fragment = matcher.group(13);

        final StringBuilder sb = new StringBuilder();

        if (scheme != null) {
            sb.append(URIComponents.encodeScheme(scheme, charset));
            sb.append(':');
        }

        if (authority != null) {
            sb.append("//");

            if (userInfo != null) {
                sb.append(URIComponents.encodeUserInfo(userInfo, charset));
                sb.append('@');
            }

            if (host != null) {
                sb.append(URIComponents.encodeHost(host, charset));
            }

            if (port != null) {
                sb.append(':');
                sb.append(URIComponents.encodePort(port, charset));
            }
        }

        sb.append(URIComponents.encodePath(path, charset));

        if (query != null) {
            sb.append('?');
            sb.append(URIComponents.encodeQuery(query, charset));
        }

        if (fragment != null) {
            sb.append('#');
            sb.append(URIComponents.encodeFragment(fragment, charset));
        }

        return sb.toString();
    }

    public static String decode(String uri) {
        return decode(uri, StandardCharsets.UTF_8);
    }

    public static String decode(String uri, Charset charset) {

        if (uri == null) {
            return null;
        }

        final int length = uri.length();
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream(length);

        boolean wasModified = false;
        for (int i = 0; i < length; i++) {
            final int ch = uri.charAt(i);
            if (ch == '+') {
                buffer.write(' ');
            }
            else if (ch == ESCAPE_CHAR) {
                if ((i + 2) >= length) {
                    throw new IllegalArgumentException("Invalid encoded sequence \"" + uri.substring(i) + "\"");
                }

                final int u = digit16(uri.charAt(i + 1));
                final int l = digit16(uri.charAt(i + 2));
                if (u == -1 || l == -1) {
                    throw new IllegalArgumentException("Invalid encoded sequence \"" + uri.substring(i) + "\"");
                }

                buffer.write((char) ((u << 4) + l));
                i += 2;
                wasModified = true;
            }
            else {
                buffer.write(ch);
            }
        }

        return wasModified ? new String(buffer.toByteArray(), charset) : uri;
    }

    private static int digit16(char c) {
        return Character.digit(c, 16);
    }

    private URIUtils() { /* Cannot be instantiated */ }
}
