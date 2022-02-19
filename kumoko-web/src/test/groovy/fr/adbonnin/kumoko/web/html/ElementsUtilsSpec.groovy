package fr.adbonnin.kumoko.web.html

import org.jsoup.nodes.Element
import org.jsoup.parser.Tag
import org.jsoup.select.Elements
import spock.lang.Specification

import java.util.stream.Collectors

class ElementsUtilsSpec extends Specification {

    void "should remove the first element"() {
        given:
        def firstElement = new Element('first')
        def lastElement = new Element('last')

        and:
        def elements = new Elements([firstElement, lastElement])

        expect:
        elements.size() == 2

        when:
        def removed = ElementsUtils.removeFist(elements)

        then:
        elements.size() == 1
        removed == firstElement
    }

    void "should remove the last element"() {
        given:
        def firstElement = new Element('first')
        def lastElement = new Element('last')

        and:
        def elements = new Elements([firstElement, lastElement])

        expect:
        elements.size() == 2

        when:
        def removed = ElementsUtils.removeLast(elements)

        then:
        elements.size() == 1
        removed == lastElement
    }

    void "should stream from the first element"() {
        given:
        def firstElement = new Element('first')
        def nextElements = ['2', '3'].collect { new Element(it) }

        and:
        def elements = new Elements([firstElement] + nextElements)

        expect:
        elements.size() == 3

        when:
        def result = ElementsUtils.nextElements(elements, firstElement).collect(Collectors.toList())

        then:
        result == nextElements
    }

    void "should stream from the last element"() {
        given:
        def previousElements = ['1', '2'].collect { new Element(it) }
        def lastElement = new Element('first')

        and:
        def elements = new Elements(previousElements + [lastElement])

        expect:
        elements.size() == 3

        when:
        def result = ElementsUtils.nextElements(elements, lastElement).collect(Collectors.toList())

        then:
        result.isEmpty()
    }

    void "should stream over abs elements"() {
        given:
        def element = new Element(Tag.valueOf('a'), 'http://a.b')
        element.attr('href', '/c')

        and:
        def elements = new Elements(element)

        when:
        def urls = ElementsUtils.absUrls(elements).collect(Collectors.toList())

        then:
        urls == ['http://a.b/c']
    }

    void "should stream over abs attribute elements"() {
        def attributeKey = 'test'

        given:
        def element = new Element(Tag.valueOf('a'), 'http://a.b')
        element.attr(attributeKey, '/c')

        and:
        def elements = new Elements(element)

        when:
        def urls = ElementsUtils.absUrls(elements, attributeKey).collect(Collectors.toList())

        then:
        urls == ['http://a.b/c']
    }
}
