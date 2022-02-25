package fr.adbonnin.kumoko.web.http

import fr.adbonnin.kumoko.http.HttpClientUtils
import org.apache.hc.core5.http.ContentType
import org.apache.hc.core5.http.HttpEntity
import spock.lang.Specification

import java.nio.charset.StandardCharsets

class HttpClientUtilsSpec extends Specification {

    static DEFAULT_CONTENT_TYPE = ContentType.create('Spock', StandardCharsets.US_ASCII)

    void "should return the entity content type"() {
        given:
        def entity = Stub(HttpEntity) {
            getContentType() >> contentType
        }

        expect:
        HttpClientUtils.getContentType(entity, DEFAULT_CONTENT_TYPE).mimeType == expectedMimeType
        HttpClientUtils.getCharset(entity, DEFAULT_CONTENT_TYPE) == expectedCharset

        where:
        contentType                                        || expectedMimeType              | expectedCharset
        null                                               || DEFAULT_CONTENT_TYPE.mimeType | DEFAULT_CONTENT_TYPE.charset
        ''                                                 || DEFAULT_CONTENT_TYPE.mimeType | DEFAULT_CONTENT_TYPE.charset
        ContentType.create('KirK', StandardCharsets.UTF_8) || 'kirk'                        | StandardCharsets.UTF_8
    }
}
