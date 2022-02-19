package fr.adbonnin.kumoko.scraper;

import fr.adbonnin.kumoko.collect.IteratorUtils;
import fr.adbonnin.kumoko.scraper.page.Page;
import fr.adbonnin.kumoko.scraper.page.PageContext;
import fr.adbonnin.kumoko.scraper.page.PageVisitor;

import java.io.IOException;
import java.util.*;

public class Scraper {

    private final Set<Page> scraped;

    public Scraper() {
        this(Collections.emptyList());
    }

    public Scraper(Collection<Page> scraped) {
        this.scraped = new HashSet<>(scraped);
    }

    public Set<Page> getScraped() {
        return scraped;
    }

    public void scrap(Page seed, PageVisitor visitor) throws IOException {
        scrap(Collections.singletonList(seed), visitor);
    }

    public void scrap(List<Page> seeds, PageVisitor visitor) throws IOException {
        final ScrapContext context = buildScrapContext();
        context.enqueueFirst(seeds);

        PageContext pageContext;
        while ((pageContext = context.dequeue()) != null) {
            scrap(pageContext, visitor);
        }
    }

    protected void scrap(PageContext pageContext, PageVisitor visitor) throws IOException {

        if (pageContext.isSubtreeSkipped()) {
            return;
        }

        final Page page = pageContext.getPage();
        if (!shouldScrap(page)) {
            return;
        }

        try {
            visitor.preVisitPage(pageContext);
            if (pageContext.isSubtreeSkipped()) {
                return;
            }

            final List<Page> nextPages = page.scrap(visitor);
            scraped.add(page);

            visitor.postVisitPage(pageContext);
            if (pageContext.isSubtreeSkipped()) {
                return;
            }

            final Iterator<Page> filtered = IteratorUtils.filter(nextPages.iterator(), this::shouldScrap);
            pageContext.enqueueFirst(IteratorUtils.newArrayList(filtered));
        }
        catch (IOException e) {
            visitor.visitPageFailed(pageContext, e);
        }
    }

    protected ScrapContext buildScrapContext() {
        return new LinkedScrapContext();
    }

    protected boolean shouldScrap(Page page) {
        return !scraped.contains(page);
    }
}
