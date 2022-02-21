package fr.adbonnin.kumoko.web.json;

import com.fasterxml.jackson.databind.ObjectReader;
import fr.adbonnin.kumoko.io.IOUtils;
import fr.adbonnin.kumoko.web.http.HttpClientUtils;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Objects;

public class ParseJsonValueResponseHandler<T> implements HttpClientResponseHandler<T> {

    private final ObjectReader reader;

    public ParseJsonValueResponseHandler(ObjectReader reader) {
        this.reader = Objects.requireNonNull(reader);
    }

    @Override
    public T handleResponse(ClassicHttpResponse response) throws IOException {
        final HttpEntity entity = response.getEntity();
        BufferedReader buff = null;
        try {
            final Charset charset = HttpClientUtils.getCharset(entity, ContentType.APPLICATION_JSON);
            buff = new BufferedReader(new InputStreamReader(entity.getContent(), charset));
            return reader.readValue(buff);
        }
        finally {
            IOUtils.closeQuietly(buff);
            EntityUtils.consumeQuietly(entity);
        }
    }
}
