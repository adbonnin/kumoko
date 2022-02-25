package fr.adbonnin.kumoko.web.html;

import fr.adbonnin.kumoko.http.HttpClientUtils;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Objects;

public class ParseHtmlResponseHandler implements HttpClientResponseHandler<Document> {

    private final ClassicHttpRequest request;

    public ParseHtmlResponseHandler(ClassicHttpRequest request) {
        this.request = Objects.requireNonNull(request);
    }

    @Override
    public Document handleResponse(ClassicHttpResponse response) throws IOException {
        final HttpEntity entity = response.getEntity();
        try {
            final String charsetName = HttpClientUtils.getCharset(entity, ContentType.TEXT_PLAIN).toString();
            return Jsoup.parse(entity.getContent(), charsetName, request.getRequestUri());
        }
        finally {
            EntityUtils.consumeQuietly(entity);
        }
    }
}