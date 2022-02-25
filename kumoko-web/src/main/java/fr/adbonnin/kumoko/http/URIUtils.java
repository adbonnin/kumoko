package fr.adbonnin.kumoko.http;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;

public class URIUtils {

    public static String encodeUri(String unescaped) {
        try {
            return new URLCodec().encode(unescaped);
        }
        catch (EncoderException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decodeUri(String escaped) {
        try {
            return new URLCodec().decode(escaped);
        }
        catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }

    private URIUtils() { /* Should not be instantiated */ }
}
