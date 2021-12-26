package fr.adbonnin.kumoko;

import fr.adbonnin.kumoko.page.RootPage;
import fr.adbonnin.kumoko.scraper.ScrapContext;
import org.apache.hc.client5.http.classic.HttpClient;

import java.io.IOException;
import java.util.*;

public class Scraper {

    public final HttpClient httpClient;

    public Scraper(HttpClient httpClient) {
        this.httpClient = Objects.requireNonNull(httpClient);
    }

    public void scrap(Page seed, PageVisitor visitor) throws IOException {
        scrap(Collections.singleton(seed).iterator(), visitor);
    }

    public void scrap(Iterator<Page> seeds, PageVisitor visitor) throws IOException {
        final Set<Page> scraped = new LinkedHashSet<>();

        final ScrapContext context = new ScrapContext();
        context.enqueue(RootPage.INSTANCE, seeds);

        while (context.hasNext()) {
            final ScrapContext.Node node = context.next();

            while (node.hasNext()) {
                final ScrapPageContext pageContext = node.next();
                try {
                    visitor.preVisitPage(pageContext);
                    if (pageContext.isSubtreeSkipped()) {
                        continue;
                    }

                    final Page page = pageContext.getPage();
                    final Set<Page> nextPages = page.scrap(httpClient, visitor);
                    scraped.add(page);

                    visitor.postVisitPage(pageContext);
                    if (pageContext.isSubtreeSkipped()) {
                        continue;
                    }

                    context.enqueue(page, nextPages.iterator());
                }
                catch (IOException e) {
                    visitor.visitPageFailed(pageContext, e);
                }
            }
        }
    }
}
