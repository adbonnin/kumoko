package fr.adbonnin.kumoko.http;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

class LaxTrustManager implements X509TrustManager {

    public static final LaxTrustManager INSTANCE = new LaxTrustManager();

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
        // everything is trusted
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
        // everything is trusted
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
