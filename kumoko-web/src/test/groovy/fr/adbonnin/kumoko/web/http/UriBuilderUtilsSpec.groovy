package fr.adbonnin.kumoko.web.http

import org.apache.hc.core5.http.message.BasicNameValuePair
import org.apache.hc.core5.net.URIBuilder
import spock.lang.Specification

class UriBuilderUtilsSpec extends Specification {

    void "should return the first query param"() {
        given:
        def uriBuilder = new URIBuilder(url)

        when:
        def pair = UriBuilderUtils.getFirstQueryParam(uriBuilder, name)

        then:
        pair == expectedPair

        where:
        url                | name || expectedPair
        'http://a.b/c?d=e' | 'd'  || Optional.of(new BasicNameValuePair('d', 'e'))
        'http://a.b/c?d=e' | 'f'  || Optional.empty()
    }
}
