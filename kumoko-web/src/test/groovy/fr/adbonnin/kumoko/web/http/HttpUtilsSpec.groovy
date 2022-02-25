package fr.adbonnin.kumoko.web.http

import fr.adbonnin.kumoko.http.HttpUtils
import spock.lang.Specification

class HttpUtilsSpec extends Specification {

    def "should return the name of the resource '#expected' from the path '#uri'"() {
        expect:
        HttpUtils.toResourceName(new URI(uri)) == expected

        where:
        uri                  || expected
        'http://foo/bar'     || 'bar'
        'http://foo/bar/'    || ''
        'http://foo'         || ''
        'http://abc/a%20b.c' || 'a b.c'
    }

    def "should return the filename '#expected' frm the path '#uri'"() {
        expect:
        HttpUtils.toFilename(new URI(uri), emptyDefault) == expected

        where:
        uri               | emptyDefault || expected
        'http://foo/abc'  | null         || 'abc'
        'http://foo/abc/' | 'def'        || 'def'
        'http://foo'      | 'def'        || 'def'
        'http://foo/:'    | 'bar'        || 'bar'
    }
}
