package fr.adbonnin.kumoko.web.http;

import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.ssl.SSLContextBuilder;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

public class HttpClientUtils {

    public static final SSLConnectionSocketFactory UNSAFE_SSL_SOCKET_FACTORY = createUnsafeSSLSocketFactory();

    /**
     * @see HttpClientUtils#UNSAFE_SSL_SOCKET_FACTORY
     */
    private static SSLConnectionSocketFactory createUnsafeSSLSocketFactory() {
        final SSLContext sslContext;
        try {
            sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(new TrustSelfSignedStrategy())
                    .build();

            final TrustManager[] tm = {LaxTrustManager.INSTANCE};
            sslContext.init(new KeyManager[0], tm, new SecureRandom());
            sslContext.init(null, tm, new SecureRandom());
        }
        catch (GeneralSecurityException e) {
            throw new IllegalArgumentException(e); // never append
        }

        return new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
    }

    public static HttpClientBuilder createUnsafeHttpClientBuilder() {
        final HttpClientConnectionManager connManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(UNSAFE_SSL_SOCKET_FACTORY)
                .build();

        return HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .setRedirectStrategy(LaxRedirectStrategy.INSTANCE);
    }

    public static Charset getCharset(HttpEntity entity, ContentType defaultContentType) {
        final ContentType contentType = getContentType(entity, defaultContentType);
        final Charset charset = contentType.getCharset();
        return charset == null ? defaultContentType.getCharset() : charset;
    }

    public static ContentType getContentType(HttpEntity entity, ContentType defaultContentType) {
        final ContentType contentType = ContentType.parseLenient(entity.getContentType());
        return contentType == null ? defaultContentType : contentType;
    }

    private HttpClientUtils() { /* Cannot be instantiated */ }
}