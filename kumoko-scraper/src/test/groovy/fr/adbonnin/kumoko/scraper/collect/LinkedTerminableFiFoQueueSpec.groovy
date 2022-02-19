package fr.adbonnin.kumoko.scraper.collect

import spock.lang.Specification
import spock.lang.Subject

class LinkedTerminableFiFoQueueSpec extends Specification {

    void "should enqueue and dequeue values"() {
        given:
        @Subject def queue = new LinkedTerminableFiFoQueue<Integer>()

        expect:
        queue.size() == 0
        queue.dequeue() == null

        when:
        queue.enqueueFirst([1, 2, 3])

        then:
        queue.size() == 3

        and:
        queue.dequeue() == 1
        queue.size() == 2

        and:
        queue.dequeue() == 2
        queue.size() == 1

        when:
        queue.enqueueFirst([4, 5])

        then:
        queue.size() == 3

        and:
        queue.dequeue() == 4
        queue.size() == 2

        and:
        queue.dequeue() == 5
        queue.size() == 1

        and:
        queue.dequeue() == 3
        queue.size() == 0

        and:
        queue.dequeue() == null
        queue.size() == 0
    }

    void "should clear the queue when terminated"() {
        given:
        @Subject def queue = new LinkedTerminableFiFoQueue<Integer>()
        queue.enqueueFirst([1, 2, 3])

        expect:
        !queue.isTerminated()
        queue.size() == 3

        when:
        queue.terminate()

        then:
        queue.isTerminated()
        queue.size() == 0
        queue.dequeue() == null

        when:
        queue.enqueueFirst([4, 5, 6])

        then:
        queue.size() == 0
    }
}
