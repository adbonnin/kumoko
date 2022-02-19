package fr.adbonnin.kumoko.web.http;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class DownloadResponseHandler implements HttpClientResponseHandler<Void> {

    private final OutputStream output;

    public DownloadResponseHandler(OutputStream output) {
        this.output = Objects.requireNonNull(output);
    }

    @Override
    public Void handleResponse(ClassicHttpResponse response) throws IOException {
        HttpEntity entity = null;
        try {
            entity = response.getEntity();
            entity.writeTo(output);
            return null;
        }
        finally {
            EntityUtils.consumeQuietly(entity);
        }
    }
}