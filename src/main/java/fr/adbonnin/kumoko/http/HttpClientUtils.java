package fr.adbonnin.kumoko.http;

import org.apache.hc.client5.http.async.HttpAsyncClient;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestProducer;
import org.apache.hc.client5.http.async.methods.SimpleResponseConsumer;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.protocol.HttpClientContext;

import java.util.concurrent.CompletableFuture;

public class HttpClientUtils {

    public static CompletableFuture<SimpleHttpResponse> execute(HttpAsyncClient httpClient,
                                                                SimpleHttpRequest request) {
        final CompletableFuture<SimpleHttpResponse> response = new CompletableFuture<>();

        httpClient.execute(
                SimpleRequestProducer.create(request),
                SimpleResponseConsumer.create(),
                null,
                HttpClientContext.create(),
                new CompletableFutureCallbackAdapter<>(response));

        return response;
    }

    private HttpClientUtils() { /* Cannot be instantiated */ }
}
