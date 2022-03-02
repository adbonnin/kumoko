package fr.adbonnin.kumoko.http;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @see <a href="https://github.com/apache/commons-codec/blob/master/src/main/java/org/apache/commons/codec/net/URLCodec.java">URLCodec.java</a>
 * @see <a href="https://github.com/oblac/jodd-util/blob/master/src/main/java/jodd/net/URLCoder.java">URLCoder.java</a>
 * @see <a href="https://github.com/oblac/jodd-util/blob/master/src/main/java/jodd/net/URLDecoder.java">URLDecoder.java</a>
 * @see <a href="https://github.com/spring-projects/spring-framework/blob/3.2.x/spring-web/src/main/java/org/springframework/web/util/HierarchicalUriComponents.java">HierarchicalUriComponents.java</a>
 * @see <a href="https://github.com/javaee/jersey-1.x/blob/master/jersey-core/src/main/java/com/sun/jersey/api/uri/UriComponent.java">UriComponent.java</a>
 */
public class URIComponents {

    public static String encodeScheme(String scheme, Charset encoding) {
        return encodeUriComponent(scheme, encoding, Type.SCHEME);
    }

    public static String encodeAuthority(String scheme, Charset encoding) {
        return encodeUriComponent(scheme, encoding, Type.AUTHORITY);
    }

    public static String encodeUserInfo(String userInfo, Charset encoding) {
        return encodeUriComponent(userInfo, encoding, Type.USER_INFO);
    }

    public static String encodeHost(String host, Charset encoding) {
        return encodeUriComponent(host, encoding, Type.HOST);
    }

    public static String encodePort(String port, Charset encoding) {
        return encodeUriComponent(port, encoding, Type.PORT);
    }

    public static String encodePath(String path, Charset encoding) {
        return encodeUriComponent(path, encoding, Type.PATH);
    }

    public static String encodePathSegment(String path, Charset encoding) {
        return encodeUriComponent(path, encoding, Type.PATH_SEGMENT);
    }

    public static String encodeQuery(String query, Charset encoding) {
        return encodeUriComponent(query, encoding, Type.QUERY);
    }

    public static String encodeQueryParam(String query, Charset encoding) {
        return encodeUriComponent(query, encoding, Type.QUERY_PARAM);
    }

    public static String encodeFragment(String fragment, Charset encoding) {
        return encodeUriComponent(fragment, encoding, Type.FRAGMENT);
    }

    public static String encodeUriComponent(String str, Charset encoding, Type type) {

        if (str == null) {
            return null;
        }

        final byte[] bytes = encodeBytes(str.getBytes(encoding), type);
        return new String(bytes, StandardCharsets.US_ASCII);
    }

    private static byte[] encodeBytes(byte[] bytes, Type type) {

        if (bytes == null) {
            return null;
        }

        final ByteArrayOutputStream buffer = new ByteArrayOutputStream(bytes.length);
        for (byte b : bytes) {
            int c = b;
            if (c < 0) {
                c = 256 + c;
            }

            if (type.isAllowed(c)) {
                buffer.write(c);
            }
            else {
                buffer.write(URIUtils.ESCAPE_CHAR);

                final char hex1 = hexDigit(c >> 4);
                final char hex2 = hexDigit(c);
                buffer.write(hex1);
                buffer.write(hex2);
            }
        }

        return buffer.toByteArray();
    }

    private static char hexDigit(int b) {
        return Character.toUpperCase(Character.forDigit(b & 0xF, 16));
    }

    public enum Type {
        SCHEME {
            @Override
            public boolean isAllowed(int c) {
                return isAlpha(c) || isDigit(c) || '+' == c || '-' == c || '.' == c;
            }
        },
        AUTHORITY {
            @Override
            public boolean isAllowed(int c) {
                return isUnreserved(c) || isSubDelimiter(c) || ':' == c || '@' == c;
            }
        },
        USER_INFO {
            @Override
            public boolean isAllowed(int c) {
                return isUnreserved(c) || isSubDelimiter(c) || ':' == c;
            }
        },
        HOST {
            @Override
            public boolean isAllowed(int c) {
                return isUnreserved(c) || isSubDelimiter(c);
            }
        },
        PORT {
            @Override
            public boolean isAllowed(int c) {
                return isDigit(c);
            }
        },
        PATH {
            @Override
            public boolean isAllowed(int c) {
                return isPchar(c) || '/' == c;
            }
        },
        PATH_SEGMENT {
            @Override
            public boolean isAllowed(int c) {
                return isPchar(c);
            }
        },
        QUERY {
            @Override
            public boolean isAllowed(int c) {
                return isPchar(c) || '/' == c || '?' == c;
            }
        },
        QUERY_PARAM {
            @Override
            public boolean isAllowed(int c) {
                if ('=' == c || '+' == c || '&' == c) {
                    return false;
                }
                else {
                    return isPchar(c) || '/' == c || '?' == c;
                }
            }
        },
        FRAGMENT {
            @Override
            public boolean isAllowed(int c) {
                return isPchar(c) || '/' == c || '?' == c;
            }
        };

        /**
         * Indicates whether the given character is allowed in this URI component.
         *
         * @return {@code true} if the character is allowed; {@code false} otherwise
         */
        public abstract boolean isAllowed(int c);

        /**
         * Indicates whether the given character is in the {@code ALPHA} set.
         *
         * @see <a href="https://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
         */
        protected boolean isAlpha(int c) {
            return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
        }

        /**
         * Indicates whether the given character is in the {@code DIGIT} set.
         *
         * @see <a href="https://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
         */
        protected boolean isDigit(int c) {
            return c >= '0' && c <= '9';
        }

        /**
         * Indicates whether the given character is in the {@code sub-delims} set.
         *
         * @see <a href="https://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
         */
        protected boolean isSubDelimiter(int c) {
            return '!' == c || '$' == c || '&' == c || '\'' == c || '(' == c || ')' == c || '*' == c || '+' == c ||
                    ',' == c || ';' == c || '=' == c;
        }

        /**
         * Indicates whether the given character is in the {@code unreserved} set.
         *
         * @see <a href="https://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
         */
        protected boolean isUnreserved(int c) {
            return isAlpha(c) || isDigit(c) || '-' == c || '.' == c || '_' == c || '~' == c;
        }

        /**
         * Indicates whether the given character is in the {@code pchar} set.
         *
         * @see <a href="https://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
         */
        protected boolean isPchar(int c) {
            return isUnreserved(c) || isSubDelimiter(c) || ':' == c || '@' == c;
        }
    }
}
