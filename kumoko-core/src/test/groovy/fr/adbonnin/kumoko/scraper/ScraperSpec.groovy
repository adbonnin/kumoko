package fr.adbonnin.kumoko.scraper

import fr.adbonnin.kumoko.page.Page
import fr.adbonnin.kumoko.page.PageContext
import fr.adbonnin.kumoko.page.PageVisitor
import spock.lang.Specification
import spock.lang.Subject

class ScraperSpec extends Specification {

    void "should scrap pages"() {
        given:
        @Subject scraper = new Scraper()

        and:
        def page1 = Mock(Page)
        def page2 = Mock(Page)
        def visitor = Mock(PageVisitor)

        when:
        scraper.scrap(page1, visitor)

        then:
        1 * visitor.preVisitPage(_ as PageContext) >> {
            assert (it[0] as PageContext).page.is(page1)
        }

        then:
        1 * page1.scrap(visitor) >> [page2]

        then:
        1 * visitor.postVisitPage(_ as PageContext) >> {
            assert (it[0] as PageContext).page.is(page1)
        }

        then:
        1 * visitor.preVisitPage(_ as PageContext) >> {
            assert (it[0] as PageContext).page.is(page2)
        }

        then:
        1 * page2.scrap(visitor) >> []

        then:
        1 * visitor.postVisitPage(_ as PageContext) >> {
            assert (it[0] as PageContext).page.is(page2)
        }
    }

    void "should call visitPageFailed when preVisitPage fail"() {
        given:
        @Subject scraper = new Scraper()

        and:
        def page = Mock(Page)
        def visitor = Mock(PageVisitor)
        def exception = new IOException()

        when:
        scraper.scrap(page, visitor)

        then:
        1 * visitor.preVisitPage(_ as PageContext) >> { throw exception }

        then:
        1 * visitor.visitPageFailed(_ as PageContext, exception)
        0 * page.scrap(visitor)
        0 * visitor.postVisitPage(_ as PageContext)
    }

    void "should call visitPageFailed when scrap fail"() {
        given:
        @Subject scraper = new Scraper()

        and:
        def page = Mock(Page)
        def visitor = Mock(PageVisitor)
        def exception = new IOException()

        when:
        scraper.scrap(page, visitor)

        then:
        1 * visitor.preVisitPage(_ as PageContext)

        then:
        1 * page.scrap(visitor) >> { throw exception }

        then:
        1 * visitor.visitPageFailed(_ as PageContext, exception)
        0 * visitor.postVisitPage(_ as PageContext)
    }

    void "should call visitPageFailed when postVisitPage fail"() {
        given:
        @Subject scraper = new Scraper()

        and:
        def page = Mock(Page)
        def visitor = Mock(PageVisitor)
        def exception = new IOException()

        when:
        scraper.scrap(page, visitor)

        then:
        1 * visitor.preVisitPage(_ as PageContext)

        then:
        1 * page.scrap(visitor) >> []

        then:
        1 * visitor.postVisitPage(_ as PageContext) >> { throw exception }

        then:
        1 * visitor.visitPageFailed(_ as PageContext, exception)
    }
}
