package fr.adbonnin.kumoko.http;

import org.apache.hc.core5.concurrent.FutureCallback;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @see <a href="https://github.com/commercetools/commercetools-jvm-sdk/blob/master/sdk-http-apache-async/src/main/java/io/sphere/sdk/http/CompletableFutureCallbackAdapter.java">CompletableFutureCallbackAdapter.java</a>
 */
public class CompletableFutureCallbackAdapter<T> implements FutureCallback<T> {
    private final CompletableFuture<T> future;

    public CompletableFutureCallbackAdapter(CompletableFuture<T> future) {
        this.future = Objects.requireNonNull(future);
    }

    @Override
    public void cancelled() {
        future.cancel(true);
    }

    @Override
    public void completed(final T result) {
        future.complete(result);
    }

    @Override
    public void failed(final Exception e) {
        future.completeExceptionally(e);
    }
}
