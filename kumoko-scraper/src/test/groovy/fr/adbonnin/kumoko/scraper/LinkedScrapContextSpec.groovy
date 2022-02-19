package fr.adbonnin.kumoko.scraper

import fr.adbonnin.kumoko.collect.IteratorUtils
import fr.adbonnin.kumoko.scraper.fixtures.SimplePage
import fr.adbonnin.kumoko.scraper.page.PageContext
import spock.lang.Specification
import spock.lang.Subject

class LinkedScrapContextSpec extends Specification {

    void "should enqueue and dequeue a page"() {
        given:
        def page = new SimplePage()
        @Subject def context = new LinkedScrapContext()

        expect:
        context.size() == 0
        context.dequeue() == null

        when:
        context.enqueueFirst([page])

        then:
        context.size() == 1

        when:
        def pageContext = context.dequeue()

        then:
        context.size() == 0
        context.dequeue() == null

        with(pageContext) {
            it.page.is(page)
            it.parent != null
            it.parentPage == null
            !it.subtreeSkipped
        }
    }

    void "should dequeue and enqueued first"() {
        given:
        def page1 = new SimplePage()
        def page2 = new SimplePage()
        def page3 = new SimplePage()
        def page4 = new SimplePage()
        def page5 = new SimplePage()

        and:
        @Subject def context = new LinkedScrapContext()
        context.enqueueFirst([page1, page2])

        expect:
        context.size() == 2

        when:
        def pageContext1 = context.dequeue()
        pageContext1.enqueueFirst([page3, page4])

        then:
        context.size() == 3

        with(pageContext1) {
            it.page.is(page1)
            it.parent != null
            it.parentPage == null
        }

        when:
        def pageContext3 = context.dequeue()

        then:
        context.size() == 2

        with(pageContext3) {
            it.page.is(page3)
            it.parent.is(pageContext1)
            it.parentPage.is(page1)
        }

        when:
        pageContext3.enqueueFirst([page5])

        then:
        context.size() == 3

        and:
        context.dequeue().page.is(page5)
        context.size() == 2

        and:
        context.dequeue().page.is(page4)
        context.size() == 1

        and:
        context.dequeue().page.is(page2)
        context.size() == 0
    }

    void "should iterate over links"() {
        given:
        def page1 = new SimplePage()
        def page2 = new SimplePage()
        def page3 = new SimplePage()

        and:
        @Subject def context = new LinkedScrapContext()
        context.enqueueFirst([page1])

        and:
        def pageContext1 = context.dequeue()
        pageContext1.enqueueFirst([page2, page3])

        when:
        def result = IteratorUtils.newArrayList(pageContext1.iterator()).collect { it.page }

        then:
        result == [page2, page3]
    }

    void "should skip subtree"() {
        given:
        def page1 = new SimplePage()
        def page2 = new SimplePage()
        def page3 = new SimplePage()
        def page4 = new SimplePage()

        and:
        @Subject def context = new LinkedScrapContext()
        context.enqueueFirst([page1, page2])

        and:
        def pageContext1 = context.dequeue()
        pageContext1.enqueueFirst([page3, page4])

        def pageContext3 = context.dequeue()
        def pageContext4 = context.dequeue()
        def pageContext2 = context.dequeue()

        and:
        def pageContexts = [
                pageContext1,
                pageContext2,
                pageContext3,
                pageContext4,
        ]

        when:
        test(pageContexts)

        then:
        pageContexts.collect { it.subtreeSkipped } == expectedSubtreeSkipped

        where:
        test                                                           || expectedSubtreeSkipped
        ({ List<PageContext> contexts -> contexts[2].skipSubtree() })  || [false, false, true, false]
        ({ List<PageContext> contexts -> contexts[2].skipSiblings() }) || [false, false, false, true]
        ({ List<PageContext> contexts -> contexts[0].skipSubtree() })  || [true, false, true, true]
    }

    void "should enqueue skipped subtree"() {
        given:
        def page1 = new SimplePage()
        def page2 = new SimplePage()

        and:
        @Subject def context = new LinkedScrapContext()
        context.enqueueFirst([page1])

        and:
        def pageContext1 = context.dequeue()

        expect:
        !pageContext1.isSubtreeSkipped()

        when:
        pageContext1.skipSubtree()
        pageContext1.enqueueFirst([page2])

        def pageContext2 = context.dequeue()

        then:
        pageContext1.isSubtreeSkipped()
        pageContext2.isSubtreeSkipped()
    }

    void "should terminate the context"() {
        given:
        def page1 = new SimplePage()

        and:
        @Subject def context = new LinkedScrapContext()
        context.enqueueFirst([page1])

        expect:
        !context.isTerminated()
        context.size() == 1

        when:
        context.terminate()

        then:
        context.isTerminated()
        context.size() == 0
        context.dequeue() == null
    }

    void "should terminate the context with a page context"() {
        given:
        def page1 = new SimplePage()
        def page2 = new SimplePage()

        and:
        @Subject def context = new LinkedScrapContext()
        context.enqueueFirst([page1, page2])

        and:
        def pageContext1 = context.dequeue()

        expect:
        !context.isTerminated()
        context.size() == 1

        and:
        !pageContext1.isTerminated()

        when:
        pageContext1.terminate()

        then:
        context.isTerminated()
        context.size() == 0
        context.dequeue() == null

        and:
        pageContext1.isTerminated()
    }
}
