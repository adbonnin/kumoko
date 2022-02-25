package fr.adbonnin.kumoko.http;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class URIBuilderUtils {

    public static Optional<NameValuePair> getFirstQueryParam(URIBuilder uriBuilder, String name) {

        final List<NameValuePair> params = uriBuilder.getQueryParams();
        for (NameValuePair param : params) {
            if (Objects.equals(param.getName(), name)) {
                return Optional.of(param);
            }
        }

        return Optional.empty();
    }

    private URIBuilderUtils() { /* Cannot be instantiated */ }
}
