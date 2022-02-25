package fr.adbonnin.kumoko.http

import org.apache.hc.core5.http.NameValuePair
import org.apache.hc.core5.net.URIBuilder
import java.util.*

fun URIBuilder.getFirstQueryParam(name: String): Optional<NameValuePair> {
    return URIBuilderUtils.getFirstQueryParam(this, name)
}
