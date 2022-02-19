package fr.adbonnin.kumoko.web.html

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Tag
import spock.lang.Specification

class ElementUtilsSpec extends Specification {

    void "should append children to element"() {
        given:
        def element = new Element('a')
        def children = ['b', 'c'].collect { new Element(it) }

        expect:
        element.children().isEmpty()

        when:
        ElementUtils.appendChildren(element, children)

        then:
        element.children().size() == 2
        element.child(0).tagName() == 'b'
        element.child(1).tagName() == 'c'
    }

    void "should get absUrl of attribut"() {
        given:
        def elt = new Element(Tag.valueOf(tag), 'http://a.b')
        elt.attr(attributeKey, attributeValue)

        expect:
        ElementUtils.absUrl(elt) == expectedUrl

        where:
        tag   | attributeKey | attributeValue || expectedUrl
        'a'   | 'href'       | '/c'           || 'http://a.b/c'
        'img' | 'src'        | '/c'           || 'http://a.b/c'
        'a'   | 'notAttr'    | '/c'           || ''
    }

    void "should transform '#html' to lines '#expectedLines'"() {
        given:
        def element = Jsoup.parse(html)

        expect:
        ElementUtils.lines(element) == expectedLines

        where:
        html                       || expectedLines
        '<div>a</div><div>b</div>' || 'a\nb'
        'a<div>b</div>'            || 'a\nb'

        'a<span>b</span>'          || 'ab'
        '<span>a</span>b'          || 'ab'
    }
}
