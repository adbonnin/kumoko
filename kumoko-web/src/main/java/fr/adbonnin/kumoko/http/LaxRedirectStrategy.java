package fr.adbonnin.kumoko.http;

import org.apache.hc.client5.http.impl.DefaultRedirectStrategy;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.protocol.HttpContext;

public class LaxRedirectStrategy extends DefaultRedirectStrategy {

    public static final LaxRedirectStrategy INSTANCE = new LaxRedirectStrategy();

    @Override
    public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context) {
        return response.getFirstHeader("location") != null;
    }
}
