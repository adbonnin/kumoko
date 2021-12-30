package fr.adbonnin.kumoko.collect

import spock.lang.Specification

import java.util.function.Predicate

class IteratorUtilsSpec extends Specification {

    void "should create a new ArrayList from an iterator"() {
        def list = [1, 2, 3]

        given:
        def iterator = list.iterator()

        when:
        def result = IteratorUtils.newArrayList(iterator)

        then:
        result == list

        and:
        result instanceof ArrayList
        !result.is(list)
    }

    void "should add all elements to a list"() {
        given:
        def list = [1] as List

        when:
        def wasModified = IteratorUtils.addAll(list, elements.iterator())

        then:
        wasModified == expectWasModified
        list == expectedList

        where:
        elements || expectedList | expectWasModified
        []       || [1]          | false
        [1]      || [1, 1]       | true
        [1, 2]   || [1, 1, 2]    | true
    }

    void "should filter elements"() {
        given:
        def iterator = [1, 2, 3].iterator()
        def predicate = { it != 2 } as Predicate<Integer>

        when:
        def filtered = IteratorUtils.filter(iterator, predicate)

        then:
        filtered.hasNext()
        filtered.next() == 1

        and:
        filtered.hasNext()
        filtered.next() == 3

        and:
        !filtered.hasNext()

        when:
        filtered.next()

        then:
        thrown(NoSuchElementException)
    }
}
